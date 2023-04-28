package pl.kurs.trzecitest.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RectangleDto extends ShapeDto {

    private double height;
    private double length;

    public RectangleDto(int id, String type, int version, double height, double length, String createdBy, LocalDateTime createAt, LocalDateTime lastModifiedAt, String lastModifiedBy, double area, double perimeter) {
        super(id, type, version, createdBy, createAt, lastModifiedAt, lastModifiedBy, area, perimeter);
        this.height = height;
        this.length = length;
    }
}
