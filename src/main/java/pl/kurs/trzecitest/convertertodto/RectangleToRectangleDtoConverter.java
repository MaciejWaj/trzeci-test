package pl.kurs.trzecitest.convertertodto;

import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;
import org.springframework.stereotype.Component;
import pl.kurs.trzecitest.figuresdto.RectangleDto;
import pl.kurs.trzecitest.model.Rectangle;

@Component
public class RectangleToRectangleDtoConverter implements Converter<Rectangle, RectangleDto>, FiguresToFigureDtoConverter {

    @Override
    public RectangleDto convert(MappingContext<Rectangle, RectangleDto> mappingContext) {
        Rectangle source = mappingContext.getSource();
        return RectangleDto.builder()
                .id(source.getId())
                .type(source.getType())
                .length(source.getLength())
                .height(source.getHeight())
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
    public RectangleDto getDtoType() {
        return RectangleDto.builder().build();
    }

    @Override
    public String getType() {
        return "RECTANGLE";
    }

}
