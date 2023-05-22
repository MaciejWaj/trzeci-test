package pl.kurs.trzecitest.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Version;
import org.springframework.data.annotation.*;
import pl.kurs.trzecitest.security.AppUser;

import javax.persistence.Id;
import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class Shape {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Version
    private int version;
    @CreatedBy
    private String createdBy;
    @CreatedDate
    private LocalDateTime createAt;
    @LastModifiedDate
    private LocalDateTime lastModifiedAt;
    @LastModifiedBy
    private String lastModifiedBy;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "author_id")
    private AppUser author;

    public Shape(AppUser author) {
        this.author = author;
    }

    public void setAppUser(AppUser appUser) {
        this.author = appUser;
        appUser.getShape().add(this);
    }

    public abstract double calculateArea();

    public abstract double calculatePerimeter();
}
