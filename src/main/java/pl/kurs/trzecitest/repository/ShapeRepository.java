package pl.kurs.trzecitest.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import pl.kurs.trzecitest.model.Shape;

public interface ShapeRepository  extends JpaRepository<Shape, Integer>, JpaSpecificationExecutor<Shape> {

}
