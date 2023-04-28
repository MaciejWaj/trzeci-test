package pl.kurs.trzecitest.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Rectangle extends Shape {

    private double height;
    private double length;


    @Override
    public double calculateArea() {
        return length * height;
    }

    @Override
    public double calculatePerimeter() {
        return 2 * length + 2 * height;
    }
}
