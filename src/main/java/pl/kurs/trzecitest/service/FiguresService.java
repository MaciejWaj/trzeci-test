package pl.kurs.trzecitest.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.kurs.trzecitest.command.CreateFiguresCommand;
import pl.kurs.trzecitest.factory.creators.FiguresFactory;
import pl.kurs.trzecitest.model.Figures;
import pl.kurs.trzecitest.repository.FiguresRepository;

import java.util.List;


@Service
@RequiredArgsConstructor
public class FiguresService {

    private final FiguresRepository figuresRepository;
    private final FiguresFactory figuresFactory;


    @Transactional
    public Figures createFigures(CreateFiguresCommand command) {
        return figuresRepository.saveAndFlush(figuresFactory.create(command));
    }


    public List<Figures> findAll(Specification<Figures> spec) {
        return figuresRepository.findAll(spec);
    }
}
