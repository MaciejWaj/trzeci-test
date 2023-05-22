package pl.kurs.trzecitest.convertertodto;

import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;
import org.springframework.stereotype.Component;
import pl.kurs.trzecitest.controller.UserController;
import pl.kurs.trzecitest.dto.RectangleDto;
import pl.kurs.trzecitest.exception.UserNotFoundException;
import pl.kurs.trzecitest.model.Rectangle;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class RectangleToRectangleDtoConverter implements Converter<Rectangle, RectangleDto>, ShapeToShapeDtoConverter {

    @Override
    public RectangleDto convert(MappingContext<Rectangle, RectangleDto> mappingContext) {
        Rectangle source = mappingContext.getSource();
        RectangleDto dto = new RectangleDto(
                source.getId(),
                source.getClass().getSimpleName(),
                source.getVersion(),
                source.getHeight(),
                source.getLength(),
                source.getCreatedBy(),
                source.getCreateAt(),
                source.getLastModifiedAt(),
                source.getLastModifiedBy(),
                source.calculateArea(),
                source.calculatePerimeter());
        try {
            dto.add(linkTo((methodOn(UserController.class).findUserByUsername(source.getCreatedBy()))).withRel("Author"));
        } catch (UserNotFoundException e) {
            e.printStackTrace();
        }
        return dto;
    }

    @Override
    public RectangleDto getDtoType() {
        return RectangleDto.builder().build();
    }

    @Override
    public String getConverterType() {
        return "Rectangle";
    }

}
