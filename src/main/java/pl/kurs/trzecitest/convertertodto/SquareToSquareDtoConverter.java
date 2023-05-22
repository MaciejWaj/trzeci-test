package pl.kurs.trzecitest.convertertodto;

import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;
import org.springframework.stereotype.Component;
import pl.kurs.trzecitest.controller.UserController;
import pl.kurs.trzecitest.dto.SquareDto;
import pl.kurs.trzecitest.exception.UserNotFoundException;
import pl.kurs.trzecitest.model.Square;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class SquareToSquareDtoConverter implements Converter<Square, SquareDto>, ShapeToShapeDtoConverter {

    @Override
    public SquareDto convert(MappingContext<Square, SquareDto> mappingContext) {
        Square source = mappingContext.getSource();
        SquareDto dto = new SquareDto(
                source.getId(),
                source.getClass().getSimpleName(),
                source.getVersion(),
                source.getWidth(),
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
    public SquareDto getDtoType() {
        return SquareDto.builder().build();
    }

    @Override
    public String getConverterType() {
        return "Square";
    }

}
