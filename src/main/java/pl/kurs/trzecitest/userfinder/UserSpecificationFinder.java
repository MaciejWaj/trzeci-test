package pl.kurs.trzecitest.userfinder;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Query;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.kurs.trzecitest.security.AppUser;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class UserSpecificationFinder {

    private final EntityManagerFactory entityManagerFactory;

    public List<AppUser> findUserByParameters(Map<String, String> parameter) {

        EntityManager entityManager = entityManagerFactory.createEntityManager();

        String queryStr = "SELECT * FROM all_user";
        String condition = getCondition(parameter);
        if (!condition.isEmpty()) {
            queryStr += " WHERE " + condition;
        }

        Query query = entityManager.createNativeQuery(queryStr, AppUser.class);

        if (!parameter.isEmpty()) {
            int parameterIndex = 1;
            for (String value : parameter.values()) {
                query.setParameter(parameterIndex, value);
                parameterIndex++;
            }
        }

        return query.getResultList();
    }

    private String getCondition(Map<String, String> parameters) {
        List<String> conditions = new ArrayList<>();
        int queryValue = 1;
        for (Map.Entry<String, String> entry : parameters.entrySet()) {
            String key = entry.getKey();
            String clearKey = key.replaceAll("[^a-zA-Z0-9\\s]", "");
            if (clearKey.endsWith("From")) {
                String keyWithoutSuffix = clearKey.substring(0, clearKey.length() - 4);
                conditions.add(keyWithoutSuffix + " >= " + "?" + queryValue);
                queryValue++;
            } else if (clearKey.endsWith("To")) {
                String keyWithoutSuffix = clearKey.substring(0, clearKey.length() - 2);
                conditions.add(keyWithoutSuffix + " <= " + "?" + queryValue);
                queryValue++;
            } else {
                String queryKey = clearKey.replaceAll("([A-Z])", "_$1".toLowerCase());
                conditions.add(queryKey + " = " + "?" + queryValue);
                queryValue++;
            }
        }
        return String.join(" AND ", conditions);
    }
}
