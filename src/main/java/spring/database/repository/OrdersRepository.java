package spring.database.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import spring.database.entity.Order;

@Repository
public interface OrdersRepository extends JpaRepository<Order, Integer> {
}
