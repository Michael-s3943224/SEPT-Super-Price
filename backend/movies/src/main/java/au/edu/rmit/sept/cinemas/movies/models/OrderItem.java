package au.edu.rmit.sept.cinemas.movies.models;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "order_item")
public class OrderItem {

    @EmbeddedId
    OrderItemKey key;

    @Column(name = "quantity")
    Integer quantity;

    @Column(name = "discount_price")
    BigDecimal discountPrice;

    @OneToOne
    @JoinColumn(name="supermarket_id", referencedColumnName = "id", insertable=false, updatable=false)
    Supermarket supermarket;

    @OneToOne
    @JoinColumn(name="product_id", referencedColumnName = "id", insertable=false, updatable=false)
    Product product;

    public OrderItem() {

    }

    public OrderItem(OrderItemKey key, Integer quantity, BigDecimal discountPrice) {
        this.key = key;
        this.quantity = quantity;
        this.discountPrice = discountPrice;
    }

    public OrderItemKey getKey() {
        return key;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public BigDecimal getDiscountPrice() { return discountPrice; }

    public Supermarket getSupermarket() { return supermarket; }

    public Product getProduct() { return product; }
}
