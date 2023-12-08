CREATE TABLE IF NOT EXISTS supermarket_product(
    price decimal(8,2),
    supermarket_id int references supermarket(id) NOT NULL,
    product_id int references product(id) NOT NULL,
    PRIMARY KEY (supermarket_id, product_id)
)