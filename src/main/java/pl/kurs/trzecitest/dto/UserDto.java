package pl.kurs.trzecitest.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto extends RepresentationModel<UserDto> {

    @JsonProperty("login")
    private String username;
    private LocalDateTime createdAccountAt;
    private LocalDateTime lastLogin;
    private int createdShape;

}
