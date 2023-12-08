package au.edu.rmit.sept.cinemas.movies.DTO;

import java.util.List;

public record ItemsFilterOptionsDTO(List<String> brand, List<String> supermarket, List<String> category)
{
}
