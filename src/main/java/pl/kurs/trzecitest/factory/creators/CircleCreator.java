package pl.kurs.trzecitest.factory.creators;

import org.springframework.stereotype.Service;
import pl.kurs.trzecitest.model.Circle;
import pl.kurs.trzecitest.model.Shape;

import java.util.Map;

@Service
public class CircleCreator implements ShapeCreator {

    @Override
    public String getType() {
        return "CIRCLE";
    }

    @Override
    public Shape create(Map<String, String> parameters) {
        return new Circle(getDoubleParameter("radius", parameters));
    }

    @Override
    public Shape update(Shape shape, Map<String, String> parameters) {

        if (shape instanceof Circle) {
            for (Map.Entry<String, String> entry : parameters.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();

                if (key.equals("radius")) {
                    ((Circle) shape).setRadius(Double.parseDouble(value));
                }
            }
        }
        return shape;
    }
}
