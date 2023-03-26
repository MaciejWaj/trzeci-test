package pl.kurs.trzecitest.figuresdto;

import lombok.*;


import javax.persistence.Column;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CircleDto extends FiguresDto{

    private int id;
    private String type;
    @Column(name = "radius")
    private double radius;
    private int version;
    private String createdBy;
    private LocalDateTime createAt;
    private LocalDateTime lastModifiedAt;
    private String lastModifiedBy;
    private double area;
    private double perimeter;

}
