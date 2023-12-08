CREATE TABLE IF NOT EXISTS product(
    id int auto_increment primary key,
    name VARCHAR(255) NOT NULL,
    description TEXT NOT NULL,
    image_name VARCHAR(255) NOT NULL,
    category Category,
    brand Brand
)