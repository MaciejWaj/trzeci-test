package pl.kurs.trzecitest.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import pl.kurs.trzecitest.TrzeciTestApplication;
import pl.kurs.trzecitest.exception.UserNotFoundException;
import pl.kurs.trzecitest.security.AppUser;
import pl.kurs.trzecitest.service.AppUserService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = TrzeciTestApplication.class)
@AutoConfigureMockMvc
class UserControllerTest {

    @Mock
    private AppUserService appUserService;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void findUserByUsernameShouldReturnUserDto() throws UserNotFoundException {
        String username = "testUser";

        AppUser user = new AppUser();
        user.setUsername(username);
        when(appUserService.findByUsername(username)).thenReturn(user);

        AppUser findUser = appUserService.findByUsername(username);

        assertEquals(findUser.getUsername(), username);
    }

    @Test
    public void findUserByUsernameShouldThrowUserNotFound() throws UserNotFoundException {
        String username = "testUser";
        String usernameToSearch = "Test";

        AppUser user = new AppUser();
        user.setUsername(username);
        when(appUserService.findByUsername(usernameToSearch)).thenReturn(user);

        when(appUserService.findByUsername(usernameToSearch)).thenThrow(UserNotFoundException.class);

    }

    @Test
    public void getUserByParameters_WithEmptyFilterParam_ShouldReturnAllUsers() {
        Map<String, String> param = new HashMap<>();
        List<AppUser> appUsers = new ArrayList<>();

        when(appUserService.findBySpecification(param)).thenReturn(appUsers);
        List<AppUser> result = appUserService.findBySpecification(param);

        assertNotNull(result);
        assertEquals(appUsers, result);
    }


}