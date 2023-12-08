'use client';

import React, { useEffect, useState } from "react";
import { ProductDeals } from "@/app/api/getProductDeals"
import logo from "../../assets/images/logo.png";
import Link from "next/link";

import { fMoney, handleAddToCart } from "./helper.tsx";

interface ProductDeal {
  supermarket: {
    id: number;
    name: string;
  },
  product: {
    id: number;
    name: string;
    category: string;
    brand: string;
  },
  deal: {
    reductionValue: number;
    reductionType: string;
    fromDate: string;
    toDate: string;
  },
  price: number;
}

export default function Home() {
  const [productDeals, setProductDeals] = useState<ProductDeal[]>([]);
  const [currentPage, setCurrentPage] = useState(1);
  const itemsPerPage = 4;
  const totalPageCount = Math.ceil(productDeals.length / itemsPerPage);
  const [cartMessage, setCartMessage] = useState("");
  
  useEffect(() => {
    ProductDeals()
      .then(data => {
        if (Array.isArray(data)) {
          setProductDeals(data);
        } else {
          console.error('Response data is not in the expected format.');
        }
      })
      .catch(error => {
        console.error('Fetch error:', error);
      });
  }, []);
  
  // go to prev page of products
  const handlePrevPage = () => {
    setCurrentPage((prevPage) => Math.max(prevPage - 1, 1));
  };

  // go to next page of products
  const handleNextPage = () => {
    setCurrentPage((prevPage) => Math.min(prevPage + 1, totalPageCount));
  };

  const startIndex = (currentPage - 1) * itemsPerPage;
  const visibleProducts = productDeals.slice(startIndex, startIndex + itemsPerPage);
  
  return (
    <main>
      <div className="container my-3">
        <div className="text-center">
          {cartMessage && (
            <div className="cart-message">
              <p>{cartMessage}</p>
            </div>
          )}
          <div className="logo-container">
            <img src={logo.src} alt="SuperPrice" />
          </div>
          <div className="product-recommendation-text">
            <h4 className="text-pink-600 font-bold text-2xl">Special Deals!</h4>
          </div>
          <div className="product-container">
            {visibleProducts.map((item, index) => (
            <div className="product-card" key={index}>
              <div className="product-deal-circle">
                -{(item.deal.reductionType === 'proportion')
                    ? `${item.deal.reductionValue * 100}%`
                    : `$${fMoney(item.deal.reductionValue)}`}
              </div>
              <Link className="product-card-link" 
                href={{
                  pathname: `/product/${item.product.id}`,
                  }}
              >
              <div className="product-card-img-group">
                <img
                  className="product-card-img-product"
                  src={process.env.NEXT_PUBLIC_API_URL+`/api/images/products/${item.product.id}`}
                  alt={item.product.name}
                />
                <img
                  className="product-card-img-supermarket"
                  src={process.env.NEXT_PUBLIC_API_URL+`/api/images/supermarkets/${item.supermarket.id}`}
                  alt={item.supermarket.id}
                />
              </div>

                <p>{item.product.name} | {item.supermarket.name}</p>
              </Link>
              <div className="product-price">
                  <span className="product-old-price">${fMoney(item.price)}</span>
                  <span className="product-new-price">
                  ${fMoney(
                    (item.deal.reductionType === 'proportion')
                      ? item.price * (1-item.deal.reductionValue)
                      : item.price - item.deal.reductionValue
                  )}
                  </span>
              </div>
              <div className="product-add-cart">
                <button className="btn btn-primary" onClick={() => handleAddToCart(item.product.id, item.supermarket.id, `"${item.product.name} | ${item.supermarket.name}"`, setCartMessage)}>
                  Add to Cart
                </button>
              </div>
            </div>
            ))}
          </div>
          <div className="pagination">
            <button className="btn btn-primary" onClick={handlePrevPage} disabled={currentPage === 1}>
              Prev
            </button>
            <span className="page-number">{currentPage}</span>
            <button className="btn btn-primary" onClick={handleNextPage} disabled={currentPage === totalPageCount}>
              Next
            </button>
          </div>
        </div>
      </div>
    </main>
  )
}