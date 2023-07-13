package pl.kurs.trzecitest.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import pl.kurs.trzecitest.dto.UserDto;
import pl.kurs.trzecitest.exception.UserNotFoundException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = {"classpath:data.sql"})
class UserControllerTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mvc;

    @Test
    void findUserByUsernameShouldReturnUserDto() throws Exception {
        //when
        String contentAsString = mvc.perform(get("/api/v1/users/Maciej")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        //then
        UserDto foundUser = objectMapper.readValue(contentAsString, UserDto.class);

        assertEquals("Maciej", foundUser.getUsername());
    }

    @Test
    void findUserByUsernameShouldThrowUserNotFound() throws Exception {
        //when
        try {
            String contentAsString = mvc.perform(get("/api/v1/users/UnknownUser")
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isNotFound())
                    .andReturn()
                    .getResponse()
                    .getContentAsString();
        } catch (UserNotFoundException e) {
            assertEquals("User with name UnknownUser not found.", e.getMessage());
        }
    }

    @Test
    @WithMockUser(username = "TomaszAdmin", roles = "ADMIN")
    void shouldFindUserByParametersIfHasRoleAdmin() throws Exception {
        //when
        String contentAsString = mvc.perform(get("/api/v1/users?username=Maciej")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        //then
        List<UserDto> foundUser = objectMapper.readValue(contentAsString, new TypeReference<>() {
        });

        assertEquals("Maciej", foundUser.get(0).getUsername());
    }
}