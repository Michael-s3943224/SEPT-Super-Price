package au.edu.rmit.sept.cinemas.movies.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = DeliveryTimeExistsValidator.class)
public @interface DeliveryTimeExists {
    String message() default "delivery time doesnt exist";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
