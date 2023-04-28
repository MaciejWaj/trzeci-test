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
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Square extends Shape {

    private double width;

    @Override
    public double calculateArea() {
        return width * width;
    }

    @Override
    public double calculatePerimeter() {
        return 4 * width;
    }
}
