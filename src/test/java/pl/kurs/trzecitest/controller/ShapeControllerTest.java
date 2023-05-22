package pl.kurs.trzecitest.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import pl.kurs.trzecitest.TrzeciTestApplication;
import pl.kurs.trzecitest.command.CreateShapeCommand;
import pl.kurs.trzecitest.exception.DuplicateShapeException;
import pl.kurs.trzecitest.exception.ShapeNotBelongToUserException;
import pl.kurs.trzecitest.exception.ShapeNotFoundException;
import pl.kurs.trzecitest.model.Circle;
import pl.kurs.trzecitest.model.Shape;
import pl.kurs.trzecitest.model.Square;
import pl.kurs.trzecitest.repository.ShapeRepository;
import pl.kurs.trzecitest.service.ShapeService;
import pl.kurs.trzecitest.shapefinder.ShapeSpecificationFinder;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = TrzeciTestApplication.class)
@AutoConfigureMockMvc
class ShapeControllerTest {

    @Mock
    private ShapeRepository shapeRepository;
    @Mock
    private ShapeSpecificationFinder shapeSpecificationFinder;
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

    @Test
    public void deleteShapeByIdShouldReturnOk() throws ShapeNotBelongToUserException, ShapeNotFoundException {
        int shapeId = 1;
        String username = "testUser";
        UserDetails currentUser = User.withUsername(username).password("password").roles("USER").build();

        shapeService.deleteShapeByIdForCurrentUser(shapeId, currentUser.getUsername());

        ResponseEntity responseEntity = shapeController.deleteShapeById(shapeId, currentUser);
        Mockito.verify(shapeService, times(1)).deleteShapeByIdForCurrentUser(shapeId, username);

        assert responseEntity != null;
        assert responseEntity.getStatusCode() == HttpStatus.OK;
    }

    @Test
    public void deleteShapeByIdShouldThrowShapeNotBelongToUserException() throws ShapeNotBelongToUserException, ShapeNotFoundException {
        int shapeId = 1;
        String ownerUsername = "ownerUser";
        String otherUsername = "otherUser";

        UserDetails ownerUser = User.withUsername(ownerUsername).password("password").roles("USER").build();
        UserDetails otherUser = User.withUsername(otherUsername).password("password").roles("USER").build();

        Circle circle = new Circle();
        circle.setId(shapeId);
        circle.setCreatedBy(ownerUser.getUsername());

        doThrow(ShapeNotBelongToUserException.class)
                .when(shapeService)
                .deleteShapeByIdForCurrentUser(shapeId, otherUsername);

        assertThrows(ShapeNotBelongToUserException.class, () -> {
            shapeService.deleteShapeByIdForCurrentUser(shapeId, otherUser.getUsername());
        });
    }

    @Test
    public void deleteShapeByIdShouldThrowShapeNotFoundException() throws ShapeNotBelongToUserException, ShapeNotFoundException {
        int existingShapeId = 1;
        int shapeIdToDelete = 2;
        String username = "testUser";
        UserDetails currentUser = User.withUsername(username).password("password").roles("USER").build();

        List<Shape> db = new ArrayList<>();
        db.add(new Circle(existingShapeId));

        doThrow(ShapeNotFoundException.class)
                .when(shapeService)
                .deleteShapeByIdForCurrentUser(shapeIdToDelete, username);

        assertThrows(ShapeNotFoundException.class, () -> {
            shapeService.deleteShapeByIdForCurrentUser(shapeIdToDelete, currentUser.getUsername());
        });
    }
}