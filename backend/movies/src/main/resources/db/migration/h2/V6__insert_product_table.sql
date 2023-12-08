INSERT INTO product (name,description,image_name,category,brand)
SELECT name,description,image_name,category,brand
FROM CSVREAD('classpath:csv/product.csv')








