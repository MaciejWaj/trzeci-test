package pl.kurs.trzecitest.convertertodto;

import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;
import org.springframework.stereotype.Component;
import pl.kurs.trzecitest.dto.SquareDto;
import pl.kurs.trzecitest.model.Square;

@Component
public class SquareToSquareDtoConverter implements Converter<Square, SquareDto>, ShapeToShapeDtoConverter {

    @Override
    public SquareDto convert(MappingContext<Square, SquareDto> mappingContext) {
        Square source = mappingContext.getSource();
        return new SquareDto(
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
