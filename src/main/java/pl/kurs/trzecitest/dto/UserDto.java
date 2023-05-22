package pl.kurs.trzecitest.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Builder
public class UserDto extends RepresentationModel<UserDto> {

    @JsonProperty("login")
    private String username;
    private LocalDateTime createdAccountAt;
    private LocalDateTime lastLogin;
    private int createdShape;


    public UserDto(String username, LocalDateTime createdAccountAt, LocalDateTime lastLogin, int createdShape) {
        this.username = username;
        this.createdAccountAt = createdAccountAt;
        this.lastLogin = lastLogin;
        this.createdShape = createdShape;
    }
}
