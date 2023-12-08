package au.edu.rmit.sept.cinemas.movies.DTO;

import java.util.List;

public record DeliveryFormDropDownDTO(List<String> deliveryTimeSlot, List<String> deliveryMethod) {
}
