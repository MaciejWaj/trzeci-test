package pl.kurs.trzecitest.service;

import lombok.RequiredArgsConstructor;
import org.mockito.internal.matchers.Null;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.kurs.trzecitest.command.CreateShapeCommand;
import pl.kurs.trzecitest.exception.DuplicateShapeException;
import pl.kurs.trzecitest.factory.creators.ShapeFactory;
import pl.kurs.trzecitest.model.Shape;
import pl.kurs.trzecitest.repository.ShapeRepository;
import pl.kurs.trzecitest.shapefinder.ShapeSpecificationFinder;

import java.util.List;
import java.util.Map;

@Service
@Transactional
@RequiredArgsConstructor
public class ShapeService {

    private final ShapeRepository shapeRepository;
    private final ShapeFactory shapeFactory;
    private final ShapeSpecificationFinder shapeSpecificationFinder;

    public Shape createShape(CreateShapeCommand command) throws DuplicateShapeException, NullPointerException {
        checkCommand(command);
        return shapeRepository.saveAndFlush(shapeFactory.create(command));
    }

    public List<Shape> findBySpecification(Map<String, String> param) {
        return shapeSpecificationFinder.getShapeWithParams(param);
    }

    private void checkCommand(CreateShapeCommand command) throws NullPointerException, DuplicateShapeException {
        if (command.getType() == null || command.getParameters() == null) {
            throw new NullPointerException("Command cannot be null");
        }
        if (findBySpecification(command.getParameters()).stream().findFirst().isPresent()) {
            throw new DuplicateShapeException("Figure with parameters " + command.getParameters() + " exists");
        }
    }
}
