package pl.kurs.trzecitest.model;



import lombok.Getter;
import lombok.Setter;

import org.springframework.data.annotation.*;


import org.springframework.data.annotation.Version;


import javax.persistence.*;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
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

}
