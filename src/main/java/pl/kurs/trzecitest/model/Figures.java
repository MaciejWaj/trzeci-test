package pl.kurs.trzecitest.model;


import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
public abstract class Figures extends AuditableEntity implements Serializable {


    @Column(name ="type")
    private String type;
    @Column(name = "area")
    public double area;
    @Column(name = "perimeter")
    public double perimeter;



    public abstract double calculateArea();

    public abstract double calculatePerimeter();

    public Figures(String type) {
        this.type = type;
    }

}
