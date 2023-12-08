package au.edu.rmit.sept.cinemas.movies.models;

import jakarta.persistence.*;
import org.hibernate.annotations.Where;

import java.math.BigDecimal;
import java.util.*;

@Entity
@Table(name = "supermarket_product")
public class SupermarketProduct {

    @EmbeddedId
    SupermarketProductKey id;

    @ManyToOne
    @MapsId("supermarketId")
    @JoinColumn(name="supermarket_id")
    Supermarket supermarket;

    @ManyToOne
    @MapsId("productId")
    @JoinColumn(name="product_id")
    Product product;

    @Column(name="price")
    BigDecimal price;

    @OneToMany
    @JoinColumns({
        @JoinColumn(name="supermarket_id", referencedColumnName = "supermarket_id"),
        @JoinColumn(name="product_id", referencedColumnName = "product_id")
    })
    @Where(clause = "CURRENT_DATE() BETWEEN from_date AND to_date")
    List<Deal> deals;

    public SupermarketProduct()
    {

    }

    public SupermarketProduct(SupermarketProductKey id, Supermarket supermarket, Product product, BigDecimal price, List<Deal> deals)
    {
        this.id = id;
        this.supermarket = supermarket;
        this.product = product;
        this.price = price;
        this.deals = deals;
    }

    public SupermarketProductKey getId()
    {
        return id;
    }

    public Supermarket getSupermarket()
    {
        return supermarket;
    }

    public Product getProduct()
    {
        return product;
    }

    public BigDecimal getPrice()
    {
        return price;
    }

    public List<Deal> getDeals()
    {
        return deals;
    }
}
