package au.edu.rmit.sept.cinemas.movies.DTO;

public class AuthenticationResponse {
    private String token;

    public AuthenticationResponse()
    {

    }

    public AuthenticationResponse(String token)
    {
        this.token = token;
    }

    public String getToken()
    {
        return token;
    }
}
