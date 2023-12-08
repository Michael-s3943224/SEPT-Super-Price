package au.edu.rmit.sept.cinemas.movies.DTO;

import java.util.List;

public record OrderDTO(
    DeliveryDetailsDTO details,
    List<OrderItemDTO> cart
) { }
