DROP TABLE IF EXISTS product;
CREATE TABLE product(
    id int auto_increment primary key,
    name VARCHAR(255) NOT NULL,
    description TEXT NOT NULL,
    image_name VARCHAR(255) NOT NULL,
    category VARCHAR(255) references category(name),
    brand VARCHAR(255) references brand(id)
)