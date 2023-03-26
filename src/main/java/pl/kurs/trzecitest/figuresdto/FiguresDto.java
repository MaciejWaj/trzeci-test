package pl.kurs.trzecitest.figuresdto;

import lombok.Getter;
import lombok.Setter;
import pl.kurs.trzecitest.model.AuditableEntity;


@Getter
@Setter
public abstract class FiguresDto extends AuditableEntity {

        private String type;

}
