package au.edu.rmit.sept.cinemas.movies.DTO;

import java.sql.Time;

public record UserDTO(
        Long id,
        String name,
        String email,
        String mobile,
        String deliveryAddress,
        String deliveryTime,
        String deliveryMethod
) {
}
