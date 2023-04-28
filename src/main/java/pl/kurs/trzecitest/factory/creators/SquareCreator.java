package pl.kurs.trzecitest.factory.creators;

import org.springframework.stereotype.Service;
import pl.kurs.trzecitest.model.Shape;
import pl.kurs.trzecitest.model.Square;

import java.util.Map;

@Service
public class SquareCreator implements ShapeCreator {

    @Override
    public String getType() {
        return "SQUARE";
    }

    @Override
    public Shape create(Map<String, String> parameters) {
        Square createdSquare = new Square(
                getDoubleParameter("width", parameters)
        );
        return createdSquare;
    }

}
