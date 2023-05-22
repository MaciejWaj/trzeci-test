package pl.kurs.trzecitest.convertertodto;

import lombok.SneakyThrows;
import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;
import org.springframework.stereotype.Component;
import pl.kurs.trzecitest.controller.ShapeController;
import pl.kurs.trzecitest.controller.UserController;
import pl.kurs.trzecitest.dto.CircleDto;
import pl.kurs.trzecitest.exception.UserNotFoundException;
import pl.kurs.trzecitest.model.Circle;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class CircleToCircleDtoConverter implements Converter<Circle, CircleDto>, ShapeToShapeDtoConverter {

    @Override
    public CircleDto convert(MappingContext<Circle, CircleDto> mappingContext) {
        Circle source = mappingContext.getSource();
        CircleDto dto = new CircleDto(
                source.getId(),
                source.getClass().getSimpleName(),
                source.getVersion(),
                source.getRadius(),
                source.getCreatedBy(),
                source.getCreateAt(),
                source.getLastModifiedAt(),
                source.getLastModifiedBy(),
                source.calculateArea(),
                source.calculatePerimeter()
        );
        try {
            dto.add(linkTo((methodOn(UserController.class).findUserByUsername(source.getCreatedBy()))).withRel("Author"));
        } catch (UserNotFoundException e) {
            e.printStackTrace();
        }
        return dto;
    }

    @Override
    public CircleDto getDtoType() {
        return CircleDto.builder().build();
    }

    @Override
    public String getConverterType() {
        return "Circle";
    }

}
