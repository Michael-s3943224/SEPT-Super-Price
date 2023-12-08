package au.edu.rmit.sept.cinemas.movies.repository;

import au.edu.rmit.sept.cinemas.movies.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query(
        nativeQuery = true,
        value =
        "SELECT DISTINCT(category) FROM product;"
    )
    public List<String> getDistinctCategories();
}
