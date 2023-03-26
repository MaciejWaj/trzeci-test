package pl.kurs.trzecitest.convertertodto;

import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;
import org.springframework.stereotype.Component;
import pl.kurs.trzecitest.figuresdto.CircleDto;
import pl.kurs.trzecitest.model.Circle;

@Component
public class CircleToCircleDtoConverter implements Converter<Circle, CircleDto>, FiguresToFigureDtoConverter {

    @Override
    public CircleDto convert(MappingContext<Circle, CircleDto> mappingContext) {
        Circle source = mappingContext.getSource();
        return CircleDto.builder()
                .id(source.getId())
                .type(source.getType())
                .radius(source.getRadius())
                .version(source.getVersion())
                .createdBy(source.getCreatedBy())
                .createAt(source.getCreateAt())
                .lastModifiedAt(source.getLastModifiedAt())
                .lastModifiedBy(source.getLastModifiedBy())
                .area(source.getArea())
                .perimeter(source.getPerimeter())
                .build();
    }

    @Override
    public CircleDto getDtoType() {
        return CircleDto.builder().build();
    }

    @Override
    public String getType() {
        return "CIRCLE";
    }

}
