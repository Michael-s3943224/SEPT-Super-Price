package au.edu.rmit.sept.cinemas.movies.controller;

import au.edu.rmit.sept.cinemas.movies.MoviesApplication;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = MoviesApplication.class)
@AutoConfigureMockMvc
public class SupermarketControllerTests {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private Flyway flyway;

    @BeforeEach
    public void setUp() {
        flyway.migrate();
    }

    @AfterEach
    public void tearDown() {
        flyway.clean();
    }

    @Test
    public void GetAllSupermarkets_ReturnResponseDto_ValidURL() throws Exception
    {
        ResultActions response = mvc.perform(get("/api/supermarkets"));
        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].id").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].name").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].desc").doesNotExist());
    }

    @Test
    public void GetAllSupermarkets_NotFound_InvalidURL() throws Exception
    {
        ResultActions response = mvc.perform(get("/api/supmarkets"));
        response.andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void GetSupermarketById_ReturnResponseDto_ValidExistingId() throws Exception
    {
        Long validExistingId = 1L;
        ResultActions response = mvc.perform(get("/api/supermarkets/" + validExistingId));
        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.desc").isNotEmpty());
    }

    @Test
    public void GetSupermarketById_ReturnEmptyObject_ValidNonExistingId() throws Exception
    {
        Long validNonExistingId = 999L;
        ResultActions response = mvc.perform(get("/api/supermarkets/" + validNonExistingId));
        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").doesNotExist());
    }

    @Test
    public void GetSupermarketById_ReturnBadRequest_InvalidId() throws Exception
    {
        String invalidId = "a";
        ResultActions response = mvc.perform(get("/api/supermarkets/" + invalidId));
        response.andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

}
