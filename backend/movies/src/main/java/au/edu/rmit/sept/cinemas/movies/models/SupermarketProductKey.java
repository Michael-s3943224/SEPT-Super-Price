package au.edu.rmit.sept.cinemas.movies.models;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class SupermarketProductKey implements Serializable {

    @Column(name = "supermarket_id")
    Long supermarketId;

    @Column(name = "product_id")
    Long productId;

    public SupermarketProductKey() {

    }

    public SupermarketProductKey(Long supermarketId, Long productId) {
        this.supermarketId = supermarketId;
        this.productId = productId;
    }

    public Long getSupermarketId() {
        return supermarketId;
    }

    public Long getProductId() {
        return productId;
    }

    @Override
    public boolean equals(Object other)
    {
        if (this == other) {
            return true;
        }

        if (!(other instanceof SupermarketProductKey)) {
            return false;
        }

        SupermarketProductKey casted = (SupermarketProductKey) other;

        return this.productId == casted.productId &&
                this.supermarketId == casted.supermarketId;
    }
    @Override
    public int hashCode()
    {
        return Objects.hash(productId, supermarketId);
    }
}
