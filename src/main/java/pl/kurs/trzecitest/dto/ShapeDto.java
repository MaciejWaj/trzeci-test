package pl.kurs.trzecitest.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public abstract class ShapeDto extends RepresentationModel<ShapeDto> {

    private Integer id;
    private String type;
    private Integer version;
    private String createdBy;
    private LocalDateTime createAt;
    private LocalDateTime lastModifiedAt;
    private String lastModifiedBy;

    protected double area;
    protected double perimeter;
}
