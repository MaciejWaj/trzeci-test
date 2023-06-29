package pl.kurs.trzecitest.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.kurs.trzecitest.exception.UserNotFoundException;
import pl.kurs.trzecitest.repository.AppUserRepository;
import pl.kurs.trzecitest.security.AppUser;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AppUserServiceTest {

    @Mock
    private AppUserRepository appUserRepository;

    @InjectMocks
    private AppUserService appUserService;

    @Test
    void shouldLoadUserByUsername() {
        //given
        AppUser user = new AppUser();
        user.setUsername("Maciej");
        when(appUserRepository.findByUsername(user.getUsername())).thenReturn(Optional.of(user));
        //when
        AppUser result = appUserService.findByUsername(user.getUsername());
        //then
        Assertions.assertEquals(user.getUsername(), result.getUsername());
    }

    @Test
    void shouldThrowUsernameNotFoundExceptionWhenWrongUsername() {
        //given
        AppUser user = new AppUser();
        //when then
        Assertions.assertThrows(UserNotFoundException.class, () -> appUserService.findByUsername(user.getUsername()));
    }

    @Test
    void findBySpecificationShouldReturnEmptyListWhenParamsAreEmpty() {
        //give
        Map<String, String> params = Collections.emptyMap();
        // when
        List<AppUser> result = appUserService.findBySpecification(params);
        // then
        assertEquals(Collections.emptyList(), result);
    }

    @Test
    void shouldGetUserId() {
        //given
        AppUser user = new AppUser();
        user.setId(1);
        user.setUsername("Maciej");
        when(appUserRepository.findByUsername(user.getUsername())).thenReturn(Optional.of(user));
        //when
        int result = appUserService.getUserId(user.getUsername());
        //then
        assertEquals(user.getId(), result);
    }
}