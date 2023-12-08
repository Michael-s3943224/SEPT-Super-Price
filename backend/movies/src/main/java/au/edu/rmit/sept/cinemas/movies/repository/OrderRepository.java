package au.edu.rmit.sept.cinemas.movies.repository;


import au.edu.rmit.sept.cinemas.movies.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {
    @Query(
        nativeQuery = true,
        value = "SELECT * FROM product_order WHERE email = :email"
    )
    List<Order> findOrdersByEmail(@Param("email") String email);
}
