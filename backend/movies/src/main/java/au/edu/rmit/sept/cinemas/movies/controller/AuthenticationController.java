package au.edu.rmit.sept.cinemas.movies.controller;

import au.edu.rmit.sept.cinemas.movies.DTO.AuthenticationRequest;
import au.edu.rmit.sept.cinemas.movies.DTO.AuthenticationResponse;
import au.edu.rmit.sept.cinemas.movies.DTO.RegisterRequest;
import au.edu.rmit.sept.cinemas.movies.service.AuthenticationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationService service;

    @PostMapping("/register")
    public AuthenticationResponse register(
            @RequestBody @Valid RegisterRequest request
    ) {
        return service.register(request);
    }

    @PostMapping("/authenticate")
    public AuthenticationResponse authenticate(
            @RequestBody AuthenticationRequest request
    ) {
        return service.authenticate(request);
    }
}
