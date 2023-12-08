package au.edu.rmit.sept.cinemas.movies.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class CorsConfig {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                // Configure CORS policies here
                registry.addMapping("/api/**") // Define the URL patterns to allow CORS for
                        .allowedOrigins("http://localhost:3000") // Specify allowed origins
                        .allowedMethods("GET", "POST", "PUT", "DELETE") // Specify allowed HTTP methods
                        .allowedHeaders("*"); // Specify allowed headers
            }
        };
    }
}
