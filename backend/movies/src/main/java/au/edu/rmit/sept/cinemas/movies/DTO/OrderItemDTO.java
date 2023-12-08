package au.edu.rmit.sept.cinemas.movies.DTO;

import au.edu.rmit.sept.cinemas.movies.models.Product;

import java.math.BigDecimal;

public record OrderItemDTO(
    SupermarketWithoutDescDTO supermarket,
    ProductWithoutDescDTO product,
    BigDecimal discountPrice,
    Integer quantity
) { }
