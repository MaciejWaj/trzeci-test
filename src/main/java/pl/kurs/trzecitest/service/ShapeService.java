package pl.kurs.trzecitest.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.kurs.trzecitest.command.CreateShapeCommand;
import pl.kurs.trzecitest.command.UpgradeShapeCommand;
import pl.kurs.trzecitest.exception.DuplicateShapeException;
import pl.kurs.trzecitest.exception.ShapeNotBelongToUserException;
import pl.kurs.trzecitest.exception.ShapeNotFoundException;
import pl.kurs.trzecitest.factory.creators.ShapeFactory;
import pl.kurs.trzecitest.model.Shape;
import pl.kurs.trzecitest.repository.ShapeRepository;
import pl.kurs.trzecitest.security.AppUser;
import pl.kurs.trzecitest.shapefinder.ShapeSpecificationFinder;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
@Transactional
@RequiredArgsConstructor
public class ShapeService {

    private final ShapeRepository shapeRepository;
    private final ShapeFactory shapeFactory;
    private final ShapeSpecificationFinder shapeSpecificationFinder;
    private final AppUserService appUserService;

    public Shape createShape(CreateShapeCommand command) throws DuplicateShapeException, NullPointerException {
        checkCommand(command);
        Shape shape = shapeFactory.create(command);
        AppUser loadedUser = appUserService.getUserFromAuthentication();
        shape.setAppUser(loadedUser);
        return shapeRepository.save(shape);
    }

    public List<Shape> findBySpecification(Map<String, String> param) {
        return shapeSpecificationFinder.getShapeWithParams(param);
    }

    public Shape findById(int id) throws ShapeNotFoundException {
        return shapeRepository.findById(id).orElseThrow(() -> new ShapeNotFoundException("Shape with id " + id + " not found"));
    }

    public List<Shape> findByCreatedBy(String createdBy) {
        return shapeRepository.findByCreatedBy(createdBy);
    }

    public void deleteShapeByIdForCurrentUser(int shapeId, String currentUsername) throws ShapeNotFoundException, ShapeNotBelongToUserException {
        Shape shape = findById(shapeId);
        shapeAuthorization(shape, currentUsername);
        shapeRepository.deleteById(shapeId);
    }

    public Shape editShape(UpgradeShapeCommand upgradeShapeCommand, String currentUsername) throws ShapeNotFoundException, ShapeNotBelongToUserException, IllegalAccessException {
        Shape shape = findById(upgradeShapeCommand.getId());
        shapeAuthorization(shape, currentUsername);

        Field[] shapeFields = shape.getClass().getDeclaredFields();
        for (Field field : shapeFields) {
            field.setAccessible(true);
            String fieldName = field.getName();
            for (Map.Entry<String, String> entry : upgradeShapeCommand.getParameters().entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                if (fieldName.equals(key)) {
                    field.set(shape, Double.parseDouble(value));
                }
            }
        }
        shape.setLastModifiedAt(LocalDateTime.now());
        shape.setLastModifiedBy(currentUsername);
        shape.setVersion(shape.getVersion() + 1);
        shapeRepository.save(shape);
        return shape;
    }

    public int countCreatedShape(String userById) {
        return shapeRepository.countByCreatedBy(userById);
    }

    private void shapeAuthorization(Shape shape, String currentUsername) throws ShapeNotFoundException, ShapeNotBelongToUserException {
        if (shape == null) {
            throw new ShapeNotFoundException("Shape doesn't exist");
        }
        if (!shape.getCreatedBy().equals(currentUsername)) {
            throw new ShapeNotBelongToUserException("Shape doesn't belong to you");
        }
    }

    private void checkCommand(CreateShapeCommand command) throws NullPointerException, DuplicateShapeException {
        if (command.getType() == null || command.getParameters() == null) {
            throw new NullPointerException("Command cannot be empty");
        }
        if (findBySpecification(command.getParameters()).stream().findFirst().isPresent()) {
            throw new DuplicateShapeException("Figure with parameters " + command.getParameters() + " exists");
        }
    }
}
