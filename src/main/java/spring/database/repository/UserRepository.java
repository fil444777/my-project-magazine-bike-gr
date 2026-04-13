package spring.database.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;
import spring.database.entity.User;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>,
        FilterUserRepository, QuerydslPredicateExecutor<User> {

    Optional<User> findByEmail(String email);


}
