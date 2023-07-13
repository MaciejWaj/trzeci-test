package pl.kurs.trzecitest.service;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.kurs.trzecitest.exception.UserNotFoundException;
import pl.kurs.trzecitest.repository.AppUserRepository;
import pl.kurs.trzecitest.security.AppRole;
import pl.kurs.trzecitest.security.AppUser;
import pl.kurs.trzecitest.userfinder.UserSpecificationFinder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) {
        return appUserRepository.findByUsernameWithDetails(username).orElseThrow(() -> new UsernameNotFoundException("user with username + " + username + " not found."));
    }

    @Transactional(readOnly = true)
    public AppUser findByUsername(String username) {
        return appUserRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User with name " + username + " not found."));
    }

    @Transactional(readOnly = true)
    public List<AppUser> findBySpecification(Map<String, String> param) {
        return userSpecificationFinder.findUserByParameters(param);
    }

}