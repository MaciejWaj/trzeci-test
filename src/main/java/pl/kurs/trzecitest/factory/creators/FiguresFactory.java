package pl.kurs.trzecitest.factory.creators;

import org.springframework.stereotype.Service;
import pl.kurs.trzecitest.command.CreateFiguresCommand;
import pl.kurs.trzecitest.model.AuditableEntity;
import pl.kurs.trzecitest.model.Figures;

import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class FiguresFactory extends AuditableEntity {

    private final Map<String, FiguresCreator> creators;

    public FiguresFactory(Set<FiguresCreator> creators) {
        this.creators = creators.stream().collect(Collectors.toMap(FiguresCreator::getType, Function.identity()));
    }

    public Figures create(CreateFiguresCommand command) {
        return creators.get(command.getType()).create(command.getParameters());
    }

}
