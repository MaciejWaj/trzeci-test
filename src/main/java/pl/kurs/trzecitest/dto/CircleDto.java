package pl.kurs.trzecitest.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CircleDto extends ShapeDto {

    private double radius;

    public CircleDto(int id, String type, int version, double radius, String createdBy, LocalDateTime createAt, LocalDateTime lastModifiedAt,
                     String lastModifiedBy, double area, double perimeter) {
        super(id, type, version, createdBy, createAt, lastModifiedAt, lastModifiedBy, area, perimeter);
        this.radius = radius;
    }
}
