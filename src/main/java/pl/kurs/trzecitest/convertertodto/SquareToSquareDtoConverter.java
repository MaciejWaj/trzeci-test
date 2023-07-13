package pl.kurs.trzecitest.convertertodto;

import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;
import org.springframework.stereotype.Component;
import pl.kurs.trzecitest.controller.UserController;
import pl.kurs.trzecitest.dto.SquareDto;
import pl.kurs.trzecitest.model.Square;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class SquareToSquareDtoConverter implements Converter<Square, SquareDto>, ShapeToShapeDtoConverter {

    @Override
    public SquareDto convert(MappingContext<Square, SquareDto> mappingContext) {
        Square source = mappingContext.getSource();
        SquareDto dto = SquareDto.builder()
                .id(source.getId())
                .type(source.getClass().getSimpleName())
                .version(source.getVersion())
                .width(source.getWidth())
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
    public SquareDto getDtoType() {
        return SquareDto.builder().build();
    }

    @Override
    public String getConverterType() {
        return "Square";
    }

}
