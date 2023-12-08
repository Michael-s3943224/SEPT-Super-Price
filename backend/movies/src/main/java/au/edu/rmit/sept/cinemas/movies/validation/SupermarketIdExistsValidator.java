package au.edu.rmit.sept.cinemas.movies.validation;

import au.edu.rmit.sept.cinemas.movies.repository.ProductRepository;
import au.edu.rmit.sept.cinemas.movies.repository.SupermarketProductRepository;
import au.edu.rmit.sept.cinemas.movies.repository.SupermarketRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

public class SupermarketIdExistsValidator implements ConstraintValidator<SupermarketIdExists, Long> {
    @Autowired
    private SupermarketRepository supermarketRepository;

    @Override
    public void initialize(SupermarketIdExists constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Long id, ConstraintValidatorContext context) {
        return !supermarketRepository.findById(id).isEmpty();
    }
}
