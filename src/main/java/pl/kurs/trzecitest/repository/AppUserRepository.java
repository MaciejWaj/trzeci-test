package pl.kurs.trzecitest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import pl.kurs.trzecitest.security.AppUser;

import java.util.Optional;

public interface AppUserRepository extends JpaRepository<AppUser, Integer> {

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    Optional<AppUser> findByUsername(String username);

}
