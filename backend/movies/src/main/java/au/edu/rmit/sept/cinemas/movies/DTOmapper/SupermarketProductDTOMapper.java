package au.edu.rmit.sept.cinemas.movies.DTOmapper;

import au.edu.rmit.sept.cinemas.movies.models.SupermarketProduct;
import au.edu.rmit.sept.cinemas.movies.DTO.DealWithoutIdsDTO;
import au.edu.rmit.sept.cinemas.movies.DTO.ProductWithoutDescDTO;
import au.edu.rmit.sept.cinemas.movies.DTO.SupermarketProductDTO;
import au.edu.rmit.sept.cinemas.movies.DTO.SupermarketWithoutDescDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class SupermarketProductDTOMapper implements Function<SupermarketProduct, SupermarketProductDTO> {

    @Autowired
    private ProductWithoutDescDTOMapper productWithoutDescDTOMapper;

    @Autowired
    private SupermarketWithoutDescDTOMapper supermarketWithoutDescDTOMapper;

    @Autowired
    private DealWithoutIdsDTOMapper dealWithoutIdsDTOMapper;

    @Override
    public SupermarketProductDTO apply(SupermarketProduct item) {
        SupermarketWithoutDescDTO supermarket =
                supermarketWithoutDescDTOMapper.apply(item.getSupermarket());

        ProductWithoutDescDTO product =
                productWithoutDescDTOMapper.apply(item.getProduct());

        List<DealWithoutIdsDTO> deals = item.getDeals().stream().map(dealWithoutIdsDTOMapper).toList();
        DealWithoutIdsDTO deal = (deals.size() > 0) ? deals.get(0) : null;

        BigDecimal price = item.getPrice();
        return new SupermarketProductDTO(supermarket, product, deal, price);
    }
}
