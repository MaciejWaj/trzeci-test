package pl.kurs.trzecitest.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pl.kurs.trzecitest.security.AppUser;

import java.util.Optional;

public interface AppUserRepository extends JpaRepository<AppUser, Integer> {

    @Query("SELECT au from AppUser au LEFT JOIN FETCH au.shape shape LEFT JOIN FETCH au.roles roles WHERE au.username = :username")
    Optional<AppUser> findByUsernameWithDetails(String username);

}