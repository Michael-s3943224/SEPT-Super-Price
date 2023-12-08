DROP TABLE IF EXISTS delivery_time_slot;
CREATE TABLE delivery_time_slot(
    name VARCHAR(255) NOT NULL primary key,
    val INT NOT NULL AUTO_INCREMENT,
    KEY val (val)
);