package pl.kurs.trzecitest.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.autoconfigure.domain.EntityScan;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Square extends Figures implements Serializable {

    @Column(name ="width")
    private double width;

    public Square(String type, double width) {
        super(type);
        this.width = width;
    }

    @Override
    public double calculateArea() {
        return width * width;
    }

    @Override
    public double calculatePerimeter() {
        return 4 * width;
    }

}
