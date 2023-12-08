package au.edu.rmit.sept.cinemas.movies.DTO;

import au.edu.rmit.sept.cinemas.movies.validation.ProductIdExists;
import au.edu.rmit.sept.cinemas.movies.validation.SupermarketIdExists;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public class DeliveryCartItemDTO {

    @NotNull(message = "product id cannot be null")
    @ProductIdExists(message = "product id doesnt exist")
    private Long productId;

    @NotNull(message = "supermarket id cannot be null")
    @SupermarketIdExists(message = "supermarket id doesnt exist")
    private Long supermarketId;

    @NotNull (message = "quantity cannot be null")
    private Integer quantity;

    public DeliveryCartItemDTO() {

    }

    public DeliveryCartItemDTO(Long productId, Long supermarketId, Integer quantity) {
        this.productId = productId;
        this.supermarketId = supermarketId;
        this.quantity = quantity;
    }

    public Long getProductId() {
        return productId;
    }

    public Long getSupermarketId() {
        return supermarketId;
    }

    public Integer getQuantity() {
        return quantity;
    }
}
