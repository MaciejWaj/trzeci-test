package pl.kurs.trzecitest.controller;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import pl.kurs.trzecitest.command.CreateShapeCommand;
import pl.kurs.trzecitest.command.UpgradeShapeCommand;
import pl.kurs.trzecitest.convertertodto.ShapeDtoFactory;
import pl.kurs.trzecitest.dto.ShapeDto;
import pl.kurs.trzecitest.exception.DuplicateShapeException;
import pl.kurs.trzecitest.exception.ShapeNotBelongToUserException;
import pl.kurs.trzecitest.exception.ShapeNotFoundException;
import pl.kurs.trzecitest.model.Shape;
import pl.kurs.trzecitest.service.ShapeService;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/shapes")
public class ShapeController {

    private final ShapeService shapeService;
    private final ModelMapper modelMapper;
    private final ShapeDtoFactory shapeDtoFactory;

    @PostMapping
    public ResponseEntity<ShapeDto> save(@RequestBody CreateShapeCommand command) throws DuplicateShapeException, NullPointerException {
        return ResponseEntity.status(HttpStatus.CREATED).body(toDto(shapeService.createShape(command)));
    }

    @GetMapping
    public List<ShapeDto> getShapeWithParam(@RequestParam(required = false) Map<String, String> filterParam) {
        return shapeService.findBySpecification(filterParam).stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteShapeById(@PathVariable int id, @AuthenticationPrincipal UserDetails currentUser) throws ShapeNotBelongToUserException, ShapeNotFoundException {
        shapeService.deleteShapeByIdForCurrentUser(id, currentUser.getUsername());
        return new ResponseEntity(HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<ShapeDto> editShape(@RequestBody UpgradeShapeCommand upgradeShapeCommand, @AuthenticationPrincipal UserDetails currentUser) throws ShapeNotBelongToUserException, ShapeNotFoundException, IllegalAccessException {
        return ResponseEntity.status(HttpStatus.OK).body(toDto(shapeService.editShape(upgradeShapeCommand, currentUser.getUsername())));
    }

    @GetMapping("/{user}")
    public List<ShapeDto> getShapeCreatedBy(@PathVariable String user) {
        return shapeService.findByCreatedBy(user).stream().map(this::toDto).collect(Collectors.toList());
    }

    public ShapeDto toDto(Shape shape) {
        return modelMapper.map(shape, shapeDtoFactory.findConverterClass(shape).getClass());
    }
}