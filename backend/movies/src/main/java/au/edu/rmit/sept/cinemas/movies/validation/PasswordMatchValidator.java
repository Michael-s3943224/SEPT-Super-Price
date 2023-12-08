package au.edu.rmit.sept.cinemas.movies.validation;

import au.edu.rmit.sept.cinemas.movies.DTO.PasswordConfirm;
import au.edu.rmit.sept.cinemas.movies.DTO.RegisterRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordMatchValidator implements ConstraintValidator<PasswordMatch, Object> {

    private String message;
    private String field;
    @Override
    public void initialize(PasswordMatch passwordMatch) {
        this.field = passwordMatch.field();
        this.message = passwordMatch.message();
    }

    private void addPasswordConfirmToErrorMap(ConstraintValidatorContext context) {
        context.disableDefaultConstraintViolation();
        context
                .buildConstraintViolationWithTemplate(message)
                .addPropertyNode(field).addConstraintViolation();
    }

    @Override
    public boolean isValid(Object obj, ConstraintValidatorContext context) {
        if (obj == null) {
            addPasswordConfirmToErrorMap(context);
            return false;
        }

        PasswordConfirm request = (PasswordConfirm) obj;

        if (request.getPassword() == null) {
            return true;
        }

        boolean matches = request.getPassword().equals(request.getPasswordConfirm());

        if (!matches) {
            addPasswordConfirmToErrorMap(context);
        };

        return matches;
    }
}
