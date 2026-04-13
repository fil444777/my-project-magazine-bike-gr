package spring.database.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import spring.database.entity.Motorcycle;

@Repository
public interface MotorcyclesRepository extends JpaRepository<Motorcycle, Integer> {
}
