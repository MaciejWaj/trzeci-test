package pl.kurs.trzecitest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import pl.kurs.trzecitest.model.Shape;

import java.util.List;
import java.util.Optional;

public interface ShapeRepository extends JpaRepository<Shape, Integer>, JpaSpecificationExecutor<Shape> {

    int countByCreatedById(int userId);

    List<Shape> findByCreatedById(int user);

    Optional<Shape> findByIdAndCreatedById(int id, int userId);

}
