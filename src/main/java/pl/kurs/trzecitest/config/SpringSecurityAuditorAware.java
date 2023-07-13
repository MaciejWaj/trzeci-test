package pl.kurs.trzecitest.config;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import pl.kurs.trzecitest.security.AppUser;

import java.util.Optional;

public class SpringSecurityAuditorAware implements AuditorAware<AppUser> {

    @Override
    public Optional<AppUser> getCurrentAuditor() {
        return Optional.ofNullable(SecurityContextHolder.getContext())
                .map(SecurityContext::getAuthentication)
                .filter(Authentication::isAuthenticated)
                .map(Authentication::getPrincipal)
                .map(AppUser.class::cast);
    }
}
