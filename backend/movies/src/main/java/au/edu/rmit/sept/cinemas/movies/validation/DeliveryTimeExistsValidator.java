package au.edu.rmit.sept.cinemas.movies.validation;
import au.edu.rmit.sept.cinemas.movies.repository.EnumRepository;
import au.edu.rmit.sept.cinemas.movies.repository.ProductRepository;
import au.edu.rmit.sept.cinemas.movies.service.UserService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

public class DeliveryTimeExistsValidator implements ConstraintValidator<DeliveryTimeExists, String>{
    @Autowired
    private EnumRepository repository;

    @Override
    public void initialize(DeliveryTimeExists constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String name, ConstraintValidatorContext context) {
        if (name == null) {
            return true;
        }
        return repository.getAllDeliveryTimeSlots().contains(name);
    }
}
