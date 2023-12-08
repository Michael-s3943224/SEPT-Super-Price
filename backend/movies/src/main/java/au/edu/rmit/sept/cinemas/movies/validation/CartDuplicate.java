package au.edu.rmit.sept.cinemas.movies.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = CartDuplicateValidator.class)
public @interface CartDuplicate {
    String message() default "cart has duplicate items";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
