package pl.kurs.trzecitest.convertertodto;

import org.springframework.stereotype.Service;
import pl.kurs.trzecitest.figuresdto.FiguresDto;
import pl.kurs.trzecitest.model.Figures;

import java.util.List;

@Service
public class FiguresDtoFactory {

    private final List<FiguresToFigureDtoConverter> converters;

    public FiguresDtoFactory(List<FiguresToFigureDtoConverter> converters) {
        this.converters = converters;
    }


    public FiguresDto findConverterClass(Figures figures) {
        return converters.stream()
                .filter(converter -> figures.getType().equals(converter.getType()))
                .findFirst()
                .map(FiguresToFigureDtoConverter::getDtoType)
                .orElseThrow(() -> new RuntimeException("No matching converter found for figure type: " + figures.getType()));
    }

}