package au.edu.rmit.sept.cinemas.movies.DTO;


import au.edu.rmit.sept.cinemas.movies.validation.PasswordMatch;
import au.edu.rmit.sept.cinemas.movies.validation.UniqueUserEmail;
import jakarta.validation.constraints.NotBlank;

@PasswordMatch(message = "passwords do not match")
public class RegisterRequest implements PasswordConfirm {

    @NotBlank(message = "name cannot be blank")
    private String name;

    @NotBlank(message = "email cannot be blank")
    @UniqueUserEmail(message = "email already exists")
    private String email;

    @NotBlank(message = "password cannot be blank")
    private String password;
    private String passwordConfirm;

    public RegisterRequest() {

    }

    public RegisterRequest(String name, String email, String password, String passwordConfirm)
    {
        this.name = name;
        this.email = email;
        this.password = password;
        this.passwordConfirm = passwordConfirm;
    }


    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getPasswordConfirm() {
        return passwordConfirm;
    }
}
