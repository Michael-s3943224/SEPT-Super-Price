CREATE SEQUENCE app_user_seq start with 1 increment by 50;
CREATE TABLE IF NOT EXISTS app_user(
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    mobile VARCHAR(255),
    delivery_address VARCHAR(255),
    preferred_delivery_time TIME,
    preferred_delivery_method VARCHAR(255),
    password VARCHAR(255) NOT NULL,
    role VARCHAR(255) NOT NULL
);