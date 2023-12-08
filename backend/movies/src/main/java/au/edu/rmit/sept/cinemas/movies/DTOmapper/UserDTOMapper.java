package au.edu.rmit.sept.cinemas.movies.DTOmapper;

import au.edu.rmit.sept.cinemas.movies.DTO.SupermarketWithoutDescDTO;
import au.edu.rmit.sept.cinemas.movies.DTO.UserDTO;
import au.edu.rmit.sept.cinemas.movies.models.Supermarket;
import au.edu.rmit.sept.cinemas.movies.models.User;

import java.util.function.Function;

public class UserDTOMapper implements Function<User, UserDTO> {
    @Override
    public UserDTO apply(User user) {
        return new UserDTO(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getMobile(),
                user.getDeliveryAddress(),
                user.getDeliveryTime(),
                user.getDeliveryMethod()
        );
    }
}
