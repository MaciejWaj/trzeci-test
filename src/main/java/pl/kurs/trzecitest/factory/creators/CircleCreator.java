package pl.kurs.trzecitest.factory.creators;

import org.springframework.stereotype.Service;
import pl.kurs.trzecitest.model.Circle;
import pl.kurs.trzecitest.model.Figures;

import java.util.Map;

@Service
public class CircleCreator implements FiguresCreator {

    @Override
    public String getType() {
        return "CIRCLE";
    }

    @Override
    public Figures create(Map<String, Object> parameters) {
        Circle createCircle = new Circle(
                getType(),
                getDoubleParameter("radius", parameters)
        );
        createCircle.setArea(createCircle.calculateArea());
        createCircle.setPerimeter(createCircle.calculatePerimeter());
        return createCircle;
    }


}
