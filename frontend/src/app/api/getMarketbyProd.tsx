"use client"
import React, { useState, useEffect } from 'react'
import {fMoney, handleAddToCart} from '../helper'

interface Supermarket {
  id: number;
  name: string;
}

interface Deal {
  reductionValue: number;
  reductionType: string;
  fromDate: number;
  toDate: number;
}

interface SupermarketDealPrice {
  supermarket: Supermarket;
  deal: Deal | null;
  price: number;
  discountPrice?: number;
}

interface Product {
  id: number;
  name: string;
  desc: string;
  category: string;
  brand: string;
  supermarkets: SupermarketDealPrice[];
}

export async function getMarketbyProd(prod_id:number):Promise<Product> {
  // fetch data from our API
    const headers = {
    cache: "no-cache"
  };
  const res = await fetch(process.env.NEXT_PUBLIC_API_URL+`/api/products/${prod_id}`, headers)

  // Recommendation: handle errors
  if (!res.ok) {
    // This will activate the closest `error.js` Error Boundary
    throw new Error('Failed to fetch data')
  }
  return res.json()
}

function getSupermarketPrice(supermarketDealPrice: SupermarketDealPrice): number {
    if (!supermarketDealPrice.deal) {
        return supermarketDealPrice.price;
    }

    if (supermarketDealPrice.deal.reductionType === 'proportion') {
        return supermarketDealPrice.price * (1 - supermarketDealPrice.deal.reductionValue);
    }

    return supermarketDealPrice.price - supermarketDealPrice.deal.reductionValue;
}
   
export default function SupermarketList(props: { prod_id: number}){
  //get products
  const [product, setProduct] = useState(null);
  const [isPending, setPending] = useState(true);
  const [markets, setMarkets] = useState(null);
  const [cartMessage, setCartMessage] = useState("");

  useEffect(() => {
    async function load() {
        const p = await getMarketbyProd(props.prod_id);
        setProduct(p);
        let m = p.supermarkets;
        for (let i = 0; i < m.length; i++) {
            m[i].discountPrice = getSupermarketPrice(m[i]);
        }
        m.sort((a, b) => a.discountPrice - b.discountPrice);
        setMarkets(m);
        setPending(false);
    }
    load();
  }, []);

  return (
		(isPending) ?  null : <div className="grid grid-cols-3 gap-4" style={{marginBottom: 20}}>
		    {cartMessage && (
                <div className="cart-message">
                  <p>{cartMessage}</p>
                </div>
            )}
			{markets.map((market: SupermarketDealPrice) => (
          <div className="max-w-sm p-6 border border-gray-200 rounded-lg shadow bg-gray-800 border-gray-700">
                    {(market.deal)
                    ? (
                    <div className="product-deal-circle">
                        -{
                            (market.deal.reductionType === 'proportion')
                                ? `${market.deal.reductionValue * 100}%`
                                : `$${fMoney(market.deal.reductionValue)}`
                        }
                    </div>)
                    : null
                    }
                    <img src={process.env.NEXT_PUBLIC_API_URL+`/api/images/supermarkets/${market.supermarket.id}`}/>
					<a href="#">
							<h5 className="mb-2 text-2xl font-bold tracking-tight text-stone-100 text-center">{market.supermarket.name}</h5>
					</a>
					<div className="mt-2 mb-5 flex items-center justify-between">
						<p className="product-price">
						    {
						        (!market.deal)
						            ? <span style={{color: "whitesmoke"}}>${fMoney(market.price)}</span>
                                    : <>
                                        <span className="product-old-price">${fMoney(market.price)}</span>
                                        <span className="product-new-price">${fMoney(market.discountPrice)}</span>
                                    </>
						    }
						</p>
					</div>
					<button
					   onClick={() => handleAddToCart(props.prod_id, market.supermarket.id, `"${product.name} | ${market.supermarket.name}"`, setCartMessage)}
					   className="btn btn-primary">
							Add to cart
					</button>
				</div>
      ))}
		</div>
	);
}
