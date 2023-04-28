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
        Circle createCircle = new Circle(
                getDoubleParameter("radius", parameters)
        );
        return createCircle;
    }

}
