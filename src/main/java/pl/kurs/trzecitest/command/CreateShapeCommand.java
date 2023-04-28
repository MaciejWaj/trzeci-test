package pl.kurs.trzecitest.command;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Map;

@Getter
@Setter
@ToString
public class CreateShapeCommand {

    private String type;
    private Map<String, String> parameters;

}
