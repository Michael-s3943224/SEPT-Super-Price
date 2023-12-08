package au.edu.rmit.sept.cinemas.movies.validation;
import au.edu.rmit.sept.cinemas.movies.repository.ProductRepository;
import au.edu.rmit.sept.cinemas.movies.service.UserService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

public class ProductIdExistsValidator implements ConstraintValidator<ProductIdExists, Long>{
    @Autowired
    private ProductRepository productRepository;

    @Override
    public void initialize(ProductIdExists constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Long id, ConstraintValidatorContext context) {
        return !productRepository.findById(id).isEmpty();
    }
}
