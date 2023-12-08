package au.edu.rmit.sept.cinemas.movies.controller;

import au.edu.rmit.sept.cinemas.movies.DTO.ProductDetailedDTO;
import au.edu.rmit.sept.cinemas.movies.repository.SupermarketProductRepository;
import au.edu.rmit.sept.cinemas.movies.DTO.SupermarketProductDTO;
import au.edu.rmit.sept.cinemas.movies.DTOmapper.SupermarketProductDTOMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/items")
public class SupermarketProductController {
    @Autowired
    private SupermarketProductRepository repository;

    @Autowired
    private SupermarketProductDTOMapper supermarketProductDTOMapper;

    @GetMapping
    public List<SupermarketProductDTO> getAllSupermarketProducts()
    {
        return repository.findAll()
                .stream()
                .map(supermarketProductDTOMapper).collect(Collectors.toList());
    }

    @GetMapping("/deals")
    public List<SupermarketProductDTO> getAllSupermarketProductsWithDeals()
    {
        return repository.findSupermarketProductsWithDeals()
                .stream()
                .map(supermarketProductDTOMapper).collect(Collectors.toList());
    }

    @GetMapping("/browse")
    public List<SupermarketProductDTO> getAllSupermarketProductsByFilter(
            @RequestParam String term,
            @RequestParam(required = false) List<String> brand,
            @RequestParam(required = false) List<String> category,
            @RequestParam(required = false) List<String> supermarket,
            @RequestParam(required = false) BigDecimal priceAbove,
            @RequestParam(required = false) BigDecimal priceBelow,
            @RequestParam(defaultValue = "product_name") String sortBy,
            @RequestParam(defaultValue = "asc") String orderBy,
            @RequestParam(defaultValue = "0") Integer page
    )
    {
        Sort.Direction direction = orderBy.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        int itemsPerPage = 9;

        return repository.findSupermarketProductByFilters(
                    term,
                    (brand == null) ? null : String.join(",",brand.stream().map(String::toLowerCase).collect(Collectors.toList())),
                    (category == null) ? null : String.join(",",category.stream().map(String::toLowerCase).collect(Collectors.toList())),
                    (supermarket == null) ? null : String.join(",", supermarket.stream().map(String::toLowerCase).collect(Collectors.toList())),
                    priceAbove,
                    priceBelow,
                    PageRequest.of(page, itemsPerPage, direction, sortBy)
                    )
                .stream()
                .map(supermarketProductDTOMapper)
                .collect(Collectors.toList());
    }

}
