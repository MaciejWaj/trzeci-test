package pl.kurs.trzecitest.service;


import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.kurs.trzecitest.repository.AppUserRepository;
import pl.kurs.trzecitest.security.AppRole;
import pl.kurs.trzecitest.security.AppUser;


import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AppUserService implements UserDetailsService {

    private final AppUserRepository appUserRepository;
    private final PasswordEncoder passwordEncoder;

    @PostConstruct
    public void initUserDb() {
        AppRole adminRole = new AppRole("ROLE_ADMIN");
        AppRole creatorRole = new AppRole("ROLE_CREATOR");

        AppUser admin = new AppUser("admin", passwordEncoder.encode("admin"), Set.of(adminRole));
        AppUser creator = new AppUser("creator", passwordEncoder.encode("creator"), Set.of(creatorRole));

        appUserRepository.saveAll(List.of(admin, creator));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return appUserRepository.findByUsernameWithRoles(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));
    }
}
