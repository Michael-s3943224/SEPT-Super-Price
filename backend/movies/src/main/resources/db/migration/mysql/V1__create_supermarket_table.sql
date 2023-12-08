DROP TABLE IF EXISTS supermarket;
CREATE TABLE supermarket(
    id int auto_increment primary key,
    name VARCHAR(255) NOT NULL,
    description TEXT NOT NULL,
    image_name VARCHAR(255) NOT NULL
)