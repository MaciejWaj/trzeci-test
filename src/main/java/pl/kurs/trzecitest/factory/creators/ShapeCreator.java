package pl.kurs.trzecitest.factory.creators;

import pl.kurs.trzecitest.model.Shape;

import java.util.Map;

public interface ShapeCreator {

    String getType();

    Shape create(Map<String, String> parameters);

    Shape update(Shape shape, Map<String, String> parameters);

    default Double getDoubleParameter(String name, Map<String, String> parameters) {
        return Double.parseDouble(parameters.get(name));
    }

}
