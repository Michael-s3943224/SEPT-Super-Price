package au.edu.rmit.sept.cinemas.movies.controller;

import au.edu.rmit.sept.cinemas.movies.DTO.SupermarketDTO;
import au.edu.rmit.sept.cinemas.movies.DTO.SupermarketWithoutDescDTO;
import au.edu.rmit.sept.cinemas.movies.DTOmapper.SupermarketDTOMapper;
import au.edu.rmit.sept.cinemas.movies.DTOmapper.SupermarketWithoutDescDTOMapper;
import au.edu.rmit.sept.cinemas.movies.repository.SupermarketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/supermarkets")
public class SupermaketController {

    @Autowired
    private SupermarketRepository repository;

    @Autowired
    private SupermarketDTOMapper supermarketDTOMapper;

    @Autowired
    private SupermarketWithoutDescDTOMapper supermarketWithoutDescDTOMapper;

    @GetMapping
    public List<SupermarketWithoutDescDTO> getAllSupermarkets()
    {
        return repository.findAll()
                .stream()
                .map(supermarketWithoutDescDTOMapper)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public Optional<SupermarketDTO> getSupermarketById(@PathVariable Long id)
    {
        return repository.findById(id).map(supermarketDTOMapper);
    }
}
