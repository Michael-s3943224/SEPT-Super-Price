INSERT INTO supermarket_product (price, supermarket_id, product_id)
SELECT price, (SELECT MAX(id) FROM supermarket WHERE name=supermarket_name) supermarket_id,(SELECT MAX(id) FROM product WHERE name=product_name) AS product_id
FROM CSVREAD('classpath:csv/supermarket_product.csv');



