package au.edu.rmit.sept.cinemas.movies.controller;

import au.edu.rmit.sept.cinemas.movies.DTO.AuthenticationResponse;
import au.edu.rmit.sept.cinemas.movies.DTO.OrderDTO;
import au.edu.rmit.sept.cinemas.movies.DTO.UserDTO;
import au.edu.rmit.sept.cinemas.movies.DTO.UserInfoSubmitDTO;
import au.edu.rmit.sept.cinemas.movies.DTOmapper.OrderDTOMapper;
import au.edu.rmit.sept.cinemas.movies.DTOmapper.UserDTOMapper;
import au.edu.rmit.sept.cinemas.movies.models.Product;
import au.edu.rmit.sept.cinemas.movies.models.User;
import au.edu.rmit.sept.cinemas.movies.repository.OrderRepository;
import au.edu.rmit.sept.cinemas.movies.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.ResourceUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.sound.midi.SysexMessage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    UserRepository repository;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private OrderDTOMapper orderDTOMapper;

    @GetMapping(value="/info")
    public UserDTO getUserInfo()
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        return new UserDTOMapper().apply(user);
    }

    @PutMapping(value="/info")
    public void putUserInfo(@Valid @RequestBody UserInfoSubmitDTO userInfoSubmit)
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();

        user.setName(userInfoSubmit.getName());
        user.setMobile(userInfoSubmit.getMobile());
        user.setDeliveryAddress(userInfoSubmit.getDeliveryAddress());
        user.setDeliveryMethod(userInfoSubmit.getDeliveryMethod());
        user.setDeliveryTime(userInfoSubmit.getDeliveryTime());

        if (userInfoSubmit.getPassword() != null && !userInfoSubmit.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(userInfoSubmit.getPassword()));
        }

        repository.save(user);
    }

    @GetMapping("/orders")
    public List<OrderDTO> getUserDeliveries() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();

        return orderRepository.findOrdersByEmail(user.getEmail()).stream().map(orderDTOMapper).collect(Collectors.toList());
    }
}
