package au.edu.rmit.sept.cinemas.movies.validation;

import au.edu.rmit.sept.cinemas.movies.service.UserService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UniqueUserEmailValidator implements ConstraintValidator<UniqueUserEmail, String> {

    @Autowired
    private UserService userService;

    @Override
    public void initialize(UniqueUserEmail constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
        if (email == null) {
            return true;
        }
        return !userService.emailExists(email);
    }
}
