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
        return new Square(getDoubleParameter("width", parameters));
    }

    @Override
    public Shape update(Shape shape, Map<String, String> parameters) {

        if (shape instanceof Square) {
            for (Map.Entry<String, String> entry : parameters.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();

                if (key.equals("width")) {
                    ((Square) shape).setWidth(Double.parseDouble(value));
                }
            }
        }
        return shape;
    }

}
