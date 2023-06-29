package pl.kurs.trzecitest.factory.creators;

import org.springframework.stereotype.Service;
import pl.kurs.trzecitest.model.Rectangle;
import pl.kurs.trzecitest.model.Shape;

import java.util.Map;

@Service
public class RectangleCreator implements ShapeCreator {

    @Override
    public String getType() {
        return "RECTANGLE";
    }

    @Override
    public Shape create(Map<String, String> parameters) {
        return new Rectangle(getDoubleParameter("height", parameters), getDoubleParameter("length", parameters));
    }

    @Override
    public Shape update(Shape shape, Map<String, String> parameters) {

        if (shape instanceof Rectangle) {
            for (Map.Entry<String, String> entry : parameters.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();

                if (key.equals("height")) {
                    ((Rectangle) shape).setHeight(Double.parseDouble(value));
                }
                if (key.equals("length")) {
                    ((Rectangle) shape).setLength(Double.parseDouble(value));
                }
            }
        }
        return shape;
    }
}
