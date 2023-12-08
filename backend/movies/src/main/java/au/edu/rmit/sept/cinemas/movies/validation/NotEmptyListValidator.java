package au.edu.rmit.sept.cinemas.movies.validation;
import au.edu.rmit.sept.cinemas.movies.repository.ProductRepository;
import au.edu.rmit.sept.cinemas.movies.service.UserService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Objects;

public class NotEmptyListValidator implements ConstraintValidator<NotEmptyList, List<? extends Object>>{

    @Override
    public void initialize(NotEmptyList constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(List<? extends Object> list, ConstraintValidatorContext context) {
        return !(list == null || list.isEmpty() || list.stream().anyMatch(Objects::isNull));
    }
}
