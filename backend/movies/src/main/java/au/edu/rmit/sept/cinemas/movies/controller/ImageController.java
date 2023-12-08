package au.edu.rmit.sept.cinemas.movies.controller;

import au.edu.rmit.sept.cinemas.movies.models.Product;
import au.edu.rmit.sept.cinemas.movies.models.Supermarket;
import au.edu.rmit.sept.cinemas.movies.repository.ProductRepository;
import au.edu.rmit.sept.cinemas.movies.repository.SupermarketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.MediaType;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;

import javax.sound.midi.SysexMessage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

@RestController
@RequestMapping("/api/images")
public class ImageController {
    @Autowired
    SupermarketRepository supermarketRepository;

    @Autowired
    ProductRepository productRepository;

    @GetMapping(
            value = "/supermarkets/{id}",
            produces = MediaType.IMAGE_PNG_VALUE
    )
    public @ResponseBody byte[] getSupermarketImage(@PathVariable Long id)
    {
        Optional<Supermarket> supermarket = supermarketRepository.findById(id);
        if (supermarket.isEmpty()) {
            return null;
        }

        final String path = "classpath:images/supermarkets/" + supermarket.get().getImageName();
        try {
            File file = ResourceUtils.getFile(path);
            InputStream in = new FileInputStream(file);
            return in.readAllBytes();
        } catch (IOException e) {
            return null;
        }
    }

    @GetMapping(
            value="/products/{id}",
            produces = MediaType.IMAGE_JPEG_VALUE
    )
    public @ResponseBody byte[] getProductImageById(@PathVariable Long id)
    {
        Optional<Product> product = productRepository.findById(id);
        if (product.isEmpty()) {
            return null;
        }

        final String path = "classpath:images/products/" + product.get().getImageName();
        try {
            File file = ResourceUtils.getFile(path);
            InputStream in = new FileInputStream(file);
            return in.readAllBytes();
        } catch (IOException e) {
            return null;
        }
    }
}
