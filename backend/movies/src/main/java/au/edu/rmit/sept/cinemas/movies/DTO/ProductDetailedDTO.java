package au.edu.rmit.sept.cinemas.movies.DTO;

import java.util.List;

public record ProductDetailedDTO(
    Long id,
    String name,
    String desc,
    String category,

    String brand,

    List<SupermarketPriceDealDTO> supermarkets
) { }
