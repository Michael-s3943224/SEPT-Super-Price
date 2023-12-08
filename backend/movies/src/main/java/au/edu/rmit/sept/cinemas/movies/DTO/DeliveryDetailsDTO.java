package au.edu.rmit.sept.cinemas.movies.DTO;

import au.edu.rmit.sept.cinemas.movies.validation.DeliveryMethodExists;
import au.edu.rmit.sept.cinemas.movies.validation.DeliveryTimeExists;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

public class DeliveryDetailsDTO {

    @NotBlank(message = "name cannot be empty")
    private String name;

    @NotBlank(message = "email cannot be empty")
    private String email;

    @NotBlank(message = "mobile cannot be empty")
    private String mobile;

    @NotBlank(message = "delivery address cannot be empty")
    private String deliveryAddress;

    @NotBlank(message = "delivery method cannot be empty")
    @DeliveryMethodExists(message = "delivery method doesn't exist")
    private String deliveryMethod;

    @NotBlank(message = "delivery method cannot be empty")
    @DeliveryTimeExists(message = "delivery time doesn't exist")
    private String deliveryTime;

    public DeliveryDetailsDTO() {
    }

    public DeliveryDetailsDTO(String name, String email, String mobile, String deliveryAddress, String deliveryMethod, String deliveryTime) {
        this.name = name;
        this.email = email;
        this.mobile = mobile;
        this.deliveryAddress = deliveryAddress;
        this.deliveryMethod = deliveryMethod;
        this.deliveryTime = deliveryTime;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getMobile() {
        return mobile;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public String getDeliveryMethod() {
        return deliveryMethod;
    }

    public String getDeliveryTime() {
        return deliveryTime;
    }
}

