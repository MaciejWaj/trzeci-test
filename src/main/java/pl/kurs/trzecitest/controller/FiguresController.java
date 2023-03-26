package pl.kurs.trzecitest.controller;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.kurs.trzecitest.command.CreateFiguresCommand;
import pl.kurs.trzecitest.figuresdto.FiguresDto;
import pl.kurs.trzecitest.filtering.FiguresSpecifications;
import pl.kurs.trzecitest.convertertodto.FiguresDtoFactory;
import pl.kurs.trzecitest.model.Figures;
import pl.kurs.trzecitest.service.FiguresService;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/shapes")
public class FiguresController {

    private final FiguresService figuresService;
    private final ModelMapper modelMapper;
    private final FiguresDtoFactory figuresDtoFactory;


    @PostMapping
    public ResponseEntity save(@RequestBody CreateFiguresCommand command) {
        return ResponseEntity.status(HttpStatus.CREATED).body(toDto(figuresService.createFigures(command)));
    }


    @GetMapping
    public List<FiguresDto> getFiguresWithParam(@RequestParam(required = false) Map<String, String> filterParam) {
        Specification<Figures> spec = new FiguresSpecifications(filterParam);
        return figuresService.findAll(spec).stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }


    public FiguresDto toDto(Figures figures) {
        return modelMapper.map(figures, figuresDtoFactory.findConverterClass(figures).getClass());
    }

}