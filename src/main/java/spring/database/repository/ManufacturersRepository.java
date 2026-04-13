package spring.database.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import spring.database.entity.Manufacturer;

@Repository
public interface ManufacturersRepository extends JpaRepository<Manufacturer, Integer> {
}
