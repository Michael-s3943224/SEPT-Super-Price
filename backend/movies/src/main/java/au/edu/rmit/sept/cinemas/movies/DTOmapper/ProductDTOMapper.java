package au.edu.rmit.sept.cinemas.movies.DTOmapper;

import au.edu.rmit.sept.cinemas.movies.DTO.ProductDTO;
import au.edu.rmit.sept.cinemas.movies.models.Product;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class ProductDTOMapper implements Function<Product, ProductDTO> {

    @Override
    public ProductDTO apply(Product product) {
        return new ProductDTO(
                product.getId(),
                product.getName(),
                product.getDesc(),
                product.getCategory(),
                product.getBrand()
        );
    }
}
