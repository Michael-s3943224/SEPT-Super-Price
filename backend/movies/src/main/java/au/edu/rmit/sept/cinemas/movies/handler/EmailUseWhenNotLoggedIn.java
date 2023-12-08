package au.edu.rmit.sept.cinemas.movies.handler;

public class EmailUseWhenNotLoggedIn extends RuntimeException {
    public EmailUseWhenNotLoggedIn(String errorMessage) {
        super(errorMessage);
    }
}