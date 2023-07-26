package pl.kurs.trzecitest.convertertodto;

import pl.kurs.trzecitest.dto.ShapeDto;

public interface ShapeToShapeDtoConverter {

    String getConverterType();

    Class<? extends ShapeDto> getDtoType();
}
