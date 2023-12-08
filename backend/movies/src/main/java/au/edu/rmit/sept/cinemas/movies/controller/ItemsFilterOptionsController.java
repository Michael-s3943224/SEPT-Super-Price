package au.edu.rmit.sept.cinemas.movies.controller;

import au.edu.rmit.sept.cinemas.movies.DTO.ItemsFilterOptionsDTO;
import au.edu.rmit.sept.cinemas.movies.repository.ProductRepository;
import au.edu.rmit.sept.cinemas.movies.repository.SupermarketProductRepository;
import au.edu.rmit.sept.cinemas.movies.repository.SupermarketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/items/filter-options")
public class ItemsFilterOptionsController {
    @Autowired
    private SupermarketRepository supermarketRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private SupermarketProductRepository supermarketProductRepository;

    @GetMapping
    public ItemsFilterOptionsDTO getSupermarketFilterOptions() {
        return new ItemsFilterOptionsDTO(
                supermarketProductRepository.getTopFivePopularBrandNames(),
                supermarketRepository.getDistinctSupermarketNames(),
                productRepository.getDistinctCategories()
        );
    }

}
