package pl.kurs.trzecitest.convertertodto;

import lombok.RequiredArgsConstructor;
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

    @Override
    public CircleDto convert(MappingContext<Circle, CircleDto> mappingContext) {
        Circle source = mappingContext.getSource();
        CircleDto dto = CircleDto.builder()
                .id(source.getId())
                .type(source.getClass().getSimpleName())
                .version(source.getVersion())
                .radius(source.getRadius())
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
    public CircleDto getDtoType() {
        return CircleDto.builder().build();
    }

    @Override
    public String getConverterType() {
        return "Circle";
    }

}
