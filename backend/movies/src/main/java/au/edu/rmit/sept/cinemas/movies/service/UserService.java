package au.edu.rmit.sept.cinemas.movies.service;

import au.edu.rmit.sept.cinemas.movies.models.User;
import au.edu.rmit.sept.cinemas.movies.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public boolean emailExists(String email) {
        return !userRepository.findByEmail(email).isEmpty();
    }

    public void save(User user) {
        userRepository.save(user);
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
