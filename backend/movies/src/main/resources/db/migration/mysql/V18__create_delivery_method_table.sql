DROP TABLE IF EXISTS delivery_method;
CREATE TABLE delivery_method(
    name VARCHAR(255) NOT NULL primary key,
    val INT NOT NULL AUTO_INCREMENT,
    KEY val (val)
);