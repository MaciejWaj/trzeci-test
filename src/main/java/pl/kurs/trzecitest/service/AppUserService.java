package pl.kurs.trzecitest.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
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

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AppUserService implements UserDetailsService {

    private final AppUserRepository appUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserSpecificationFinder userSpecificationFinder;

    @PostConstruct
    public void initUserDb() {
        AppRole adminRole = new AppRole("ROLE_ADMIN");
        AppRole creatorRole = new AppRole("ROLE_CREATOR");

        AppUser admin = new AppUser("TomaszAdmin", passwordEncoder.encode("admin"), Set.of(adminRole), LocalDateTime.now(), LocalDateTime.now());
        AppUser creator = new AppUser("Maciej", passwordEncoder.encode("creator"), Set.of(creatorRole), LocalDateTime.now(), LocalDateTime.now());
        AppUser creator2 = new AppUser("Test", passwordEncoder.encode("creator"), Set.of(creatorRole), LocalDateTime.now(), LocalDateTime.now());
        appUserRepository.saveAll(List.of(admin, creator, creator2));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return appUserRepository.findByUsernameWithRoles(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));
    }

    public AppUser getUserFromAuthentication() {
        AppUser user = (AppUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return appUserRepository.findById(user.getId()).orElseThrow();
    }

    public AppUser findByUsername(String username) throws UserNotFoundException {
        return appUserRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User with name " + username + " not found."));
    }

    public List<AppUser> findBySpecification(Map<String, String> param) {
        return userSpecificationFinder.findUserByParameters(param);
    }
}