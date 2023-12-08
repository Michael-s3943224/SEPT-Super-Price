DROP TABLE IF EXISTS supermarket_product;
CREATE TABLE supermarket_product(
    price decimal(8,2),
    supermarket_id int references supermarket(id),
    product_id int references product(id),
    PRIMARY KEY (supermarket_id, product_id)
)