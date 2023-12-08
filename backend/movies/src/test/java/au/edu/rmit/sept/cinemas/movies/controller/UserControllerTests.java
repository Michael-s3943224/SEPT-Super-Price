package au.edu.rmit.sept.cinemas.movies.controller;

import au.edu.rmit.sept.cinemas.movies.DTO.AuthenticationRequest;
import au.edu.rmit.sept.cinemas.movies.DTO.AuthenticationResponse;
import au.edu.rmit.sept.cinemas.movies.MoviesApplication;
import au.edu.rmit.sept.cinemas.movies.models.*;
import au.edu.rmit.sept.cinemas.movies.repository.OrderItemRepository;
import au.edu.rmit.sept.cinemas.movies.repository.OrderRepository;
import au.edu.rmit.sept.cinemas.movies.service.AuthenticationService;
import au.edu.rmit.sept.cinemas.movies.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.antlr.v4.runtime.atn.SemanticContext;
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
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = MoviesApplication.class)
@AutoConfigureMockMvc
public class UserControllerTests {

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

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private OrderRepository orderRepository;

    @BeforeEach
    private void setUp() {
        flyway.migrate();
    }

    @AfterEach
    private void tearDown() {
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

    private Order createMockUserOrderDetails() {
        return new Order(
                null,
                "example",
                "example@gmail.com",
                "123456789",
                "123 Somewhere Street",
                "Standard",
                "1:00pm - 2:00pm"
        );
    }

    private void createMockUserOrders() {
        Order order1 = createMockUserOrderDetails();
        orderRepository.save(order1);
        OrderItem orderItem1 = new OrderItem(new OrderItemKey(order1.getId(), 1L, 1L),5, new BigDecimal("1.7"));
        OrderItem orderItem2 = new OrderItem(new OrderItemKey(order1.getId(), 1L, 2L), 3, new BigDecimal("2.3"));
        orderItemRepository.save(orderItem1);
        orderItemRepository.save(orderItem2);

        Order order2 = createMockUserOrderDetails();
        orderRepository.save(order2);
        OrderItem orderItem3 = new OrderItem(new OrderItemKey(order2.getId(), 1L, 4L), 2, new BigDecimal("3.4"));
        orderItemRepository.save(orderItem3);
    }

    @Test
    public void GetUserInfo_ReturnUserInfo_ValidToken() throws Exception {
        createMockUser();
        String token = loginMockUser();

        ResultActions response = mvc.perform(get("/api/user/info")
                .header("Authorization", "Bearer " + token)
        );

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.mobile").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.deliveryAddress").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.deliveryTime").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.deliveryMethod").exists());
    }

    @Test
    public void GetUserInfo_ReturnForbidden_NoToken() throws Exception {

        ResultActions response = mvc.perform(get("/api/user/info"));
        response.andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    @Test
    public void GetUserInfo_ReturnForbidden_InvalidToken() throws Exception {
        String token = "12345678";
        ResultActions response = mvc.perform(get("/api/user/info")
                .header("Authorization", "Bearer " + token)
        );
        response.andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    @Test
    public void UpdateUserInfo_ReturnOk_ValidToken() throws Exception {
        createMockUser();
        String token = loginMockUser();

        Map<String, Object> json = new HashMap<>();

        json.put("name", "example");
        json.put("mobile", null);
        json.put("deliveryAddress", null);
        json.put("deliveryMethod", null);
        json.put("deliveryTime", null);
        json.put("password", null);
        json.put("confirmPassword", null);

        ResultActions response = mvc.perform(put("/api/user/info")
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(json))
        );

        response.andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void UpdateUserInfo_ReturnBadRequest_ValidTokenInvalidInput() throws Exception {
        createMockUser();
        String token = loginMockUser();

        Map<String, Object> json = new HashMap<>();
        json.put("name", null);
        json.put("mobile", null);
        json.put("deliveryAddress", null);
        json.put("deliveryTime", "abc");
        json.put("deliveryMethod", "abc");
        json.put("password", null);
        json.put("passwordConfirm", null);

        ResultActions response = mvc.perform(put("/api/user/info")
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(json))
        );

        response.andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.deliveryTime").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.deliveryMethod").exists());
    }

    @Test
    public void UpdateUserInfo_ReturnForbidden_InvalidToken() throws Exception {
        String token = "abc";
        ResultActions response = mvc.perform(put("/api/user/info")
                .header("Authorization", "Bearer " + token)
        );
        response.andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    @Test
    public void GetUserOrders_ReturnOk_ValidToken() throws Exception {
        createMockUser();
        createMockUserOrders();
        String token = loginMockUser();

        ResultActions response = mvc.perform(get("/api/user/orders")
                .header("Authorization", "Bearer " + token)
        );

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].cart").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].cart[*].supermarket.id").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].cart[*].supermarket.name").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].cart[*].product.id").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].cart[*].product.name").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].cart[*].discountPrice").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].cart[*].quantity").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].details.name").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].details.email").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].details.mobile").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].details.deliveryAddress").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].details.deliveryMethod").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].details.deliveryTime").exists());
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
