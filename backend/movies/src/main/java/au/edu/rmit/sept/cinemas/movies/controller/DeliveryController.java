package au.edu.rmit.sept.cinemas.movies.controller;

import au.edu.rmit.sept.cinemas.movies.DTO.DeliveryCartItemDTO;
import au.edu.rmit.sept.cinemas.movies.DTO.DeliveryDetailsDTO;
import au.edu.rmit.sept.cinemas.movies.DTO.DeliverySubmissionDTO;
import au.edu.rmit.sept.cinemas.movies.DTO.OrderDTO;
import au.edu.rmit.sept.cinemas.movies.DTOmapper.OrderDTOMapper;
import au.edu.rmit.sept.cinemas.movies.handler.EmailUseWhenNotLoggedIn;
import au.edu.rmit.sept.cinemas.movies.models.*;
import au.edu.rmit.sept.cinemas.movies.repository.DealRepository;
import au.edu.rmit.sept.cinemas.movies.repository.OrderItemRepository;
import au.edu.rmit.sept.cinemas.movies.repository.OrderRepository;
import au.edu.rmit.sept.cinemas.movies.repository.SupermarketProductRepository;
import au.edu.rmit.sept.cinemas.movies.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/delivery")
public class DeliveryController {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private DealRepository dealRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private SupermarketProductRepository supermarketProductRepository;

    private BigDecimal calculateDiscountPrice(SupermarketProduct supermarketProduct, Optional<Deal> deal) {
        BigDecimal result = supermarketProduct.getPrice();

        if (!deal.isEmpty()) {
            Deal d = deal.get();
            if (d.getReductionType().equals("proportion")) {
                result = result.multiply( new BigDecimal("1.00").subtract(d.getReductionValue()));
            } else {
                result = result.subtract(d.getReductionValue());
            }
        }

        return result.setScale(2, RoundingMode.CEILING);
    }

    @PostMapping
    public void saveDeliveryOrder(@Valid @RequestBody DeliverySubmissionDTO deliverySubmission) {
        DeliveryDetailsDTO details = deliverySubmission.getDeliveryDetails();
        List<DeliveryCartItemDTO> cart = deliverySubmission.getDeliveryCart();

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getPrincipal().equals("anonymousUser")) {
            if (userService.emailExists(details.getEmail().toLowerCase())) {
                throw new EmailUseWhenNotLoggedIn("Something went wrong");
            }
        }

        Order newOrder = orderRepository.save(
            new Order(
                null,
                details.getName(),
                details.getEmail(),
                details.getMobile(),
                details.getDeliveryAddress(),
                details.getDeliveryMethod(),
                details.getDeliveryTime()
            )
        );

        for (DeliveryCartItemDTO item: cart) {
            Optional<Deal> deal = dealRepository.findDealById(item.getSupermarketId(), item.getProductId());
            Optional<SupermarketProduct> supermarketProduct = supermarketProductRepository.getSupermarketProductById(item.getSupermarketId(), item.getProductId());
            if (!supermarketProduct.isEmpty()) {
                BigDecimal discountPrice = calculateDiscountPrice(supermarketProduct.get(), deal);
                orderItemRepository.save(
                        new OrderItem(
                                new OrderItemKey(newOrder.getId(), item.getProductId(), item.getSupermarketId()),
                                item.getQuantity(),
                                discountPrice
                        )
                );
            }
        }
    }
}
