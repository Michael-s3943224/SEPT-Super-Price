package au.edu.rmit.sept.cinemas.movies.DTOmapper;

import au.edu.rmit.sept.cinemas.movies.DTO.DealWithoutIdsDTO;
import au.edu.rmit.sept.cinemas.movies.DTO.ProductDetailedDTO;
import au.edu.rmit.sept.cinemas.movies.DTO.SupermarketWithoutDescDTO;
import au.edu.rmit.sept.cinemas.movies.models.Deal;
import au.edu.rmit.sept.cinemas.movies.models.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Function;

@Service
public class DealWithoutIdsDTOMapper implements Function<Deal, DealWithoutIdsDTO> {
    @Override
    public DealWithoutIdsDTO apply(Deal deal) {
        return new DealWithoutIdsDTO(
                deal.getReductionValue(),
                deal.getReductionType(),
                deal.getId().getFromDate(),
                deal.getId().getToDate()
        );
    }
}