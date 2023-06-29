package pl.kurs.trzecitest.service;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.kurs.trzecitest.exception.UserNotFoundException;
import pl.kurs.trzecitest.repository.AppUserRepository;
import pl.kurs.trzecitest.security.AppRole;
import pl.kurs.trzecitest.security.AppUser;
import pl.kurs.trzecitest.userfinder.UserSpecificationFinder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AppUserService implements UserDetailsService {

    private final AppUserRepository appUserRepository;
    private final UserSpecificationFinder userSpecificationFinder;
    private final PasswordEncoder passwordEncoder;

    @PostConstruct
    public void initUserDataBase() {
        AppRole adminRole = new AppRole("ROLE_ADMIN");
        AppRole creatorRole = new AppRole("ROLE_CREATOR");

        AppUser admin = new AppUser("TomaszAdmin", passwordEncoder.encode("admin"), Set.of(adminRole), LocalDateTime.now(), LocalDateTime.now());
        AppUser creator = new AppUser("Maciej", passwordEncoder.encode("creator"), Set.of(creatorRole), LocalDateTime.now(), LocalDateTime.now());
        AppUser creator2 = new AppUser("Test", passwordEncoder.encode("creator"), Set.of(creatorRole), LocalDateTime.now(), LocalDateTime.now());
        appUserRepository.saveAll(List.of(admin, creator, creator2));
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        AppUser user = appUserRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("user with username + " + username + " doesn't exist"));
        return new User(user.getUsername(), user.getPassword(), user.getRoles()
                .stream()
                .map(role -> new SimpleGrantedAuthority(role.getAuthority()))
                .collect(Collectors.toSet()));
    }

    public AppUser findByUsername(String username) {
        return appUserRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User with name " + username + " not found."));
    }

    public List<AppUser> findBySpecification(Map<String, String> param) {
        return userSpecificationFinder.findUserByParameters(param);
    }

    public int getUserId(String currentUserName) {
        return appUserRepository.findByUsername(currentUserName)
                .map(AppUser::getId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
    }
}