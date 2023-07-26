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
        String updatedHeight = parameters.get("height");
        String updatedLength = parameters.get("length");

        if(Double.parseDouble(updatedHeight) != rectangle.getHeight()) {
            rectangle.setHeight(Double.parseDouble(updatedHeight));
        } else if (Double.parseDouble(updatedLength) != rectangle.getLength()) {
            rectangle.setLength(Double.parseDouble(updatedLength));
        }
        rectangle.setVersion(version);
        return rectangle;
    }
}
