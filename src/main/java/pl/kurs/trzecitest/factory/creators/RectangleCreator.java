package pl.kurs.trzecitest.factory.creators;

import org.springframework.stereotype.Service;
import pl.kurs.trzecitest.model.Rectangle;

import java.util.Map;

@Service
public class RectangleCreator implements ShapeCreator<Rectangle> {

    @Override
    public String getType() {
        return "RECTANGLE";
    }

    @Override
    public Rectangle create(Map<String, String> parameters) {
        return new Rectangle(getDoubleParameter("height", parameters), getDoubleParameter("length", parameters));
    }

    @Override
    public Rectangle update(Rectangle rectangle, Map<String, String> parameters, int version) {
        for (Map.Entry<String, String> entry : parameters.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();

            if (key.equals("height")) {
                rectangle.setHeight(Double.parseDouble(value));
            }
            if (key.equals("length")) {
                rectangle.setLength(Double.parseDouble(value));
            }
        }
        rectangle.setVersion(version);
        return rectangle;
    }
}
