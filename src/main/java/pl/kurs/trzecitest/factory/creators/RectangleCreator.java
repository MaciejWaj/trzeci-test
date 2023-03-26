package pl.kurs.trzecitest.factory.creators;

import org.springframework.stereotype.Service;
import pl.kurs.trzecitest.model.Figures;
import pl.kurs.trzecitest.model.Rectangle;

import java.util.Map;

@Service
public class RectangleCreator implements FiguresCreator {

    @Override
    public String getType() {
        return "RECTANGLE";
    }

    @Override
    public Figures create(Map<String, Object> parameters) {
        Rectangle createdRectangle = new Rectangle(
                getType(),
                getDoubleParameter("height", parameters),
                getDoubleParameter("length", parameters)
        );
        createdRectangle.setArea(createdRectangle.calculateArea());
        createdRectangle.setPerimeter(createdRectangle.calculatePerimeter());
        return createdRectangle;
    }

}
