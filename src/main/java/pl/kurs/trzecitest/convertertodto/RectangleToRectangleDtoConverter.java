package pl.kurs.trzecitest.convertertodto;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;
import org.springframework.stereotype.Component;
import pl.kurs.trzecitest.controller.UserController;
import pl.kurs.trzecitest.dto.RectangleDto;
import pl.kurs.trzecitest.model.Rectangle;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
@Slf4j
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
                source.getCreatedBy().getUsername(),
                source.getCreateAt(),
                source.getLastModifiedAt(),
                source.getLastModifiedBy().getUsername(),
                source.calculateArea(),
                source.calculatePerimeter());

        dto.add(linkTo((methodOn(UserController.class).findUserByUsername(source.getCreatedBy().getUsername()))).withRel("Author"));

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
