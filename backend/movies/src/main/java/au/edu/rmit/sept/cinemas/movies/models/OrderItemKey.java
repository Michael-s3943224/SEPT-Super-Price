package au.edu.rmit.sept.cinemas.movies.models;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.util.Objects;

@Embeddable
public class OrderItemKey {
    @Column(name = "order_id")
    Long orderId;

    @Column(name = "product_id")
    Long productId;

    @Column(name = "supermarket_id")
    Long supermarketId;

    public OrderItemKey() {

    }

    public OrderItemKey(Long orderId, Long productId, Long supermarketId) {
        this.orderId = orderId;
        this.productId = productId;
        this.supermarketId = supermarketId;
    }

    public Long getOrderId() {
        return orderId;
    }

    public Long getProductId() {
        return productId;
    }

    public Long getSupermarketId() {
        return supermarketId;
    }

    @Override
    public boolean equals(Object other)
    {
        if (this == other) {
            return true;
        }

        if (!(other instanceof OrderItemKey)) {
            return false;
        }

        OrderItemKey casted = (OrderItemKey) other;

        return this.orderId == casted.orderId &&
                this.productId == casted.productId &&
                this.supermarketId == casted.supermarketId;
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(orderId, productId, supermarketId);
    }
}
