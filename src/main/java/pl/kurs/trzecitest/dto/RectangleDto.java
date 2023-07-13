package pl.kurs.trzecitest.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RectangleDto extends ShapeDto {

    private double height;
    private double length;

    @Builder
    public RectangleDto(int id, String type, Integer version, double height, double length, String createdBy, LocalDateTime createAt, LocalDateTime lastModifiedAt, String lastModifiedBy, double area, double perimeter) {
        super(id, type, version, createdBy, createAt, lastModifiedAt, lastModifiedBy, area, perimeter);
        this.height = height;
        this.length = length;
    }
}
