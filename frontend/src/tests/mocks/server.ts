import { setupServer } from 'msw/node'
import { rest } from 'msw'

const url = import.meta.env.VITE_API_URL = "http://localhost:8080"

export const product1 = 
{
	id: 1,
	name: "Coca - Cola Zero Sugar Soft Drink Bottle 1.25l",
	desc: "Coca-Cola Zero Sugar is a deliciously light choice for those who want the classic taste of Coke with no sugar. Whether sharing with family and friends or accompanying a meal, Coca-Cola Zero Sugar has a pack for almost any occasion. Coca-Cola Zero Sugar soft drink is available in cans, mini cans, single serve and sharing size bottles as well as multipacks.",
	category: "Drinks",
	brand: "Coca-Cola",
	supermarkets:[{"supermarket":{"id":1,"name":"Coles"},"price":3.40,"deal":{"reductionValue":0.500,"reductionType":"proportion","fromDate":946645200000,"toDate":4102405200000}},{"supermarket":{"id":2,"name":"Woolworths"},"price":3.55,"deal":null},{"supermarket":{"id":4,"name":"IGA"},"price":3.55,"deal":{"reductionValue":0.500,"reductionType":"proportion","fromDate":946645200000,"toDate":4102405200000}}]
}

export const product_1 = null

