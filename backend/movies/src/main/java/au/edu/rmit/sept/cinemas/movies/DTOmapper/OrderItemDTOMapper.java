package au.edu.rmit.sept.cinemas.movies.DTOmapper;

import au.edu.rmit.sept.cinemas.movies.DTO.OrderDTO;
import au.edu.rmit.sept.cinemas.movies.DTO.OrderItemDTO;
import au.edu.rmit.sept.cinemas.movies.models.Order;
import au.edu.rmit.sept.cinemas.movies.models.OrderItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class OrderItemDTOMapper implements Function<OrderItem, OrderItemDTO> {

    @Autowired
    SupermarketWithoutDescDTOMapper supermarketWithoutDescDTOMapper;

    @Autowired
    ProductWithoutDescDTOMapper productWithoutDescDTOMapper;

    public OrderItemDTO apply(OrderItem orderItem) {
        return new OrderItemDTO(
            supermarketWithoutDescDTOMapper.apply(orderItem.getSupermarket()),
            productWithoutDescDTOMapper.apply(orderItem.getProduct()),
            orderItem.getDiscountPrice(),
            orderItem.getQuantity()
        );
    }
}
