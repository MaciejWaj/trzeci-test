package pl.kurs.trzecitest.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.kurs.trzecitest.command.CreateShapeCommand;
import pl.kurs.trzecitest.command.UpgradeShapeCommand;
import pl.kurs.trzecitest.exception.CommandInvalidValueException;
import pl.kurs.trzecitest.exception.DuplicateShapeException;
import pl.kurs.trzecitest.exception.ShapeNotFoundException;
import pl.kurs.trzecitest.factory.creators.ShapeFactory;
import pl.kurs.trzecitest.model.Shape;
import pl.kurs.trzecitest.repository.ShapeRepository;
import pl.kurs.trzecitest.security.AppUser;
import pl.kurs.trzecitest.shapefinder.ShapeSpecificationFinder;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ShapeService {

    private final ShapeRepository shapeRepository;
    private final ShapeFactory shapeFactory;
    private final ShapeSpecificationFinder shapeSpecificationFinder;
    private final AppUserService appUserService;

    @Transactional
    public Shape createShape(CreateShapeCommand command) {
        checkCommand(command);
        Shape shape = shapeFactory.create(command);
        return shapeRepository.save(shape);
    }

    @Transactional(readOnly = true)
    public List<Shape> findBySpecification(Map<String, String> param) {
        return shapeSpecificationFinder.getShapeWithParams(param);
    }

    @Transactional(readOnly = true)
    public Set<Shape> findByCreatedBy(String createdBy) {
        AppUser byUsername = appUserService.findByUsername(createdBy);
        return byUsername.getShape();
    }

    @Transactional
    public void deleteByIdAndUsername(int shapeId, String currentUserName) {
        Shape shape = findByShapeIdAndUsername(shapeId, currentUserName);
        shapeRepository.delete(shape);
    }

    @Transactional
    public Shape editShape(UpgradeShapeCommand upgradeShapeCommand, String currentUsername) {
        checkUpgradeShapeCommand(upgradeShapeCommand);
        Shape shape = findByShapeIdAndUsername(upgradeShapeCommand.getId(), currentUsername);
        shapeFactory.update(shape, upgradeShapeCommand);
        return shapeRepository.save(shape);
    }

    @Transactional(readOnly = true)
    public Shape findByShapeIdAndUsername(int id, String currentUsername) {
        return shapeRepository.findByIdAndCreatedBy(id, currentUsername)
                .orElseThrow(() -> new ShapeNotFoundException("Shape with id " + id + " and created by " + currentUsername + " not found"));
    }

    private void checkCommand(CreateShapeCommand command) {
        if (command.getType() == null || command.getParameters() == null || command.getParameters().isEmpty()) {
            throw new CommandInvalidValueException("value can't be empty");
        }
        if (findBySpecification(command.getParameters()).stream().findFirst().isPresent()) {
            throw new DuplicateShapeException("Figure with parameters " + command.getParameters() + " exists");
        }
    }

    private void checkUpgradeShapeCommand(UpgradeShapeCommand upgradeCommand) {
        if (upgradeCommand.getParameters() == null ||
                !upgradeCommand.getParameters().entrySet().stream().allMatch(e -> isDouble(e.getValue()))) {
            throw new CommandInvalidValueException("invalid value");
        }
    }

    private boolean isDouble(String value) {
        try {
            Double.parseDouble(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

}
