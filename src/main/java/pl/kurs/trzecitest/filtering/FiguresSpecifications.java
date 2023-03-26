package pl.kurs.trzecitest.filtering;

import org.springframework.data.jpa.domain.Specification;

import org.apache.commons.lang3.StringUtils;
import pl.kurs.trzecitest.model.Figures;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class FiguresSpecifications implements Specification<Figures> {

    private final Map<String, String> params;

    public FiguresSpecifications(Map<String, String> params) {
        this.params = params;
    }

    @Override
    public Predicate toPredicate(Root<Figures> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        List<Predicate> predicates = new ArrayList<>();

        for (Map.Entry<String, String> entry : params.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            if (StringUtils.isNotBlank(value)) {
                if (key.endsWith("From")) {
                    String field = key.substring(0, key.length() - 4);
                    predicates.add(cb.greaterThanOrEqualTo(root.get(field), Double.parseDouble(value)));
                } else if (key.endsWith("To")) {
                    String field = key.substring(0, key.length() - 2);
                    predicates.add(cb.lessThanOrEqualTo(root.get(field), Double.parseDouble(value)));
                } else {
                    predicates.add(cb.like(cb.lower(root.get(key)), "%" + value.toLowerCase() + "%"));
                }
            }
        }

        return cb.and(predicates.toArray(new Predicate[0]));
    }

}


