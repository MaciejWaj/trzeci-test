package pl.kurs.trzecitest.convertertodto;

import org.springframework.stereotype.Service;
import pl.kurs.trzecitest.dto.UserDto;
import pl.kurs.trzecitest.security.AppUser;

import java.util.List;
import java.util.Locale;

@Service
public class UserDtoFactory {

    private final List<UserToUserDtoConverter> converters;

    public UserDtoFactory(List<UserToUserDtoConverter> converters) {
        this.converters = converters;
    }


    public Class<? extends UserDto> getDtoClass(AppUser user) {
        return converters.stream()
                .filter(converter -> user.getClass().getSimpleName().toLowerCase(Locale.ROOT)
                        .equals(converter.getConverterType().toLowerCase(Locale.ROOT)))
                .findFirst()
                .map(UserToUserDtoConverter::getDtoType)
                .orElseThrow(() ->  new RuntimeException("No matching converter found for shape type: " + user.getClass().getSimpleName()));
    }

}
