package pl.kurs.trzecitest.convertertodto;

import org.springframework.stereotype.Service;
import pl.kurs.trzecitest.dto.ShapeDto;
import pl.kurs.trzecitest.model.Shape;

import java.util.List;
import java.util.Locale;

@Service
public class ShapeDtoFactory {

    private final List<ShapeToShapeDtoConverter> converters;

    public ShapeDtoFactory(List<ShapeToShapeDtoConverter> converters) {
        this.converters = converters;
    }


    public ShapeDto findConverterClass(Shape shape) {
        return converters.stream()
                .filter(converter -> shape.getClass().getSimpleName().toLowerCase(Locale.ROOT)
                        .equals(converter.getConverterType().toLowerCase(Locale.ROOT)))
                .findFirst()
                .map(ShapeToShapeDtoConverter::getDtoType)
                .orElseThrow(() -> new RuntimeException("No matching converter found for shape type: " + shape.getClass()));
    }

}