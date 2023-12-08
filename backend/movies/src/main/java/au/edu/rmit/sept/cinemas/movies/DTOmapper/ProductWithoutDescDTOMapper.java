package au.edu.rmit.sept.cinemas.movies.DTOmapper;

import au.edu.rmit.sept.cinemas.movies.DTO.ProductWithoutDescDTO;
import au.edu.rmit.sept.cinemas.movies.DTO.SupermarketProductDTO;
import au.edu.rmit.sept.cinemas.movies.models.Product;
import au.edu.rmit.sept.cinemas.movies.models.SupermarketProduct;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class ProductWithoutDescDTOMapper implements Function<Product, ProductWithoutDescDTO> {
    @Override
    public ProductWithoutDescDTO apply(Product product) {
        return new ProductWithoutDescDTO(
                product.getId(),
                product.getName(),
                product.getCategory(),
                product.getBrand()
        );
    }
}
