package au.edu.rmit.sept.cinemas.movies.DTO;

import au.edu.rmit.sept.cinemas.movies.validation.CartDuplicate;
import au.edu.rmit.sept.cinemas.movies.validation.NotEmptyList;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public class DeliverySubmissionDTO {

    @Valid
    DeliveryDetailsDTO deliveryDetails;

    @NotEmptyList(message = "delivery cart cannot be empty")
    @CartDuplicate(message = "delivery cart has duplicate items")
    List<@Valid DeliveryCartItemDTO> deliveryCart;

    public DeliverySubmissionDTO() {
    }

    public DeliverySubmissionDTO(DeliveryDetailsDTO deliveryDetails, List<DeliveryCartItemDTO> deliveryCart) {
        this.deliveryDetails = deliveryDetails;
        this.deliveryCart = deliveryCart;
    }

    public DeliveryDetailsDTO getDeliveryDetails() {
        return deliveryDetails;
    }

    public List<DeliveryCartItemDTO> getDeliveryCart() {
        return deliveryCart;
    }
}
