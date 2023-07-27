package pl.kurs.trzecitest.controller;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.kurs.trzecitest.convertertodto.UserDtoFactory;
import pl.kurs.trzecitest.dto.UserDto;
import pl.kurs.trzecitest.security.AppUser;
import pl.kurs.trzecitest.service.AppUserService;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final ModelMapper modelMapper;
    private final AppUserService appUserService;
    private final UserDtoFactory userDtoFactory;

    @GetMapping("/{username}")
    public ResponseEntity<UserDto> findUserByUsername(@PathVariable String username) {
        AppUser userByUsername = appUserService.findByUsername(username);
        return ResponseEntity.ok().body(mapToUserDto(userByUsername));
    }

    @GetMapping
    public List<UserDto> getUserByParameters(@RequestParam(required = false) Map<String, String> filterParam) {
        return appUserService.findBySpecification(filterParam).stream().map(this::mapToUserDto).collect(Collectors.toList());
    }

    public UserDto getByCreatedBy(AppUser user) {
        return mapToUserDto(user);
    }

    private UserDto mapToUserDto(AppUser user) {
        return modelMapper.map(user, userDtoFactory.getDtoClass(user));
    }
}