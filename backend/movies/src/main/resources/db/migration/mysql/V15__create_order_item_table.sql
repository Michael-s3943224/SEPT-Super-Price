DROP TABLE IF EXISTS order_item;
CREATE TABLE order_item(
    order_id INT REFERENCES product_order(id),
    product_id INT REFERENCES product(id),
    supermarket_id INT REFERENCES supermarket(id),
    quantity INT,
    discount_price DECIMAL(9,3),
    PRIMARY KEY (order_id, product_id, supermarket_id)
)