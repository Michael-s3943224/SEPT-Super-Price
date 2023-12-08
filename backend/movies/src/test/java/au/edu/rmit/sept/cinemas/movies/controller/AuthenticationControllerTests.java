package au.edu.rmit.sept.cinemas.movies.controller;

import au.edu.rmit.sept.cinemas.movies.MoviesApplication;
import au.edu.rmit.sept.cinemas.movies.models.Role;
import au.edu.rmit.sept.cinemas.movies.models.User;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import java.util.HashMap;
import java.util.Map;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = MoviesApplication.class)
@AutoConfigureMockMvc
public class AuthenticationControllerTests {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private Flyway flyway;

    @Autowired
    private UserService userService;

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

    @Test
    public void Register_ReturnBadRequest_InvalidDetails() throws Exception {
        Map<String, Object> json = new HashMap<>();
        json.put("name", "");
        json.put("email","");
        json.put("password","");
        json.put("passwordConfirm", "a");

        ResultActions response = mvc.perform(post("/api/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(json)));

        response.andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.password").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.passwordConfirm").exists());
    }

    @Test
    public void Register_ReturnOk_ValidDetails() throws Exception {
        Map<String, Object> json = new HashMap<>();
        json.put("name", "example");
        json.put("email", "example@gmail.com");
        json.put("password", "password");
        json.put("passwordConfirm", "password");

        ResultActions response = mvc.perform(post("/api/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(json))
        );

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.token").exists());
    }

    @Test
    public void Register_ReturnBadRequest_EmailExists() throws Exception {
        userService.save(
            new User(
                null,
                "example",
                "example@gmail.com",
                "", "", "", "",
                passwordEncoder.encode("password"),
                Role.USER
            )
        );

        Map<String, Object> json = new HashMap<>();
        json.put("name", "example");
        json.put("email", "example@gmail.com");
        json.put("password", "password");
        json.put("passwordConfirm", "password");

        ResultActions response = mvc.perform(post("/api/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(json)));

        response.andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").doesNotExist())
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.password").doesNotExist())
                .andExpect(MockMvcResultMatchers.jsonPath("$.passwordConfirm").doesNotExist());

    }

    @Test
    public void Login_ReturnForbidden_InvalidDetails() throws Exception {
        Map<String, Object> json = new HashMap<>();
        json.put("email", "");
        json.put("password", "");

        ResultActions response = mvc.perform(post("/api/auth/authenticate")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(json))
        );

        response.andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    @Test
    public void Login_ReturnBadRequest_InvalidPassword() throws Exception {
        userService.save(
                new User(
                        null,
                        "example",
                        "example@gmail.com",
                        "", "", "", "",
                        passwordEncoder.encode("password"),
                        Role.USER
                )
        );

        Map<String, Object> json = new HashMap<>();
        json.put("email", "example@gmail.com");
        json.put("password","notpassword");

        ResultActions response = mvc.perform(post("/api/auth/authenticate")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(json))
        );

        response.andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    @Test
    public void Login_ReturnOk_ValidDetails() throws Exception {
        userService.save(
                new User(
                        null,
                        "example",
                        "example@gmail.com",
                        "", "", "", "",
                        passwordEncoder.encode("password"),
                        Role.USER
                )
        );

        Map<String, Object> json = new HashMap<>();
        json.put("email", "example@gmail.com");
        json.put("password", "password");

        ResultActions response = mvc.perform(post("/api/auth/authenticate")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(json))
        );

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.token").exists());
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
