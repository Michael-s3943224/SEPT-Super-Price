package au.edu.rmit.sept.cinemas.movies.models;

import au.edu.rmit.sept.cinemas.movies.models.Role;
import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.sql.Time;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "app_user")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "mobile")
    private String mobile;

    @Column(name = "delivery_address")
    private String deliveryAddress;

    @Column(name = "delivery_time")
    private String deliveryTime;

    @Column(name = "delivery_method")
    private String deliveryMethod;

    @Column(name = "password")
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    public User() {

    }
    public User(
            Long id,
            String name,
            String email,
            String mobile,
            String deliveryAddress,
            String preferredDeliveryTime,
            String preferredDeliveryMethod,
            String password,
            Role role
        ) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.mobile = mobile;
        this.deliveryAddress = deliveryAddress;
        this.deliveryTime = preferredDeliveryTime;
        this.deliveryMethod = preferredDeliveryMethod;
        this.password = password;
        this.role = role;
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

    public Role getRole()
    {
        return role;
    }

    public String getDeliveryTime () {
        return deliveryTime;
    }

    public String getDeliveryMethod() {
        return deliveryMethod;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public void setDeliveryTime(String deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public void setDeliveryMethod(String deliveryMethod) {
        this.deliveryMethod = deliveryMethod;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
