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
        for (Map.Entry<String, String> entry : parameters.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();

            if (key.equals("width")) {
                square.setWidth(Double.parseDouble(value));
            }
        }
        square.setVersion(version);
        return square;
    }

}
