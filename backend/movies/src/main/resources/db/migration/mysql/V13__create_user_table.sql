DROP TABLE IF EXISTS app_user;
CREATE TABLE app_user(
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    mobile VARCHAR(255),
    delivery_address VARCHAR(255),
    delivery_time VARCHAR (255) REFERENCES delivery_time_slot(name),
    delivery_method VARCHAR (255) REFERENCES delivery_method(name),
    password VARCHAR(255) NOT NULL,
    role VARCHAR(255) NOT NULL,
    KEY email (email)
);

DROP TABLE IF EXISTS app_user_seq;
CREATE TABLE app_user_seq(
    next_val bigint
);
INSERT INTO app_user_seq VALUES (1);