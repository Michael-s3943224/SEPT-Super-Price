package au.edu.rmit.sept.cinemas.movies.models;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "product_order")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    @Column(name = "name")
    String name;

    @Column(name = "email")
    String email;

    @Column(name = "mobile")
    String mobile;

    @Column(name = "delivery_address")
    String deliveryAddress;

    @Column(name = "delivery_method")
    String deliveryMethod;

    @Column(name = "delivery_time")
    String deliveryTime;

    @OneToMany
    @JoinColumn(name = "order_id", referencedColumnName = "id")
    List<OrderItem> orderItems;

    public Order() {

    }

    public Order(Long id, String name, String email, String mobile, String deliveryAddress, String deliveryMethod, String deliveryTime) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.mobile = mobile;
        this.deliveryAddress = deliveryAddress;
        this.deliveryMethod = deliveryMethod;
        this.deliveryTime = deliveryTime;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getMobile() {
        return mobile;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public String getDeliveryMethod() {
        return deliveryMethod;
    }

    public String getDeliveryTime() {
        return deliveryTime;
    }

    public List<OrderItem> getOrderItems() { return orderItems; }
}
