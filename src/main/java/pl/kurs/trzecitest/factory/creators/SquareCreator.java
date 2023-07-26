package pl.kurs.trzecitest.factory.creators;

import org.springframework.stereotype.Service;
import pl.kurs.trzecitest.model.Square;

import java.util.Map;

@Service
public class SquareCreator implements ShapeCreator<Square> {

    @Override
    public String getType() {
        return "SQUARE";
    }

    @Override
    public Square create(Map<String, String> parameters) {
        return new Square(getDoubleParameter("width", parameters));
    }

    @Override
    public Square update(Square square, Map<String, String> parameters, int version) {
        String updatedWidth = parameters.get("width");
        square.setWidth(Double.parseDouble(updatedWidth));
        square.setVersion(version);
        return square;
    }

}
