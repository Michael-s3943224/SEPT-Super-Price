CREATE TEMPORARY TABLE tmp_deal(
    reduction_value decimal(9,3),
    reduction_type enum('value','proportion'),
    supermarket_name varchar(255) NOT NULL,
    product_name varchar(255) NOT NULL,
    from_date datetime NOT NULL,
    to_date datetime NOT NULL
);

INSERT INTO tmp_deal(reduction_value, reduction_type, supermarket_name, product_name, from_date, to_date) VALUES
(0.5,'proportion','Coles','Coca - Cola Classic Soft Drink 1.25L','2000-01-01','2100-01-01'),
(0.5,'proportion','IGA','Coca - Cola Classic Soft Drink 1.25L','2000-01-01','2100-01-01'),
(0.5,'proportion','Coles','Coca - Cola Zero Sugar Soft Drink Bottle 1.25l','2000-01-01','2100-01-01'),
(0.5,'proportion','IGA','Coca - Cola Zero Sugar Soft Drink Bottle 1.25l','2000-01-01','2100-01-01'),
(0.5,'proportion','Coles','Coca - Cola Vanilla Soft Drink Bottle 1.25l','2000-01-01','2100-01-01'),
(0.5,'proportion','IGA','Coca - Cola Vanilla Soft Drink Bottle 1.25l','2000-01-01','2100-01-01'),
(0.5,'proportion','Coles','Sprite Lemonade Soft Drink 1.25L','2000-01-01','2100-01-01'),
(0.5,'proportion','IGA','Sprite Lemonade Soft Drink 1.25L','2000-01-01','2100-01-01'),
(0.5,'proportion','Coles','Sprite Zero Sugar Lemonade Soft Drink Bottle 1.25l','2000-01-01','2100-01-01'),
(0.5,'proportion','IGA','Sprite Zero Sugar Lemonade Soft Drink Bottle 1.25l','2000-01-01','2100-01-01'),
(0.5,'proportion','Coles','Sprite Lemon+ Soft Drink 1.25l','2000-01-01','2100-01-01'),
(0.5,'proportion','IGA','Sprite Lemon+ Soft Drink 1.25l','2000-01-01','2100-01-01'),
(1.20,'value','Woolworths','Mountain Dew Soft Drink Energised Bottle 1.25l','2000-01-01','2100-01-01'),
(6.60,'value','Woolworths','Mountain Dew Soft Drink Cans 375ml X10 Pack','2000-01-01','2100-01-01'),
(1.00,'value','Woolworths','Arnott''s Cream Favourites Assorted Biscuits 500g','2000-01-01','2100-01-01'),
(0.5,'proportion','Coles','Kitkat 4 Finger Milk Chocolate Bar 45g','2000-01-01','2100-01-01'),
(0.5,'proportion','Coles','Nestle Aero Peppermint 40g Bar','2000-01-01','2100-01-01'),
(1.65,'value','Woolworths','Nestle Smarties Chocolate Block 180g','2000-01-01','2100-01-01'),
(0.3,'proportion','IGA','Nestle Smarties Chocolate Block 180g','2000-01-01','2100-01-01'),
(0.2,'proportion','IGA','Helga''s Bread Traditional Wholemeal 750g','2000-01-01','2100-01-01'),
(0.2,'proportion','IGA','Helga''s Grain Bread Mixed Grain 850g','2000-01-01','2100-01-01'),
(0.2,'proportion','IGA','Helga''s Pumpkin Seed & Grain 720g','2000-01-01','2100-01-01'),
(21.60,'value','Woolworths','Finish Ultimate Pro Dishwasher Tablets Lemon 46 Pack','2000-01-01','2100-01-01'),
(0.40,'value','Coles','Kleenex Aloe Vera & Vitamin E 3 Ply Facial Tissues 95 Pack','2000-01-01','2100-01-01'),
(0.40,'value','Woolworths','Kleenex Aloe Vera & Vitamin E 3 Ply Facial Tissues 95 Pack','2000-01-01','2100-01-01'),
(0.35,'value','Woolworths','Kleenex Large & Thick 3 Ply Facial Tissues 95 Pack','2000-01-01','2100-01-01'),
(0.50,'value','Woolworths','Vip Chunkers With Beef, Peas & Carrot 1kg','2000-01-01','2100-01-01'),
(4.00,'value','Woolworths','Asahi Super Dry Lager Bottles 330ml X24 Case','2000-01-01','2100-01-01'),
(1.50,'value','Woolworths','Asahi Super Dry 0% Bottles 330ml X 6 Pack','2000-01-01','2100-01-01'),
(6.00,'value','Coles','Bundaberg Underproof Rum 700ml','2000-01-01','2100-01-01'),
(1.55,'value','Coles','Bundaberg Sparkling Passionfruit Drink 375ml X4 Pack','2000-01-01','2100-01-01'),
(1.55,'value','Coles','Bundaberg Sparkling Guava Sparkling Drink 375ml X4 Pack','2000-01-01','2100-01-01'),
(4.50,'value','Coles','Ben & Jerry’s Ice Cream Tub Chocolate Chip Cookie Dough 458 Ml','2000-01-01','2100-01-01'),
(4.50,'value','Coles','Ben & Jerry''s Topped Tirumisu Ice Cream Tub 436ml','2000-01-01','2100-01-01'),
(4.50,'value','Coles','Ben & Jerry’s Ice Cream Tub Chocolate Fudge Brownie 458 Ml','2000-01-01','2100-01-01'),
(0.5,'proportion','IGA','Ben & Jerry’s Ice Cream Tub Chocolate Chip Cookie Dough 458 Ml','2000-01-01','2100-01-01'),
(0.5,'proportion','IGA','Ben & Jerry''s Topped Tirumisu Ice Cream Tub 436ml','2000-01-01','2100-01-01'),
(0.5,'proportion','IGA','Ben & Jerry’s Ice Cream Tub Chocolate Fudge Brownie 458 Ml','2000-01-01','2100-01-01');

INSERT INTO deal(reduction_value, reduction_type, supermarket_id, product_id, from_date, to_date)
SELECT reduction_value, reduction_type,
(SELECT MAX(id) FROM supermarket WHERE name = supermarket_name),
(SELECT MAX(id) FROM product WHERE name = product_name),
from_date, to_date
FROM tmp_deal;

DROP TABLE tmp_deal;


