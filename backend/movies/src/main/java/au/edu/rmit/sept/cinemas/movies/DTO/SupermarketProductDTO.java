package au.edu.rmit.sept.cinemas.movies.DTO;


import java.math.BigDecimal;

public record SupermarketProductDTO(
        SupermarketWithoutDescDTO supermarket,
        ProductWithoutDescDTO product,
        DealWithoutIdsDTO deal,
        BigDecimal price
) { }
