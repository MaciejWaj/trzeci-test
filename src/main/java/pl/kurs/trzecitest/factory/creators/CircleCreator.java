package pl.kurs.trzecitest.factory.creators;

import org.springframework.stereotype.Service;
import pl.kurs.trzecitest.model.Circle;

import java.util.Map;

@Service
public class CircleCreator implements ShapeCreator<Circle> {

    @Override
    public String getType() {
        return "CIRCLE";
    }

    @Override
    public Circle create(Map<String, String> parameters) {
        return new Circle(getDoubleParameter("radius", parameters));
    }

    @Override
    public Circle update(Circle circle, Map<String, String> parameters, int version) {
        for (Map.Entry<String, String> entry : parameters.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();

            if (key.equals("radius")) {
                circle.setRadius(Double.parseDouble(value));
            }
        }
        circle.setVersion(version);
        return circle;
    }
}
