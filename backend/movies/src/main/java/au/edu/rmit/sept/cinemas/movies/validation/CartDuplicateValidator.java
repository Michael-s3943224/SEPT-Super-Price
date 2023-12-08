package au.edu.rmit.sept.cinemas.movies.validation;
import au.edu.rmit.sept.cinemas.movies.DTO.DeliveryCartItemDTO;
import au.edu.rmit.sept.cinemas.movies.DTO.DeliveryDetailsDTO;
import au.edu.rmit.sept.cinemas.movies.repository.EnumRepository;
import au.edu.rmit.sept.cinemas.movies.repository.ProductRepository;
import au.edu.rmit.sept.cinemas.movies.service.UserService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;

import java.util.*;

public class CartDuplicateValidator implements ConstraintValidator<CartDuplicate, List<DeliveryCartItemDTO>>{

    @Override
    public void initialize(CartDuplicate constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(List<DeliveryCartItemDTO> cart, ConstraintValidatorContext context) {
        if (cart == null) {
            return true;
        }
        Set<Pair<Long, Long>> seenBefore = new HashSet<>();
        for (DeliveryCartItemDTO item: cart) {
            var pair = Pair.of(item.getSupermarketId(), item.getProductId());
            if (seenBefore.contains(pair)) {
                return false;
            }
            seenBefore.add(pair);
        }
        return true;
    }
}
