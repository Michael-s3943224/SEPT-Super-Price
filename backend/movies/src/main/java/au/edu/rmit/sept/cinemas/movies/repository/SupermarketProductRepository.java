package au.edu.rmit.sept.cinemas.movies.repository;

import au.edu.rmit.sept.cinemas.movies.models.SupermarketProduct;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface SupermarketProductRepository extends JpaRepository<SupermarketProduct, Long> {

    /* this only works for mysql */
    @Query(
        nativeQuery = true,
        countQuery =
        "SELECT COUNT(*) " +
        "FROM supermarket_product AS i " +
        "INNER JOIN supermarket AS s ON i.supermarket_id = s.id " +
        "INNER JOIN product AS p ON i.product_id = p.id",
        value =
        "SELECT i.price, i.supermarket_id, i.product_id, p.name AS product_name " +
        "FROM supermarket_product AS i " +
        "INNER JOIN supermarket AS s ON i.supermarket_id = s.id " +
        "INNER JOIN product AS p ON i.product_id = p.id " +
        "WHERE (LOWER(p.name) LIKE CONCAT('%',LOWER(:term),'%') " +
        "OR LOWER(p.brand) LIKE CONCAT('%',LOWER(:term),'%')) " +
        "AND (:brand IS NULL OR FIND_IN_SET(LOWER(p.brand), :brand)) " +
        "AND (:category IS NULL OR FIND_IN_SET(LOWER(p.category), :category)) " +
        "AND (:supermarket IS NULL OR FIND_IN_SET(LOWER(s.name), :supermarket)) " +
        "AND (:priceAbove IS NULL OR i.price >= :priceAbove) " +
        "AND (:priceBelow IS NULL OR i.price <= :priceBelow)"
    )
    List<SupermarketProduct> findSupermarketProductByFilters(
            @Param("term") String term,
            @Param("brand") String brand,
            @Param("category") String category,
            @Param("supermarket") String supermarket,
            @Param("priceAbove") BigDecimal priceAbove,
            @Param("priceBelow") BigDecimal priceBelow,
            Pageable pageable
            );

    /* If you want to use H2, uncomment below and comment above
        The below query does not work my mysql due to the way mysql handles
        list query parameters
     */
    /*
    @Query(
            nativeQuery = true,
            countQuery =
            "SELECT COUNT(*) " +
            "FROM supermarket_product AS i " +
            "INNER JOIN supermarket AS s ON i.supermarket_id = s.id " +
            "INNER JOIN product AS p ON i.product_id = p.id",
            value =
            "SELECT i.price, i.supermarket_id, i.product_id, p.name AS product_name " +
            "FROM supermarket_product AS i " +
            "INNER JOIN supermarket AS s ON i.supermarket_id = s.id " +
            "INNER JOIN product AS p ON i.product_id = p.id " +
            "WHERE (LOWER(p.name) LIKE CONCAT('%',LOWER(:term),'%') " +
            "OR LOWER(p.brand) LIKE CONCAT('%',LOWER(:term),'%')) " +
            "AND (:brand IS NULL OR LOWER(p.brand) IN (:brand)) " +
            "AND (:category IS NULL OR LOWER(p.category) IN (:category)) " +
            "AND (:supermarket IS NULL OR LOWER(s.name) IN (:supermarket)) " +
            "AND (:priceAbove IS NULL OR i.price >= :priceAbove) " +
            "AND (:priceBelow IS NULL OR i.price <= :priceBelow)
    )
    List<SupermarketProduct> findSupermarketProductByFilters(
            @Param("term") String term,
            @Param("brand") String brand,
            @Param("category") String category,
            @Param("supermarket") String supermarket,
            @Param("priceAbove") BigDecimal priceAbove,
            @Param("priceBelow") BigDecimal priceBelow,
            Pageable pageable
    );
    */

    @Query(
        nativeQuery = true,
        value =
        "SELECT i.price, i.supermarket_id, i.product_id, d.from_date, d.to_date " +
        "FROM supermarket_product AS i " +
        "INNER JOIN deal AS d " +
        "ON i.supermarket_id = d.supermarket_id " +
        "AND i.product_id = d.product_id " +
        "WHERE CURRENT_DATE() BETWEEN d.from_date AND d.to_date;"
    )
    List<SupermarketProduct> findSupermarketProductsWithDeals();

    @Query(
        nativeQuery = true,
        value =
        "SELECT t.brand FROM\n" +
        "(SELECT p.brand, COUNT(*) AS c\n" +
        "FROM supermarket_product AS i\n" +
        "INNER JOIN supermarket AS s ON i.supermarket_id = s.id\n" +
        "INNER JOIN product AS p ON i.product_id = p.id\n" +
        "GROUP BY p.brand\n" +
        "ORDER BY c DESC, p.brand ASC\n" +
        "LIMIT 5) AS t;"
    )
    List<String> getTopFivePopularBrandNames();

    @Query(
        nativeQuery = true,
        value =
        "SELECT i.price, i.supermarket_id, i.product_id\n" +
        "FROM supermarket_product AS i\n" +
        "WHERE i.supermarket_id = :supermarketId AND i.product_id = :productId"
    )
    Optional<SupermarketProduct> getSupermarketProductById(@Param("supermarketId") Long supermarketId, @Param("productId") Long productId);
}
