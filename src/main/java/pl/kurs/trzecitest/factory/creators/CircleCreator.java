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
        String updatedRadius = parameters.get("radius");
        circle.setRadius(Double.parseDouble(updatedRadius));
        circle.setVersion(version);
        return circle;
    }
}
