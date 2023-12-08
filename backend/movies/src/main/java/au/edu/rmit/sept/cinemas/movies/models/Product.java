package au.edu.rmit.sept.cinemas.movies.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.List;
import java.util.Map;

@Entity
@Table(name = "product")
public class Product
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column (name = "description")
    private String desc;

    @Column (name = "brand")
    private String brand;

    @Column (name = "category")
    private String category;

    @Column(name = "image_name")
    private String imageName;

    @OneToMany
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    List<SupermarketProduct> supermarketProducts;

    public Product() {

    }

    public Product(Long id, String name, String desc, String brand, String category, String imageName)
    {
        this.id = id;
        this.name = name;
        this.desc = desc;
        this.brand = brand;
        this.category = category;
        this.imageName = imageName;
    }

    public Long getId()
    {
        return id;
    }

    public String getName()
    {
        return name;
    }

    public String getDesc()
    {
        return desc;
    }

    public String getBrand()
    {
        return brand;
    }

    public String getCategory()
    {
        return category;
    }

    public String getImageName()
    {
        return imageName;
    }

    public List<SupermarketProduct> getSupermarketProducts()
    {
        return supermarketProducts;
    }

}