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
        RectangleDto dto = RectangleDto.builder()
                .id(source.getId())
                .type(source.getClass().getSimpleName())
                .version(source.getVersion())
                .height(source.getHeight())
                .length(source.getLength())
                .createdBy(source.getCreatedBy().getUsername())
                .createAt(source.getCreateAt())
                .lastModifiedAt(source.getLastModifiedAt())
                .lastModifiedBy(source.getLastModifiedBy().getUsername())
                .area(source.calculateArea())
                .perimeter(source.calculatePerimeter())
                .build();

        dto.add(linkTo((methodOn(UserController.class).getByCreatedBy(source.getCreatedBy()))).withRel("Author"));

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
