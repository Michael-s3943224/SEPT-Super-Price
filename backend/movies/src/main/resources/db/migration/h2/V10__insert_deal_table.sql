INSERT INTO deal (reduction_value,reduction_type,supermarket_id,product_id,from_date,to_date)
SELECT reduction_value, reduction_type, (SELECT MAX(id) FROM supermarket WHERE name=supermarket_name) supermarket_id,(SELECT MAX(id) FROM product WHERE name=product_name) AS product_id, from_date, to_date
FROM CSVREAD('classpath:csv/deal.csv');