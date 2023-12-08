package au.edu.rmit.sept.cinemas.movies.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class EnumRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<String> getAllDeliveryTimeSlots()
    {
        return jdbcTemplate.query(
            "SELECT e.name FROM (SELECT * FROM delivery_time_slot ORDER BY val) AS e",
            (rs, rowNum) -> {
                return rs.getString("name");
            }
        );
    }

    public List<String> getAllDeliveryMethods()
    {
        return jdbcTemplate.query(
            "SELECT e.name FROM (SELECT * FROM delivery_method ORDER BY val) AS e",
            (rs, rowNum) -> {
                return rs.getString("name");
            }
        );
    }
}
