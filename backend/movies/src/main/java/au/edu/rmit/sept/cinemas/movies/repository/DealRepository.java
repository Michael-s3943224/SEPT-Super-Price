package au.edu.rmit.sept.cinemas.movies.repository;

import au.edu.rmit.sept.cinemas.movies.models.Deal;
import au.edu.rmit.sept.cinemas.movies.models.DealKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface DealRepository extends JpaRepository<Deal, DealKey> {

    @Query(
        nativeQuery = true,
        value =
        "SELECT * FROM deal\n" +
        "WHERE supermarket_id = :supermarketId AND\n" +
        "product_id = :productId"
    )
    Optional<Deal> findDealById(@Param("supermarketId") Long supermarketId, @Param("productId") Long productId);
}
