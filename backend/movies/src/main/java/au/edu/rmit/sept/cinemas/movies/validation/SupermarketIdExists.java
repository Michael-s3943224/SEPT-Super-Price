package au.edu.rmit.sept.cinemas.movies.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = SupermarketIdExistsValidator.class)
public @interface SupermarketIdExists {
    String message() default "supermarket id doesn't exist";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
