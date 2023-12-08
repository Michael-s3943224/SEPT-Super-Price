package au.edu.rmit.sept.cinemas.movies.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.math.BigDecimal;

@Entity
@Table(name = "deal")
public class Deal {
    @EmbeddedId
    DealKey id;

    @Column(name = "reduction_value")
    BigDecimal reductionValue;

    @Column(name = "reduction_type")
    String reductionType;

    public Deal() {

    }

    public Deal(DealKey id, BigDecimal reductionValue, String reductionType) {
        this.id = id;
        this.reductionValue = reductionValue;
        this.reductionType = reductionType;
    }

    public DealKey getId()
    {
        return id;
    }

    public BigDecimal getReductionValue()
    {
        return reductionValue;
    }

    public String getReductionType()
    {
        return reductionType;
    }
}
