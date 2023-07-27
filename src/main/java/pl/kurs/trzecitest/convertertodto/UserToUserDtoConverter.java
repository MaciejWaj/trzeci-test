package pl.kurs.trzecitest.convertertodto;

import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;
import org.springframework.stereotype.Component;
import pl.kurs.trzecitest.controller.ShapeController;
import pl.kurs.trzecitest.dto.UserDto;
import pl.kurs.trzecitest.security.AppUser;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class UserToUserDtoConverter implements Converter<AppUser, UserDto>, UserToUserDto{

    @Override
    public UserDto convert(MappingContext<AppUser, UserDto> mappingContext) {
        AppUser source = mappingContext.getSource();
        UserDto dto = UserDto.builder()
                .username(source.getUsername())
                .createdAccountAt(source.getCreatedAccountAt())
                .lastLogin(source.getLastLogin())
                .createdShape(source.getShape().size())
                .build();

        dto.add(linkTo((methodOn(ShapeController.class).getListOfShapeCreatedBy(source))
        ).withRel("List of created shape"));

        return dto;
    }

    @Override
    public String getConverterType() {
        return "AppUser";
    }

    @Override
    public Class<? extends UserDto> getDtoType() {
        return UserDto.class;
    }
}
