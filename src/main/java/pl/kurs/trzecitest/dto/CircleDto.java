package pl.kurs.trzecitest.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CircleDto extends ShapeDto {

    private double radius;

    @Builder
    public CircleDto(int id, String type, Integer version, double radius, String createdBy, LocalDateTime createAt, LocalDateTime lastModifiedAt,
                     String lastModifiedBy, double area, double perimeter) {
        super(id, type, version, createdBy, createAt, lastModifiedAt, lastModifiedBy, area, perimeter);
        this.radius = radius;
    }
}
