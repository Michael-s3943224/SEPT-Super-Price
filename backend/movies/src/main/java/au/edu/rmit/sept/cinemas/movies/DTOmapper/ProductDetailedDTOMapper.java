package au.edu.rmit.sept.cinemas.movies.DTOmapper;

import au.edu.rmit.sept.cinemas.movies.DTO.*;
import au.edu.rmit.sept.cinemas.movies.models.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Function;

@Service
public class ProductDetailedDTOMapper implements Function<Product, ProductDetailedDTO> {

    @Autowired
    DealWithoutIdsDTOMapper dealWithoutIdsDTOMapper;

    @Autowired
    SupermarketWithoutDescDTOMapper supermarketWithoutDescDTOMapper;

    @Override
    public ProductDetailedDTO apply(Product product) {

        List<SupermarketPriceDealDTO> supermarketDeals = product
                .getSupermarketProducts()
                .stream()
                .map(sp -> {
                    SupermarketWithoutDescDTO supermarket = supermarketWithoutDescDTOMapper.apply(sp.getSupermarket());

                    List<DealWithoutIdsDTO> deals = sp.getDeals().stream().map(dealWithoutIdsDTOMapper).toList();
                    DealWithoutIdsDTO deal = (deals.size() > 0) ? deals.get(0) : null;

                    return new SupermarketPriceDealDTO(
                            supermarket,
                            sp.getPrice(),
                            deal);
                })
                .toList();

        return new ProductDetailedDTO(
                product.getId(),
                product.getName(),
                product.getDesc(),
                product.getCategory(),
                product.getBrand(),
                supermarketDeals
        );
    }
}