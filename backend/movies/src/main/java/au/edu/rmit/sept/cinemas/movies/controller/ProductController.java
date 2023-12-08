package au.edu.rmit.sept.cinemas.movies.controller;

import au.edu.rmit.sept.cinemas.movies.DTO.ProductDTO;
import au.edu.rmit.sept.cinemas.movies.DTO.ProductDetailedDTO;
import au.edu.rmit.sept.cinemas.movies.DTO.ProductWithoutDescDTO;
import au.edu.rmit.sept.cinemas.movies.DTOmapper.ProductDTOMapper;
import au.edu.rmit.sept.cinemas.movies.DTOmapper.ProductDetailedDTOMapper;
import au.edu.rmit.sept.cinemas.movies.DTOmapper.ProductWithoutDescDTOMapper;
import au.edu.rmit.sept.cinemas.movies.models.Product;
import au.edu.rmit.sept.cinemas.movies.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    @Autowired
    private ProductRepository repository;

    @Autowired
    private ProductDetailedDTOMapper productDetailedDTOMapper;

    @Autowired
    private ProductWithoutDescDTOMapper productWithoutDescDTOMapper;

    @GetMapping
    public List<ProductWithoutDescDTO> getAllProducts()
    {
        return repository.findAll()
                .stream()
                .map(productWithoutDescDTOMapper)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public Optional<ProductDetailedDTO> getProductById(@PathVariable Long id)
    {
        return repository.findById(id).map(productDetailedDTOMapper);
    }
}
