package au.edu.rmit.sept.cinemas.movies.DTO;

public record ProductWithoutDescDTO(
        Long id,
        String name,
        String category,
        String brand
) { }
