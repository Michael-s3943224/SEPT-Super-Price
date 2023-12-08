DROP TABLE IF EXISTS deal;
CREATE TABLE deal(
    reduction_value decimal(9,3),
    reduction_type enum('value','proportion'),
    supermarket_id int references supermarket(id),
    product_id int references product(id),
    from_date datetime NOT NULL,
    to_date datetime NOT NULL,
    primary key (supermarket_id, product_id, from_date, to_date)
)
