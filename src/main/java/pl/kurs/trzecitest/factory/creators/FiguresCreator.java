package pl.kurs.trzecitest.factory.creators;

import pl.kurs.trzecitest.model.Figures;

import java.util.Map;

public interface FiguresCreator {

    String getType();

    Figures create(Map<String, Object> parameters);

    default String getStringParameters(String name, Map<String, Object> parameters) {
        return (String) parameters.get(name);
    }

    default Double getDoubleParameter(String name, Map<String, Object> parameters) {
        return (Double) parameters.get(name);
    }

}
