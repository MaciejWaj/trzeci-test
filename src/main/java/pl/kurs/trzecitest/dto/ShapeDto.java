package pl.kurs.trzecitest.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public abstract class ShapeDto {

        private int id;
        private String type;
        private int version;
        private String createdBy;
        private LocalDateTime createAt;
        private LocalDateTime lastModifiedAt;
        private String lastModifiedBy;

        protected double area;
        protected double perimeter;


}
