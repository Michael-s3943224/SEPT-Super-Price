package au.edu.rmit.sept.cinemas.movies.DTOmapper;

import au.edu.rmit.sept.cinemas.movies.DTO.DealWithoutIdsDTO;
import au.edu.rmit.sept.cinemas.movies.DTO.DeliveryDetailsDTO;
import au.edu.rmit.sept.cinemas.movies.DTO.OrderDTO;
import au.edu.rmit.sept.cinemas.movies.DTO.ProductDetailedDTO;
import au.edu.rmit.sept.cinemas.movies.models.Deal;
import au.edu.rmit.sept.cinemas.movies.models.Order;
import au.edu.rmit.sept.cinemas.movies.models.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class OrderDTOMapper implements Function<Order, OrderDTO> {
    @Autowired
    OrderItemDTOMapper orderItemDTOMapper;
    @Override
    public OrderDTO apply(Order order) {
        return new OrderDTO(
                new DeliveryDetailsDTO(
                        order.getName(),
                        order.getEmail(),
                        order.getMobile(),
                        order.getDeliveryAddress(),
                        order.getDeliveryMethod(),
                        order.getDeliveryTime()
                ),
                order.getOrderItems().stream().map(orderItemDTOMapper).collect(Collectors.toList())
        );
    }
}
