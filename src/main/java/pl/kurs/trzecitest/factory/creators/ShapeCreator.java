package pl.kurs.trzecitest.factory.creators;

import pl.kurs.trzecitest.model.Shape;

import java.util.Map;

public interface ShapeCreator<T extends Shape> {

    String getType();

    T create(Map<String, String> parameters);

    T update(T shape, Map<String, String> parameters, int version);

    default Double getDoubleParameter(String name, Map<String, String> parameters) {
        return Double.parseDouble(parameters.get(name));
    }

}
