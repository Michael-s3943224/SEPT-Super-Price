DROP TABLE IF EXISTS product_order;
CREATE TABLE product_order(
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    mobile VARCHAR(255) NOT NULL,
    delivery_address VARCHAR(255) NOT NULL,
    delivery_method VARCHAR(255) NOT NULL,
    delivery_time VARCHAR(255) NOT NULL,
    user_id INT REFERENCES app_user(id)
);

DROP TABLE IF EXISTS product_order_seq;
CREATE TABLE product_order_seq(
    next_val bigint
);
INSERT INTO product_order_seq VALUES (1);
