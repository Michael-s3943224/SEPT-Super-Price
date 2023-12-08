package au.edu.rmit.sept.cinemas.movies.models;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.sql.Date;
import java.util.Objects;

@Embeddable
public class DealKey {
    @Column(name = "supermarket_id")
    Long supermarketId;

    @Column(name = "product_id")
    Long productId;

    @Column(name = "from_date")
    Date fromDate;

    @Column(name = "to_date")
    Date toDate;

    public DealKey() {

    }

    public DealKey(Long supermarketId, Long productId, Date fromDate, Date toDate)
    {
        this.supermarketId = supermarketId;
        this.productId = productId;
        this.toDate = toDate;
        this.fromDate = fromDate;
    }

    public Long getSupermarketId() {
        return supermarketId;
    }

    public Long getProductId() {
        return productId;
    }

    public Date getToDate()
    {
        return toDate;
    }

    public Date getFromDate()
    {
        return fromDate;
    }

    @Override
    public boolean equals(Object other)
    {
        if (this == other) {
            return true;
        }

        if (!(other instanceof DealKey)) {
            return false;
        }

        DealKey casted = (DealKey) other;

        return this.productId == casted.productId &&
                this.supermarketId == casted.supermarketId &&
                this.fromDate.equals(casted.fromDate) &&
                this.toDate.equals(casted.toDate);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(productId, supermarketId, fromDate, toDate);
    }
}
