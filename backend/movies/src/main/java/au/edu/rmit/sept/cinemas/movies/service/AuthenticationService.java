package au.edu.rmit.sept.cinemas.movies.service;

import au.edu.rmit.sept.cinemas.movies.DTO.AuthenticationRequest;
import au.edu.rmit.sept.cinemas.movies.DTO.AuthenticationResponse;
import au.edu.rmit.sept.cinemas.movies.DTO.RegisterRequest;
import au.edu.rmit.sept.cinemas.movies.models.Role;
import au.edu.rmit.sept.cinemas.movies.models.User;
import au.edu.rmit.sept.cinemas.movies.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthenticationService {

    @Autowired
    private UserService userService;

    @Autowired
    JwtService jwtService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest request)
    {
        User user = new User(
                -1L,
                request.getName(),
                request.getEmail().toLowerCase(),
                null,
                null,
                null,
                null,
                passwordEncoder.encode(request.getPassword()),
                Role.USER
        );

        userService.save(user);
        String jwtToken = jwtService.generateToken(user);
        return new AuthenticationResponse(jwtToken);
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request)
    {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail().toLowerCase(),
                        request.getPassword()
                )
        );

        User user = userService.findByEmail(request.getEmail().toLowerCase())
                .orElseThrow();

        String jwtToken = jwtService.generateToken(user);
        return new AuthenticationResponse(jwtToken);
    }
}
