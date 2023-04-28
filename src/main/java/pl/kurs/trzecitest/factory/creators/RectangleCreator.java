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
        Rectangle createdRectangle = new Rectangle(
                getDoubleParameter("height", parameters),
                getDoubleParameter("length", parameters)
        );
        return createdRectangle;
    }

}
