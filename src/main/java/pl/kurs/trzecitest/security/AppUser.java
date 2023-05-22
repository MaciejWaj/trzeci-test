package pl.kurs.trzecitest.security;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import pl.kurs.trzecitest.model.Shape;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class AppUser extends RepresentationModel<AppUser> implements UserDetails  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String username;
    private String password;
    @CreatedDate
    private LocalDateTime createdAccountAt;
    private LocalDateTime lastLogin;

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL)
    @Fetch(FetchMode.JOIN)
    private List<Shape> shape = new ArrayList<>();

    @ManyToMany(cascade = CascadeType.ALL)
    @Fetch(FetchMode.JOIN)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<AppRole> roles = new HashSet<>();

    public AppUser(String username, String password, Set<AppRole> roles, LocalDateTime createdAccountAt, LocalDateTime lastLogin) {
        this.username = username;
        this.password = password;
        this.createdAccountAt = createdAccountAt;
        this.lastLogin = lastLogin;
        this.roles = roles;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
