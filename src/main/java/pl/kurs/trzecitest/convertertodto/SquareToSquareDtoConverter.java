package pl.kurs.trzecitest.convertertodto;

import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;
import org.springframework.stereotype.Component;
import pl.kurs.trzecitest.figuresdto.SquareDto;
import pl.kurs.trzecitest.model.Square;

@Component
public class SquareToSquareDtoConverter implements Converter<Square, SquareDto>, FiguresToFigureDtoConverter {

    @Override
    public SquareDto convert(MappingContext<Square, SquareDto> mappingContext) {
        Square source = mappingContext.getSource();
        return SquareDto.builder()
                .id(source.getId())
                .type(source.getType())
                .width(source.getWidth())
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
    public SquareDto getDtoType() {
        return SquareDto.builder().build();
    }

    @Override
    public String getType() {
        return "SQUARE";
    }

}
