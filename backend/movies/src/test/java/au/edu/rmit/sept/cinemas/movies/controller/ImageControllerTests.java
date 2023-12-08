package au.edu.rmit.sept.cinemas.movies.controller;

import au.edu.rmit.sept.cinemas.movies.MoviesApplication;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import javax.xml.transform.Result;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = MoviesApplication.class)
@AutoConfigureMockMvc
public class ImageControllerTests {

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
    public void GetSupermarketImageById_ReturnImage_ValidExistingId() throws Exception
    {
        Long validExistingId = 1L;
        ResultActions response = mvc.perform(get("/api/images/supermarkets/" + validExistingId));
        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.IMAGE_PNG));
    }

    @Test
    public void GetSuperMarketImageById_ReturnNull_ValidNonExistingId() throws Exception
    {
        Long validNonExistingId = 999L;
        ResultActions response = mvc.perform(get("/api/images/supermarkets/" + validNonExistingId));
        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").doesNotExist());
    }

    @Test
    public void GetSuperMarketImageById_BadRequest_InvalidId() throws Exception
    {
        String invalidId = "a";
        ResultActions response = mvc.perform(get("/api/images/supermarkets/" + invalidId));
        response.andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$").doesNotExist());
    }

    @Test
    public void GetProductImageById_ReturnImage_ValidExistingId() throws Exception
    {
        Long validExistingId = 1L;
        ResultActions response = mvc.perform(get("/api/images/products/" + validExistingId));
        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.IMAGE_JPEG));
    }

    @Test
    public void GetProductImageById_ReturnNull_ValidNonExistingId() throws Exception
    {
        Long validNonExistingId = 999L;
        ResultActions response = mvc.perform(get("/api/images/products/" + validNonExistingId));
        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").doesNotExist());
    }

    @Test
    public void GetProductImageById_BadRequest_InvalidId() throws Exception
    {
        String invalidId = "a";
        ResultActions response = mvc.perform(get("/api/images/products/" + invalidId));
        response.andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$").doesNotExist());
    }

}
