package au.edu.rmit.sept.cinemas.movies.repository;

import au.edu.rmit.sept.cinemas.movies.models.Supermarket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SupermarketRepository extends JpaRepository<Supermarket, Long> {
    @Query(
        nativeQuery = true,
        value =
        "SELECT DISTINCT(name) FROM supermarket;"
    )
    List<String> getDistinctSupermarketNames();
}
