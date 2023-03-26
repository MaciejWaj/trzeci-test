package pl.kurs.trzecitest.model;

import lombok.*;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.web.bind.annotation.GetMapping;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Rectangle extends Figures implements Serializable {

    @Column(name ="height")
    private double height;
    @Column(name ="length")
    private double length;

    public Rectangle(String type, double height, double length) {
        super(type);
        this.height = height;
        this.length = length;
    }

    @Override
    public double calculateArea() {
        return length * height;
    }

    @Override
    public double calculatePerimeter() {
        return 2 * length + 2 * height;
    }

}
