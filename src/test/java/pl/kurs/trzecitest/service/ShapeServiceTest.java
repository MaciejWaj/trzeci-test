package pl.kurs.trzecitest.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.kurs.trzecitest.command.CreateShapeCommand;
import pl.kurs.trzecitest.command.UpgradeShapeCommand;
import pl.kurs.trzecitest.exception.CommandInvalidValueException;
import pl.kurs.trzecitest.exception.ShapeNotFoundException;
import pl.kurs.trzecitest.factory.creators.ShapeFactory;
import pl.kurs.trzecitest.model.Rectangle;
import pl.kurs.trzecitest.model.Shape;
import pl.kurs.trzecitest.model.Square;
import pl.kurs.trzecitest.repository.ShapeRepository;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ShapeServiceTest {

    private static final int SHAPE_ID = 1;
    private static final int USER_ID = 1;
    private static final String USER_NAME = "Maciej";

    @Mock
    private ShapeRepository shapeRepository;
    @Mock
    private ShapeFactory shapeFactory;
    @Mock
    private AppUserService appUserService;

    @InjectMocks
    private ShapeService shapeService;

    @Test
    void shouldCreateShapeSquare() {
        //given
        CreateShapeCommand command = new CreateShapeCommand();
        Square square = new Square();
        command.setType("SQUARE");
        command.setParameters(Collections.singletonMap("width", "10"));
        when(shapeFactory.create(command)).thenReturn(square);
        when(shapeRepository.save(square)).thenReturn(square);
        //when
        Shape result = shapeService.createShape(command);
        //then
        assertEquals(square, result);
    }

    @ParameterizedTest
    @MethodSource("provideInvalidCommand")
    void shouldThrowCommandInvalidValueExceptionWhenWrongCommand(CreateShapeCommand command) {
        //when then
        assertThrows(CommandInvalidValueException.class, () -> shapeService.createShape(command));
    }

    @Test
    void findBySpecificationShouldReturnEmptyListWhenParamsAreEmpty() {
        //give
        Map<String, String> params = Collections.emptyMap();
        // when
        List<Shape> result = shapeService.findBySpecification(params);
        // then
        assertEquals(Collections.emptyList(), result);
    }

    @Test
    void shouldFindByIdAndUsername() {
        //given
        Rectangle rectangle = new Rectangle();
        when(shapeRepository.findByIdAndCreatedBy(USER_ID, USER_NAME)).thenReturn(Optional.of(rectangle));
        //when
        Shape result = shapeService.findByShapeIdAndUsername(USER_ID, USER_NAME);
        //then
        assertEquals(rectangle, result);
    }

    @Test
    void shouldThrowShapeNotFoundExceptionWhenShapeNotFound() {
        //given
        when(shapeRepository.findByIdAndCreatedBy(USER_ID, USER_NAME)).thenReturn(Optional.empty());
        //when then
        assertThrows(ShapeNotFoundException.class, () -> shapeService.findByShapeIdAndUsername(SHAPE_ID, USER_NAME));
    }

    @Test
    void shouldEditShapeRectangle() {
        //given
        UpgradeShapeCommand upCommand = new UpgradeShapeCommand();
        upCommand.setId(SHAPE_ID);
        upCommand.setParameters(Map.of("height", "10"));
        Rectangle rectangle = new Rectangle();
        when(shapeRepository.findByIdAndCreatedBy(SHAPE_ID, USER_NAME)).thenReturn(Optional.of(rectangle));
        when(shapeFactory.update(rectangle, upCommand)).thenReturn(rectangle);
        when(shapeRepository.save(rectangle)).thenReturn(rectangle);
        //when
        Shape result = shapeService.editShape(upCommand, USER_NAME);
        //then
        assertEquals(rectangle, result);
    }

    @ParameterizedTest
    @MethodSource("provideInvalidUpdateCommand")
    void shouldThrowCommandInvalidValueExceptionOnUpdate(UpgradeShapeCommand command) {
        //when then
        assertThrows(CommandInvalidValueException.class, () -> shapeService.editShape(command, USER_NAME));
    }

    private static Stream<UpgradeShapeCommand> provideInvalidUpdateCommand() {
        UpgradeShapeCommand withoutParameters = new UpgradeShapeCommand();
        UpgradeShapeCommand withStringParameters = new UpgradeShapeCommand();
        withStringParameters.setParameters(Map.of("radius", "radius"));

        return Stream.of(
                withoutParameters,
                withStringParameters
        );
    }

    private static Stream<CreateShapeCommand> provideInvalidCommand() {
        CreateShapeCommand withoutType = new CreateShapeCommand();
        withoutType.setParameters(Map.of("width", "10"));
        CreateShapeCommand withoutParameters = new CreateShapeCommand();
        withoutParameters.setType("CIRCLE");
        CreateShapeCommand nullCommand = new CreateShapeCommand();
        nullCommand.setType("CIRCLE");
        nullCommand.setParameters(Map.of());

        return Stream.of(
                withoutParameters,
                withoutType,
                nullCommand);
    }
}