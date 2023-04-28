package pl.kurs.trzecitest.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import pl.kurs.trzecitest.TrzeciTestApplication;
import pl.kurs.trzecitest.command.CreateShapeCommand;
import pl.kurs.trzecitest.convertertodto.ShapeDtoFactory;
import pl.kurs.trzecitest.exception.DuplicateShapeException;
import pl.kurs.trzecitest.factory.creators.ShapeFactory;
import pl.kurs.trzecitest.model.Shape;
import pl.kurs.trzecitest.model.Square;
import pl.kurs.trzecitest.repository.ShapeRepository;
import pl.kurs.trzecitest.service.AppUserService;
import pl.kurs.trzecitest.service.ShapeService;
import pl.kurs.trzecitest.shapefinder.ShapeSpecificationFinder;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = TrzeciTestApplication.class)
@AutoConfigureMockMvc
class ShapeControllerTest {

    @Mock
    private ShapeRepository shapeRepository;
    @Mock
    private ShapeFactory shapeFactory;
    @Mock
    private ShapeSpecificationFinder shapeSpecificationFinder;
    @Mock
    private AppUserService userService;
    @Mock
    private ShapeDtoFactory shapeDtoFactory;
    @Mock
    private ShapeService shapeService;
    @InjectMocks
    private ShapeController shapeController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void shouldCreateShapeSquare() throws DuplicateShapeException {
        CreateShapeCommand command = new CreateShapeCommand();
        Shape square = new Square();
        command.setType("SQUARE");
        command.setParameters(Collections.singletonMap("width", "10"));

        when(shapeService.createShape(command)).thenReturn(square);
        when(shapeRepository.saveAndFlush(square)).thenReturn(square);

        Shape result = shapeService.createShape(command);

        assertNotNull(result);
        assertEquals(square, result);
    }

    @Test
    public void shouldFindBySpecification() {
        Map<String, String> param = new HashMap<>();
        List<Shape> shapes = new ArrayList<>();

        when(shapeSpecificationFinder.getShapeWithParams(param)).thenReturn(shapes);

        List<Shape> result = shapeService.findBySpecification(param);

        assertNotNull(result);
        assertEquals(shapes, result);
    }

    @Test
    public void addingDuplicateShapeShouldThrowException() throws DuplicateShapeException {
        CreateShapeCommand command = new CreateShapeCommand();
        command.setType("Circle");
        command.setParameters(Collections.singletonMap("radius", "10"));
        shapeService.createShape(command);

        when(shapeService.createShape(command)).thenThrow(DuplicateShapeException.class);
    }

    @Test
    public void commandCantBeEmpty() throws NullPointerException, DuplicateShapeException {
        CreateShapeCommand command = new CreateShapeCommand();

        when(shapeService.createShape(command)).thenThrow(NullPointerException.class);
    }

}