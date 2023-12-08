package au.edu.rmit.sept.cinemas.movies.controller;

import au.edu.rmit.sept.cinemas.movies.MoviesApplication;
import org.aspectj.lang.annotation.After;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.JsonPathResultMatchers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = MoviesApplication.class)
@AutoConfigureMockMvc
public class SupermarketProductControllerTests {

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
    public void GetAllSupermarketProducts_ReturnResponseDto_ValidURL() throws Exception
    {
        ResultActions response = mvc.perform(get("/api/items"));
        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].price").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].deal").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].supermarket.id").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].supermarket.name").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].supermarket.desc").doesNotExist())
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].product.id").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].product.name").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].product.desc").doesNotExist())
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].product.brand").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].product.category").isNotEmpty());
    }

    @Test
    public void GetAllSupermarketProducts_NotFound_InvalidURL() throws Exception
    {
        ResultActions response = mvc.perform(get("/api/item"));
        response.andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.jsonPath("$").doesNotExist());
    }

    @Test
    public void GetSupermarketProductsByDeals_ReturnDeals_ValidURL() throws Exception
    {
        ResultActions response = mvc.perform(get("/api/items/deals"));
        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].price").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].deal", everyItem(notNullValue())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].supermarket.id").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].supermarket.name").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].supermarket.desc").doesNotExist())
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].product.id").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].product.name").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].product.desc").doesNotExist())
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].product.brand").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].product.category").isNotEmpty());

    }

    @Test
    public void GetAllSupermarketProductsByFiltered_ReturnFilteredByTerm_ValidSearchTerm() throws Exception
    {
        String term = "Coca-Cola";
        ResultActions response = mvc.perform(get("/api/items/browse?term=" + term));
        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].product[?(@.brand =~ /^.*" + term + ".*$/i || @.name =~ /^.*" + term + ".*$/i)]").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].price").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].deal").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].supermarket.id").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].supermarket.name").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].supermarket.desc").doesNotExist())
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].product.id").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].product.desc").doesNotExist())
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].product.category").isNotEmpty());
    }

    @Test
    public void GetAllSupermarketProductsByFilteredBrand_ReturnFilteredByBrand_ValidSearchTermAndBrand() throws Exception
    {
        String term = "Ca";
        String brand = "Coca-Cola";

        ResultActions response = mvc.perform(get("/api/items/browse?term=" + term + "&brand=" + brand));
        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].product[?(@.brand =~ /^.*" + term + ".*$/i || @.name =~ /^.*" + term + ".*$/i)]").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].product.brand", everyItem(containsStringIgnoringCase(brand))))
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].price").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].deal").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].supermarket.id").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].supermarket.name").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].supermarket.desc").doesNotExist())
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].product.id").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].product.desc").doesNotExist())
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].product.category").isNotEmpty());
    }

    @Test
    public void GetAllSupermarketsByFilteredBrands_ReturnFilteredByBrands_ValidSearchTermAndBrands() throws Exception
    {
        String term = "Ca";
        String brand1 = "Coca-Cola";
        String brand2 = "Asahi";

        ResultActions response = mvc.perform(get("/api/items/browse?term=" + term + "&brand=" + brand1 + "&brand=" + brand2));
        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].product[?(@.brand =~ /^.*" + term + ".*$/i || @.name =~ /^.*" + term + ".*$/i)]").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].product.brand", everyItem(anyOf(containsStringIgnoringCase(brand1), containsStringIgnoringCase(brand2)))))
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].price").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].deal").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].supermarket.id").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].supermarket.name").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].supermarket.desc").doesNotExist())
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].product.id").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].product.desc").doesNotExist())
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].product.category").isNotEmpty());
    }

    @Test
    public void GetAllSupermarketProductsByFilteredSupermarkets_ReturnFilteredBySupermarket_ValidSearchTermAndSupermarkets() throws Exception
    {
        String term = "Ca";
        String supermarket1 = "Coles";
        String supermarket2 = "Woolworths";

        ResultActions response = mvc.perform(get("/api/items/browse?term=" + term + "&supermarket=" + supermarket1 + "&supermarket=" + supermarket2));
        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].product[?(@.brand =~ /^.*" + term + ".*$/i || @.name =~ /^.*" + term + ".*$/i)]").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].price").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].deal").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].supermarket.id").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].supermarket.name", everyItem(anyOf(containsStringIgnoringCase(supermarket1),containsStringIgnoringCase(supermarket2)))))
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].supermarket.desc").doesNotExist())
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].product.id").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].product.desc").doesNotExist())
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].product.brand").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].product.category").isNotEmpty());
    }

    @Test
    public void GetAllSupermarketProductsByFilteredPrice_ReturnFilteredByPrice_ValidSearchTermAndPriceRange() throws Exception {
        String term = "Ca";
        Double priceAbove = 3.5;
        Double priceBelow = 55.0;

        ResultActions response = mvc.perform(get("/api/items/browse?term=" + term + "&priceAbove="+ priceAbove + "&priceBelow=" + priceBelow));
        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].product[?(@.brand =~ /^.*" + term + ".*$/i || @.name =~ /^.*" + term + ".*$/i)]").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].price", everyItem(allOf(greaterThanOrEqualTo(priceAbove), lessThanOrEqualTo(priceBelow) ))))
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].deal").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].supermarket.id").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].supermarket.name").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].supermarket.desc").doesNotExist())
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].product.id").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].product.desc").doesNotExist())
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].product.brand").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].product.category").isNotEmpty());

    }

    @Test
    public void GetAllSupermarketProductsEveryFilter_ReturnEveryFiltered_ValidFilters() throws Exception {
        String term = "BISCUITS";
        String supermarket = "Coles";
        String brand = "Arnott's";
        Double priceAbove = 4.0;
        Double priceBelow = 6.0;

        ResultActions response = mvc.perform(get("/api/items/browse?term=" + term));
        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].product[?(@.brand =~ /^.*" + term + ".*$/i || @.name =~ /^.*" + term + ".*$/i)]").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].price").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].deal").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].supermarket.id").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].supermarket.name").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].supermarket.desc").doesNotExist())
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].product.id").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].product.desc").doesNotExist())
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].product.brand").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].product.category").isNotEmpty());

    }
}
