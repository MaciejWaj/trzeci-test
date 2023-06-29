package pl.kurs.trzecitest.convertertodto;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;
import org.springframework.stereotype.Component;
import pl.kurs.trzecitest.controller.UserController;
import pl.kurs.trzecitest.dto.CircleDto;
import pl.kurs.trzecitest.model.Circle;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
@RequiredArgsConstructor
public class CircleToCircleDtoConverter implements Converter<Circle, CircleDto>, ShapeToShapeDtoConverter {

    @SneakyThrows
    @Override
    public CircleDto convert(MappingContext<Circle, CircleDto> mappingContext) {
        Circle source = mappingContext.getSource();
        CircleDto dto = new CircleDto(
                source.getId(),
                source.getClass().getSimpleName(),
                source.getVersion(),
                source.getRadius(),
                source.getCreatedBy().getUsername(),
                source.getCreateAt(),
                source.getLastModifiedAt(),
                source.getLastModifiedBy().getUsername(),
                source.calculateArea(),
                source.calculatePerimeter()
        );

        dto.add(linkTo((methodOn(UserController.class).findUserByUsername(source.getCreatedBy().getUsername()))).withRel("Author"));

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
