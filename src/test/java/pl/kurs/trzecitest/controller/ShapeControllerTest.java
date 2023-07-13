package pl.kurs.trzecitest.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import pl.kurs.trzecitest.command.CreateShapeCommand;
import pl.kurs.trzecitest.command.UpgradeShapeCommand;
import pl.kurs.trzecitest.dto.CircleDto;
import pl.kurs.trzecitest.dto.SquareDto;
import pl.kurs.trzecitest.model.Circle;
import pl.kurs.trzecitest.repository.AppUserRepository;
import pl.kurs.trzecitest.repository.ShapeRepository;
import pl.kurs.trzecitest.security.AppRole;
import pl.kurs.trzecitest.security.AppUser;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = {"classpath:data.sql"})
class ShapeControllerTest {

    @Autowired
    private ShapeRepository shapeRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private AppUserRepository appUserRepository;

    @Autowired
    private MockMvc mvc;

    @AfterEach
    public void cleanUp() {
        shapeRepository.deleteAll();
    }

    @Test
    void shouldCreateShapeSquare() throws Exception {
        AppUser appUser = mockUserCreator();
        //given
        CreateShapeCommand command = new CreateShapeCommand();
        command.setType("SQUARE");
        command.setParameters(Collections.singletonMap("width", "10"));

        //when
        String contentAsString = mvc.perform(post("/api/v1/shapes")
                .with(user(appUser))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(command)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        //then
        SquareDto squareDto = objectMapper.readValue(contentAsString, SquareDto.class);
        assertEquals(squareDto.getType(), "Square");

        assertEquals(1, shapeRepository.findAll().size());
    }

    @Test
    void shouldThrowUnauthorizedWhenCreateShapeWithoutRoleCreator() throws Exception {
        AppUser appUser = mockUserAdmin();
        //given
        CreateShapeCommand command = new CreateShapeCommand();
        command.setType("SQUARE");
        command.setParameters(Collections.singletonMap("width", "10"));

        //when
        String contentAsString = mvc.perform(post("/api/v1/shapes")
                .with(user(appUser))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(command)))
                .andExpect(status().isForbidden())
                .andReturn()
                .getResponse()
                .getContentAsString();
    }

    @Test
    void shouldFindBySpecification() throws Exception {
        AppUser appUser = mockUserCreator();
        appUser.setUsername("Maciej");
        //given
        Circle circle = new Circle(10);
        circle.setCreatedBy(appUser);
        shapeRepository.save(circle);

        //when
        String contentAsString = mvc.perform(get("/api/v1/shapes?radius=10")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        //then
        List<CircleDto> circleDto = objectMapper.readValue(contentAsString, new TypeReference<>() {
        });

        assertEquals(1, circleDto.size());
        assertEquals(10, circleDto.get(0).getRadius());
    }

    @Test
    void commandCantBeEmpty() throws Exception {
        AppUser appUser = mockUserCreator();
        //given
        CreateShapeCommand command = new CreateShapeCommand();

        //when
        ResultActions resultActions = mvc.perform(post("/api/v1/shapes")
                .with(user(appUser))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(command)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void addingDuplicateShapeShouldThrowException() throws Exception {
        AppUser appUser = mockUserCreator();
        //given
        Circle circle = new Circle(1);
        CreateShapeCommand command = new CreateShapeCommand();
        command.setType("CIRCLE");
        command.setParameters(Collections.singletonMap("radius", "1"));

        //when
        shapeRepository.save(circle);

        //then
        ResultActions resultActions = mvc.perform(post("/api/v1/shapes")
                .with(user(appUser))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(command)))
                .andExpect(status().isBadRequest());

        assertEquals(1, shapeRepository.findAll().size());
    }

    @Test
    void shouldDeleteShapeById() throws Exception {
        AppUser appUser = mockUserCreator();
        appUser.setUsername("Maciej");
        //given
        Circle circle = new Circle(10);
        circle.setCreatedBy(appUser);
        shapeRepository.save(circle);

        //when
        ResultActions resultActions = mvc.perform(delete("/api/v1/shapes/{id}", circle.getId())
                .with(user(appUser.getUsername()).roles("CREATOR")))
                .andExpect(status().isNoContent());

        assertEquals(0, shapeRepository.findAll().size());
    }

    @Test
    void shouldEditCircleRadiusFrom1To10() throws Exception {
        AppUser appUser = mockUserCreator();
        appUser.setUsername("Maciej");
        //given
        Circle circle = new Circle(1);
        shapeRepository.save(circle);

        UpgradeShapeCommand upgradeCommand = new UpgradeShapeCommand();
        upgradeCommand.setId(circle.getId());
        upgradeCommand.setParameters(Collections.singletonMap("radius", "10"));

        //when
        String contentAsString = mvc.perform(put("/api/v1/shapes")
                .with(user(appUser))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(upgradeCommand)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        //then
        CircleDto circleDto = objectMapper.readValue(contentAsString, CircleDto.class);
        assertEquals(10.0, circleDto.getRadius());
    }

    private AppUser mockUserCreator() {
        AppRole appRole = new AppRole();
        appRole.setName("ROLE_CREATOR");
        AppUser appUser = new AppUser();
        appUser.setRoles(Set.of(appRole));
        appUser = appUserRepository.save(appUser);
        return appUser;
    }

    private AppUser mockUserAdmin() {
        AppRole appRole = new AppRole();
        appRole.setName("ROLE_ADMIN");
        AppUser appUser = new AppUser();
        appUser.setRoles(Set.of(appRole));
        appUser = appUserRepository.save(appUser);
        return appUser;
    }
}