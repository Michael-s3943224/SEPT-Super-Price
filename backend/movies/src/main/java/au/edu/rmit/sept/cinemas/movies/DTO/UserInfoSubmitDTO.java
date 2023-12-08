package au.edu.rmit.sept.cinemas.movies.DTO;

import au.edu.rmit.sept.cinemas.movies.validation.DeliveryMethodExists;
import au.edu.rmit.sept.cinemas.movies.validation.DeliveryTimeExists;
import au.edu.rmit.sept.cinemas.movies.validation.PasswordMatch;
import au.edu.rmit.sept.cinemas.movies.validation.UniqueUserEmail;
import jakarta.validation.constraints.NotBlank;

@PasswordMatch(message = "passwords do not match")
public class UserInfoSubmitDTO implements PasswordConfirm
{
    @NotBlank(message = "name cannot be blank")
    private String name;

    private String mobile;

    private String deliveryAddress;

    @DeliveryMethodExists(message = "delivery method doesn't exist")
    private String deliveryMethod;

    @DeliveryTimeExists(message = "delivery time slot doesn't exist")
    private String deliveryTime;

    private String password;

    private String passwordConfirm;

    public UserInfoSubmitDTO() {

    }

    public UserInfoSubmitDTO(String name, String mobile, String deliveryAddress, String deliveryMethod, String deliveryTime, String password, String passwordConfirm) {
        this.name = name;
        this.mobile = mobile;
        this.deliveryAddress = deliveryAddress;
        this.deliveryMethod = deliveryMethod;
        this.deliveryTime = deliveryTime;
        this.password = password;
        this.passwordConfirm = passwordConfirm;
    }

    public String getName() {
        return name;
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

    public String getPassword() {
        return password;
    }

    public String getPasswordConfirm() {
        return passwordConfirm;
    }
}
