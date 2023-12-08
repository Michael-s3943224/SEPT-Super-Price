package au.edu.rmit.sept.cinemas.movies.models;

import jakarta.persistence.*;

@Entity
@Table(name = "supermarket")
public class Supermarket {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String desc;

    @Column(name = "image_name")
    private String imageName;

    public Supermarket() {

    }

    public Supermarket(Long id, String name, String desc, String imageName)
    {
        this.id = id;
        this.name = name;
        this.desc = desc;
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

    public String getImageName()
    {
        return imageName;
    }

}