export const productList = 
[{"id":1,"name":"Coca - Cola Classic Soft Drink 1.25L","category":"Drinks","brand":"Coca-Cola"},{"id":2,"name":"Coca - Cola Zero Sugar Soft Drink Bottle 1.25l","category":"Drinks","brand":"Coca-Cola"},{"id":3,"name":"Coca - Cola Vanilla Soft Drink Bottle 1.25l","category":"Drinks","brand":"Coca-Cola"},{"id":4,"name":"Sprite Lemonade Soft Drink 1.25L","category":"Drinks","brand":"Sprite"},{"id":5,"name":"Sprite Zero Sugar Lemonade Soft Drink Bottle 1.25l","category":"Drinks","brand":"Sprite"},{"id":6,"name":"Sprite Lemon+ Soft Drink 1.25l","category":"Drinks","brand":"Sprite"},{"id":7,"name":"Mountain Dew Soft Drink Energised Bottle 1.25l","category":"Drinks","brand":"Mountain Dew"},{"id":8,"name":"Mountain Dew Soft Drink Cans 375ml X10 Pack","category":"Drinks","brand":"Mountain Dew"},{"id":9,"name":"Mountain Dew Major Melon Soft Drink Bottle 600ml","category":"Drinks","brand":"Mountain Dew"},{"id":10,"name":"Driscoll's Fresh Blueberries Punnet 170g","category":"Fruit & Vegetables","brand":"Driscoll's"},{"id":11,"name":"Fresh Raspberry 125g","category":"Fruit & Vegetables","brand":"Driscoll's"},{"id":12,"name":"Blackberry Fresh 125g","category":"Fruit & Vegetables","brand":"Driscoll's"},{"id":13,"name":"Arnott's Cream Favourites Assorted Biscuits 500g","category":"Pantry","brand":"Arnott's"},{"id":14,"name":"Arnott's Scotch Finger Plain Biscuits 250g","category":"Pantry","brand":"Arnott's"},{"id":15,"name":"Arnott's Family Favourites Assorted Biscuits 500g","category":"Pantry","brand":"Arnott's"},{"id":16,"name":"Kitkat 4 Finger Milk Chocolate Bar 45g","category":"Pantry","brand":"Nestle"},{"id":17,"name":"Nestle Aero Peppermint 40g Bar","category":"Pantry","brand":"Nestle"},{"id":18,"name":"Nestle Smarties Chocolate Block 180g","category":"Pantry","brand":"Nestle"},{"id":19,"name":"Helga's Bread Traditional Wholemeal 750g","category":"Bakery","brand":"Helga's"},{"id":20,"name":"Helga's Grain Bread Mixed Grain 850g","category":"Bakery","brand":"Helga's"},{"id":21,"name":"Helga's Pumpkin Seed & Grain 720g","category":"Bakery","brand":"Helga's"},{"id":22,"name":"Primo Turkey Breast 80g","category":"Deli","brand":"Primo"},{"id":23,"name":"Primo Pastrami 80g","category":"Deli","brand":"Primo"},{"id":24,"name":"Primo Champagne Ham Thinly Sliced 100g","category":"Deli","brand":"Primo"},{"id":25,"name":"Quilton Toilet Tissue 3 Ply White 180 Sheets 24 Pack","category":"Household","brand":"Quilton"},{"id":26,"name":"Quilton Double Length Toilet Tissue 3ply White 360 Sheets 8 Pack","category":"Household","brand":"Quilton"},{"id":27,"name":"Quilton Classic Toilet Tissue 3 Ply 8 Pack","category":"Household","brand":"Quilton"},{"id":28,"name":"Finish Ultimate Pro Dishwasher Tablets Lemon 46 Pack","category":"Household","brand":"Finish"},{"id":29,"name":"Finish Rinse & Shine Aid Liquid Regular 500ml","category":"Household","brand":"Finish"},{"id":30,"name":"Finish Concentrated Dishwasher Powder Classic Lemon Sparkle 2kg","category":"Household","brand":"Finish"},{"id":31,"name":"Kleenex Aloe Vera & Vitamin E 3 Ply Facial Tissues 95 Pack","category":"Household","brand":"Kleenex"},{"id":32,"name":"Kleenex Everyday 2 Ply Facial Tissues 200 Pack","category":"Household","brand":"Kleenex"},{"id":33,"name":"Kleenex Large & Thick 3 Ply Facial Tissues 95 Pack","category":"Household","brand":"Kleenex"},{"id":34,"name":"Huggies Thick Baby Wipes Fragrance Free 80 Pack","category":"Baby","brand":"Huggies"},{"id":35,"name":"Huggies Thick Baby Wipes Coconut Oil 80 Pack","category":"Baby","brand":"Huggies"},{"id":36,"name":"Huggies Thick Baby Wipes Cucumber & Aloe 80 Pack","category":"Baby","brand":"Huggies"},{"id":37,"name":"A2 Full Cream Milk 2l","category":"Dairy, Eggs & Fridge","brand":"A2"},{"id":38,"name":"A2 Light Milk 2l","category":"Dairy, Eggs & Fridge","brand":"A2"},{"id":39,"name":"A2 Milk Lactose Free Light Milk 2l","category":"Dairy, Eggs & Fridge","brand":"A2"},{"id":40,"name":"Vip Chunkers With Beef, Peas & Carrot 1kg","category":"Pet","brand":"VIP"},{"id":41,"name":"Vip Paws Adult Chilled Fresh Dog & Cat Food Mince 1kg","category":"Pet","brand":"VIP"},{"id":42,"name":"Vip Paws Adult Chilled Fresh Dog & Cat Food Chicken Mince 1kg","category":"Pet","brand":"VIP"},{"id":43,"name":"Asahi Super Dry Lager Bottles 330ml X24 Case","category":"Liquor","brand":"Asahi"},{"id":44,"name":"Asahi Super Dry 0% Bottles 330ml X 6 Pack","category":"Liquor","brand":"Asahi"},{"id":45,"name":"Asahi Super Dry Lager Bottles 330ml X 6 Pack","category":"Liquor","brand":"Asahi"},{"id":46,"name":"Bundaberg Underproof Rum 700ml","category":"Liquor","brand":"Bundaberg"},{"id":47,"name":"Bundaberg Sparkling Passionfruit Drink 375ml X4 Pack","category":"Drinks","brand":"Bundaberg"},{"id":48,"name":"Bundaberg Sparkling Guava Sparkling Drink 375ml X4 Pack","category":"Drinks","brand":"Bundaberg"},{"id":49,"name":"Ben & Jerry’s Ice Cream Tub Chocolate Chip Cookie Dough 458 Ml","category":"Frozen","brand":"Ben&Jerry's"},{"id":50,"name":"Ben & Jerry's Topped Tirumisu Ice Cream Tub 436ml","category":"Frozen","brand":"Ben&Jerry's"},{"id":51,"name":"Ben & Jerry’s Ice Cream Tub Chocolate Fudge Brownie 458 Ml","category":"Frozen","brand":"Ben&Jerry's"}]

export const handlers = [
  rest.get(url+'/api/products/1', (req, res, ctx) => {
	return res(ctx.status(200), ctx.json(product1))
  }),
	rest.get(url+'/api/products', (req, res, ctx) => {
	return res(ctx.status(200), ctx.json(productList))
  }),
  rest.get(url+'/api/products/-1', (req, res, ctx) => {
	return res(ctx.status(200), ctx.json(product_1))
  }),

]

// This configures a Service Worker with the given request handlers.
export const server = setupServer(...handlers)