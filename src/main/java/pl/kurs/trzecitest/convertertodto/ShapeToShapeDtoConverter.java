package pl.kurs.trzecitest.convertertodto;

import pl.kurs.trzecitest.dto.ShapeDto;

public interface ShapeToShapeDtoConverter {

    ShapeDto getDtoType();

    String getConverterType();

}
