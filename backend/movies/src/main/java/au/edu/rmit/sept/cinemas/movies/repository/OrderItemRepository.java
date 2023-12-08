package au.edu.rmit.sept.cinemas.movies.repository;

import au.edu.rmit.sept.cinemas.movies.models.OrderItem;
import au.edu.rmit.sept.cinemas.movies.models.OrderItemKey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, OrderItemKey> {
}
