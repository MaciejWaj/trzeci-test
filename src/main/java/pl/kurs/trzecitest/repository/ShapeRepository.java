package pl.kurs.trzecitest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import pl.kurs.trzecitest.model.Shape;

import java.util.Optional;

public interface ShapeRepository extends JpaRepository<Shape, Integer>, JpaSpecificationExecutor<Shape> {

    @Query(value = "SELECT s FROM Shape s WHERE s.id = :id AND s.createdBy.username = :username")
    Optional<Shape> findByIdAndCreatedBy(int id, String username);

}