package au.edu.rmit.sept.cinemas.movies.controller;

import au.edu.rmit.sept.cinemas.movies.MoviesApplication;
import au.edu.rmit.sept.cinemas.movies.repository.ProductRepository;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = MoviesApplication.class)
@AutoConfigureMockMvc
public class ProductControllerTests {

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
    public void GetAllProducts_ReturnResponseDto_ValidURL() throws Exception
    {
        ResultActions response = mvc.perform(get("/api/products"));
        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].id").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].name").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].desc").doesNotExist())
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].category").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].brand").isNotEmpty());
    }

    @Test
    public void GetAllSupermarkets_NotFound_InvalidURL() throws Exception
    {
        ResultActions response = mvc.perform(get("/api/produkts"));
        response.andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.jsonPath("$").doesNotExist());
    }

    @Test
    public void GetProductById_ReturnResponseDto_ValidExistingId() throws Exception
    {
        Long validExistingId = 1L;
        ResultActions response = mvc.perform(get("/api/products/" + validExistingId));
        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.desc").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.category").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.brand").isNotEmpty());
    }

    @Test
    public void GetProductById_ReturnEmptyObject_ValidNonExistingId() throws Exception
    {
        Long validNonExistingId = 999L;
        ResultActions response = mvc.perform(get("/api/products/" + validNonExistingId));
        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").doesNotExist());
    }

    @Test
    public void GetProductById_ReturnBadRequest_InvalidId() throws Exception
    {
        String invalidId = "a";
        ResultActions response = mvc.perform(get("/api/products/" + invalidId));
        response.andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$").doesNotExist());
    }

}
