package au.edu.rmit.sept.cinemas.movies.DTOmapper;

import au.edu.rmit.sept.cinemas.movies.DTO.SupermarketDTO;
import au.edu.rmit.sept.cinemas.movies.models.Supermarket;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class SupermarketDTOMapper implements Function<Supermarket, SupermarketDTO> {

    @Override
    public SupermarketDTO apply(Supermarket supermarket) {
        return new SupermarketDTO(
                supermarket.getId(),
                supermarket.getName(),
                supermarket.getDesc()
        );
    }
}
