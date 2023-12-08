package au.edu.rmit.sept.cinemas.movies.handler;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import io.jsonwebtoken.MalformedJwtException;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalRESTExceptionHandler {
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleInvalidArgument(MethodArgumentNotValidException e) {
        Map<String, String> map = new HashMap<>();
        e.getBindingResult().getFieldErrors().forEach(fieldError -> {
            map.put(fieldError.getField(), fieldError.getDefaultMessage());
        });
        return map;
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(EmailUseWhenNotLoggedIn.class)
    public Map<String, String> handleEmailUseWhenNotLoggedIn(EmailUseWhenNotLoggedIn e) {
        Map<String, String> map = new HashMap<>();
        map.put("error", e.getMessage());
        return map;
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(MalformedJwtException.class)
    public Map<String, String> handleExpiredJsonWebToken(MalformedJwtException e) {
        Map<String, String> map = new HashMap<>();
        map.put("jwt", "expired");
        return map;
    }
}
