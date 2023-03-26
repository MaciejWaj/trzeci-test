package pl.kurs.trzecitest.factory.creators;

import org.springframework.stereotype.Service;
import pl.kurs.trzecitest.model.Figures;
import pl.kurs.trzecitest.model.Square;

import java.util.Map;

@Service
public class SquareCreator implements FiguresCreator {

    @Override
    public String getType() {
        return "SQUARE";
    }

    @Override
    public Figures create(Map<String, Object> parameters) {
        Square createdSquare = new Square(
                getType(),
                getDoubleParameter("width", parameters)
        );
        createdSquare.setArea(createdSquare.calculateArea());
        createdSquare.setPerimeter(createdSquare.calculatePerimeter());
        return createdSquare;
    }

}
