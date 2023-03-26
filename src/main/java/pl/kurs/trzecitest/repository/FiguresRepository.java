package pl.kurs.trzecitest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import pl.kurs.trzecitest.model.Figures;


public interface FiguresRepository extends JpaRepository<Figures, Integer>, JpaSpecificationExecutor<Figures> {


}
