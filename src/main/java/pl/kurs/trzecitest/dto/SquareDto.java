package pl.kurs.trzecitest.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SquareDto extends ShapeDto {

    private double width;

    public SquareDto(int id, String type, int version, double width, String createdBy, LocalDateTime createAt, LocalDateTime lastModifiedAt, String lastModifiedBy, double area, double perimeter) {
        super(id, type, version, createdBy, createAt, lastModifiedAt, lastModifiedBy, area, perimeter);
        this.width = width;
    }
}
