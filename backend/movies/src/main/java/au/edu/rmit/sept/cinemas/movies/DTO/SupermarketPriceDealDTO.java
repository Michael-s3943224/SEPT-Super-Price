package au.edu.rmit.sept.cinemas.movies.DTO;

import java.math.BigDecimal;

public record SupermarketPriceDealDTO(
        SupermarketWithoutDescDTO supermarket,
        BigDecimal price,
        DealWithoutIdsDTO deal
) { }
