package pl.kurs.trzecitest.convertertodto;

import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;
import org.springframework.stereotype.Component;
import pl.kurs.trzecitest.dto.CircleDto;
import pl.kurs.trzecitest.model.Circle;

@Component
public class CircleToCircleDtoConverter implements Converter<Circle, CircleDto>, ShapeToShapeDtoConverter {

    @Override
    public CircleDto convert(MappingContext<Circle, CircleDto> mappingContext) {
        Circle source = mappingContext.getSource();
        return new CircleDto(
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
