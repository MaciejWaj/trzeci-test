package pl.kurs.trzecitest.convertertodto;

import pl.kurs.trzecitest.dto.UserDto;

public interface UserToUserDto {

    String getConverterType();

    Class<? extends UserDto> getDtoType();
}
