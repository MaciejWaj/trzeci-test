package pl.kurs.trzecitest.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Version;
import org.springframework.data.annotation.*;

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

    public abstract double calculateArea();

    public abstract double calculatePerimeter();
}
