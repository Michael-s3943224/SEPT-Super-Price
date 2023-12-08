'use client';

import React, { useEffect, useState } from 'react';
import { getProductbyID } from '@/app/api/getProductbyID';
import Link from 'next/link';
import { useRouter } from 'next/navigation';
import {fMoney} from '@/app/helper';

export default function CartPage() {
	const [cartItems, setCartItems] = useState([]);
	const [products, setProducts] = useState([]);
	const [errorMessage, setErrorMessage] = useState<string>('');
	const router = useRouter();

	// get cart items from localStorage
	useEffect(() => {
		const storedCartItems = JSON.parse(localStorage.getItem('cartItems') || '[]');
		setCartItems(storedCartItems);
	}, []);

	const handleNextButtonClick = () => {
		const hasItemsInCart = cartItems.length > 0;

		if (hasItemsInCart) {
			router.push('/checkout/delivery');
		} else {
			setErrorMessage('No items in the cart. Cannot proceed to payment.');
		}
	}

	const getSupermarketDetailsById = (supermarketId, product) => {
	    const supermarket = product.supermarkets.find(s => s.supermarket.id === supermarketId);
	    let result = {supermarketName: null, price: null}
	    if (supermarket) {
	        result.supermarketName = supermarket.supermarket.name;
	        result.price = calculateDiscountPrice(supermarket.price, supermarket.deal);
	    }
	    return result;
	}

	// get product details based on cart items when cart items change
	useEffect(() => {
		const fetchProductDetails = async () => {
			const productDetails = await Promise.all(
				cartItems.map(async (item) => {
				const product = await getProductbyID(item.productId);
				const {supermarketName, deal, price } = getSupermarketDetailsById(item.supermarketId, product);
				return { ...product, quantity: item.quantity, price, supermarketName, supermarketId: item.supermarketId };
				})
			);
			setProducts(productDetails);
		};

		fetchProductDetails();
	}, [cartItems]);

	const handleRemoveFromCart = (productId, supermarketId) => {
		const confirmed = window.confirm('Are you sure you want to remove this item from the cart?');
		if (confirmed) {
			const updatedCartItems = cartItems.filter((item) => !(item.productId === productId && item.supermarketId === supermarketId));
			setCartItems(updatedCartItems);
			localStorage.setItem('cartItems', JSON.stringify(updatedCartItems));
		}
	};

	// increment the quantity of a product in the cart
	const handleIncrement = (productId, supermarketId) => {
		const updatedCartItems = cartItems.map((item) => {
			if (item.productId === productId && item.supermarketId === supermarketId) {
				return { ...item, quantity: item.quantity + 1 };
			}
			return item;
		});
		setCartItems(updatedCartItems);
		localStorage.setItem('cartItems', JSON.stringify(updatedCartItems));
	};

	// decrement the quantity of a product in the cart
	const handleDecrement = (productId, supermarketId) => {
		const updatedCartItems = cartItems.map((item) => {
			if (item.productId === productId && item.supermarketId === supermarketId) {
			    console.log("found match");
				const updatedQuantity = item.quantity - 1;
				if (updatedQuantity > 0) {
					return { ...item, quantity: updatedQuantity };
				} else {
					// ask user for confirmation before removing item from cart
					handleRemoveFromCart(productId);
					return null;
				}
			}
			return item;
		}).filter(Boolean);
		setCartItems(updatedCartItems);
		localStorage.setItem('cartItems', JSON.stringify(updatedCartItems));
	};

	function calculateDiscountPrice(price, deal) {
        if (!deal) {
            return fMoney(price);
        }

        if (deal.reductionType === 'proportion') {
            return fMoney(price * (1-deal.reductionValue))
        }

        return fMoney(price - deal.reductionValue);
	}

	function calculateTotalPrice(products) {
		const totalPrice = products.reduce((accumulator, product) => {
			return accumulator + product.price * product.quantity;
		}, 0);
		return totalPrice;
	}

	return (
		<main>
			<h1 className="text-center text-5xl font-bold text-pink-600 mb-8">Cart</h1>
			<div className="overflow-auto">
			<table className="cart-table">
				<thead>
					<tr>
						<th>Product</th>
						<th>Supermarket</th>
						<th>Name</th>
						<th>Quantity</th>
						<th>Price</th>
						<th>Total Price</th>
					</tr>
				</thead>
				<tbody>
				{products.map((product, index) => (
					<tr key={index}>
						<td>
							<Link className="cart-item-image" href={{ pathname: `/product/${product.id}` }}>
							<img src={`http://localhost:8080/api/images/products/${product.id}`} alt={product.name} />
							</Link>
						</td>
						<td>
							{product.supermarketName}
						</td>
						<td>
							{product.name}
						</td>
						<td>
							{product.quantity}{' '}
							<div className="quantity-buttons">
							<button className="btn btn-small" onClick={() => handleDecrement(product.id, product.supermarketId)}>
								-
							</button>
							<button className="btn btn-small" onClick={() => handleIncrement(product.id, product.supermarketId)}>
								+
							</button>
							</div>
						</td>
						<td>
							${fMoney(product.price)}
						</td>
						<td>
							${fMoney(product.price * product.quantity)}
						</td>
					</tr>
				))}
				</tbody>
			</table>
			</div>
			<table className='total-price-table'>
				<tbody>
					<tr>
						<td>Total Price:</td>
						<td>${fMoney(calculateTotalPrice(products))}</td>
					</tr>
				</tbody>
			</table>
			{errorMessage && <p className="error-message">{errorMessage}</p>}
			<div className="btn-container">
				<button onClick={handleNextButtonClick} className="btn btn-primary">
				Next
				</button>
			</div>
		</main>
	);
}
