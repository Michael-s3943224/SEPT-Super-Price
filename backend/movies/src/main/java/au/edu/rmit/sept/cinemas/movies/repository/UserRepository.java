package au.edu.rmit.sept.cinemas.movies.repository;

import au.edu.rmit.sept.cinemas.movies.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    /*@Modifying
    @Transactional
    @Query(
        nativeQuery = true,
        value =
        "UPDATE app_user SET\n" +
        "name = :name,\n" +
        "mobile = :mobile,\n" +
        "delivery_address = :delivery_address,\n" +
        "delivery_method = :delivery_method,\n" +
        "delivery_time = :delivery_time\n" +
        "WHERE id = :id"
    )
    void updateUserInfo(
            @Param("id") Long id,
            @Param("name") String name,
            @Param("mobile") String mobile,
            @Param("delivery_address") String deliveryAddress,
            @Param("delivery_method") String deliveryMethod,
            @Param("delivery_time") String deliveryTime);

    @Modifying
    @Transactional
    @Query(
        nativeQuery = true,
        value =
        "UPDATE app_user SET\n" +
        "password = :password\n" +
        "WHERE id = :id"
    )
    void updateUserPassword(
            @Param("id") Long id,
            @Param("password") String password
    );*/
}
