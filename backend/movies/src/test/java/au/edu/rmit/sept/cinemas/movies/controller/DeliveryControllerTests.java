package au.edu.rmit.sept.cinemas.movies.controller;

import au.edu.rmit.sept.cinemas.movies.DTO.AuthenticationRequest;
import au.edu.rmit.sept.cinemas.movies.DTO.AuthenticationResponse;
import au.edu.rmit.sept.cinemas.movies.MoviesApplication;
import au.edu.rmit.sept.cinemas.movies.models.Role;
import au.edu.rmit.sept.cinemas.movies.models.User;
import au.edu.rmit.sept.cinemas.movies.service.AuthenticationService;
import au.edu.rmit.sept.cinemas.movies.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import javax.xml.transform.Result;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = MoviesApplication.class)
@AutoConfigureMockMvc
public class DeliveryControllerTests {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private Flyway flyway;

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    public void setUp() {
        flyway.migrate();
    }

    @AfterEach
    public void tearDown() {
        flyway.clean();
    }

    private void createMockUser() {
        userService.save(
                new User(
                        null,
                        "example",
                        "example@gmail.com",
                        "123456789",
                        "123 Somewhere Rd",
                        "11:00 am  - 12:00 pm",
                        "Standard",
                        passwordEncoder.encode("password"),
                        Role.USER
                )
        );
    }

    private String loginMockUser() {
        AuthenticationResponse res = authenticationService.authenticate(
                new AuthenticationRequest(
                        "example@gmail.com",
                        "password"
                )
        );
        return res.getToken();
    }

    private Map<String, Object> getMockDeliveryDetails() {
        Map<String, Object> deliveryDetails = new HashMap<>();
        deliveryDetails.put("name", "example");
        deliveryDetails.put("email", "example@gmail.com");
        deliveryDetails.put("mobile", "123456789");
        deliveryDetails.put("deliveryAddress", "123 Somewhere Street");
        deliveryDetails.put("deliveryMethod", "Standard");
        deliveryDetails.put("deliveryTime", "1:00 pm - 2:00 pm");
        return deliveryDetails;
    }

    private Map<String, Object> getMockDelivery() {
        Map<String, Object> submission = new HashMap<>();

        var deliveryDetails = getMockDeliveryDetails();

        Map<String, Object> cartItem1 = new HashMap<>();
        cartItem1.put("supermarketId", 1);
        cartItem1.put("productId", 1);
        cartItem1.put("quantity", 3);

        Map<String, Object> cartItem2 = new HashMap<>();
        cartItem2.put("supermarketId", 2);
        cartItem2.put("productId", 1);
        cartItem2.put("quantity", 2);

        submission.put("deliveryDetails", deliveryDetails);
        submission.put("deliveryCart", List.of(cartItem1, cartItem2));

        return submission;
    }

    private Map<String, Object> getMockDeliveryDuplicateCart()
    {
        Map<String, Object> submission = new HashMap<>();

        var deliveryDetails = getMockDeliveryDetails();

        Map<String, Object> cartItem1 = new HashMap<>();
        cartItem1.put("supermarketId", 1);
        cartItem1.put("productId", 1);
        cartItem1.put("quantity", 3);

        Map<String, Object> cartItem2 = new HashMap<>();
        cartItem2.put("supermarketId", 1);
        cartItem2.put("productId", 1);
        cartItem2.put("quantity", 2);

        submission.put("deliveryDetails", deliveryDetails);
        submission.put("deliveryCart", List.of(cartItem1, cartItem2));

        return submission;
    }

    @Test
    public void SendDelivery_ReturnOk_ValidInput() throws Exception {

        var submission = getMockDelivery();

        ResultActions response = mvc.perform(post("/api/delivery")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(submission))
        );

        response.andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void SendDelivery_ReturnBadRequest_DuplicateCartItems() throws Exception {

        var submission = getMockDeliveryDuplicateCart();

        ResultActions response = mvc.perform(post("/api/delivery")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(submission))
        );

        response.andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void SendDelivery_ReturnBadRequest_EmptyCart() throws Exception {
        Map<String, Object> submission = new HashMap<>();
        var deliveryDetails = getMockDeliveryDetails();
        submission.put("deliveryDetails", deliveryDetails);
        submission.put("deliveryCart", new ArrayList<>());

        ResultActions response = mvc.perform(post("/api/delivery")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(submission))
        );

        response.andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void SendDelivery_ReturnForbidden_NotLoggedInEmail() throws Exception {
        createMockUser();
        var submission = getMockDelivery();

        ResultActions response = mvc.perform(post("/api/delivery")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(submission))
        );

        response.andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    @Test
    public void SendDelivery_ReturnOk_LoggedInEmail() throws Exception
    {
        createMockUser();
        String token = loginMockUser();
        var submission = getMockDelivery();

        ResultActions response = mvc.perform(post("/api/delivery")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(submission))
                .header("Authorization", "Bearer " + token)
        );

        response.andExpect(MockMvcResultMatchers.status().isOk());
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
