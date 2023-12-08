package au.edu.rmit.sept.cinemas.movies.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = ProductIdExistsValidator.class)
public @interface ProductIdExists {
    String message() default "product id doesn't exist";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
