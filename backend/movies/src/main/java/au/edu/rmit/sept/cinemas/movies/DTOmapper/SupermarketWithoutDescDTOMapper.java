package au.edu.rmit.sept.cinemas.movies.DTOmapper;

import au.edu.rmit.sept.cinemas.movies.DTO.ProductWithoutDescDTO;
import au.edu.rmit.sept.cinemas.movies.DTO.SupermarketWithoutDescDTO;
import au.edu.rmit.sept.cinemas.movies.models.Product;
import au.edu.rmit.sept.cinemas.movies.models.Supermarket;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class SupermarketWithoutDescDTOMapper implements Function<Supermarket, SupermarketWithoutDescDTO> {
    @Override
    public SupermarketWithoutDescDTO apply(Supermarket supermarket) {
        return new SupermarketWithoutDescDTO(
                supermarket.getId(),
                supermarket.getName()
        );
    }
}
