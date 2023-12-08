package au.edu.rmit.sept.cinemas.movies.DTO;

public record ProductDTO (
        Long id,
        String name,
        String desc,
        String category,
        String brand
) { }
