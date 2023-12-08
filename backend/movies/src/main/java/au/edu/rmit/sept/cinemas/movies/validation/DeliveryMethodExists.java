package au.edu.rmit.sept.cinemas.movies.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = DeliveryMethodExistsValidator.class)
public @interface DeliveryMethodExists {
    String message() default "delivery method doesnt exist";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
