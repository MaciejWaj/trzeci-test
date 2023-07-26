package pl.kurs.trzecitest.shapefinder;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Query;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.kurs.trzecitest.model.Shape;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class ShapeSpecificationFinder {

    private final EntityManagerFactory entityManagerFactory;

    public List<Shape> getShapeWithParams(Map<String, String> parameters) {

        EntityManager entityManager = entityManagerFactory.createEntityManager();

        String viewName = getViewName(parameters);

        String queryStr = "SELECT * FROM " + viewName;
        String condition = getCondition(parameters);
        if (!condition.isEmpty()) {
            queryStr += " WHERE " + condition;
        }

        Query query = entityManager.createNativeQuery(queryStr, Shape.class);

        if (!parameters.isEmpty()) {
            for (Map.Entry<String, String> entry : parameters.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                query.setParameter(key, value);
            }
        }
        return query.getResultList();
    }

    private String getCondition(Map<String, String> parameters) {
        List<String> conditions = new ArrayList<>();

        for (Map.Entry<String, String> entry : parameters.entrySet()) {
            String key = entry.getKey();
            String clearKey = key.replaceAll("[^a-zA-Z0-9\\s]", "");
            if (clearKey.endsWith("From")) {
                String keyWithoutSuffix = clearKey.substring(0, clearKey.length() - 4);
                conditions.add(keyWithoutSuffix + " >= :" + clearKey );

            } else if (clearKey.endsWith("To")) {
                String keyWithoutSuffix = clearKey.substring(0, clearKey.length() - 2);
                conditions.add(keyWithoutSuffix + " <= :" + clearKey);
            } else if (clearKey.contains("type")) {
                conditions.add("d" + clearKey + " = :" + clearKey);
            } else {
                String queryKey = clearKey.replaceAll("([A-Z])", "_$1".toLowerCase());
                conditions.add(queryKey + " = :" +  clearKey);
            }
        }
        return String.join(" AND ", conditions);
    }

    private String getViewName(Map<String, String> params) {
        if (params.containsKey("areaFrom") || params.containsKey("areaTo") || params.containsKey("area") ||
                params.containsKey("perimeterFrom") || params.containsKey("perimeterTo") || params.containsKey("perimeter")) {
            return "shape_with_area_and_perimeter";
        } else {
            return "all_shapes";
        }
    }
}