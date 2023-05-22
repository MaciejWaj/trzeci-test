package pl.kurs.trzecitest.userfinder;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.kurs.trzecitest.security.AppUser;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class UserSpecificationFinder {

    private final EntityManagerFactory entityManagerFactory;

    public List<AppUser> findUserByParameters(Map<String, String> parameter) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        List<AppUser> users = new ArrayList<>();

        try {
            String queryStr = "SELECT * FROM all_user";
            String condition = getCondition(parameter);
            if (!condition.isEmpty()) {
                queryStr += " WHERE " + condition;
            }
            Query query = entityManager.createNativeQuery(queryStr, AppUser.class);
            users = query.getResultList();
        } catch (NoResultException e) {
            e.getMessage();
        } finally {
            entityManager.close();
        }
        return users;
    }

    private String getCondition(Map<String, String> parameters) {
        List<String> conditions = new ArrayList<>();
        for (Map.Entry<String, String> entry : parameters.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            String clearKey = key.replaceAll("[^a-zA-Z0-9\\s]", "");
            if (clearKey.endsWith("From")) {
                String keyWithoutSuffix = clearKey.substring(0, clearKey.length() - 4);
                conditions.add(keyWithoutSuffix + " >= " + value);
            } else if (clearKey.endsWith("To")) {
                String keyWithoutSuffix = clearKey.substring(0, clearKey.length() - 2);
                conditions.add(keyWithoutSuffix + " <= " + value);
            } else {
                String queryKey = clearKey.replaceAll("([A-Z])", "_$1".toLowerCase());
                conditions.add(queryKey + " = " + "'" + value + "'");
            }
        }
        return String.join(" AND ", conditions);
    }
}
