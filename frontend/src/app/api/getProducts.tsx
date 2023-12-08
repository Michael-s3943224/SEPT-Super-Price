import React, { useEffect, useState } from 'react';
import Link from 'next/link'

export type Product = {
  id: number,
  name: string,
  category: string,
  brand: string,
};


import { useSearchParams } from 'next/navigation'
 
function SearchBar() {
  const searchParams = useSearchParams()
 
  const search = searchParams.get('search')
 
  // URL -> `/dashboard?search=my-project`
  // `search` -> 'my-project'
  return <>Search: {search}</>
}

export function getProducts() {
  // fetch data from our API
  return fetch(process.env.NEXT_PUBLIC_API_URL+'/api/products')
    .then((res) => {
      // Recommendation: handle errors
      if (!res.ok) {
        // This will activate the closest `error.js` Error Boundary
        throw new Error('Failed to fetch data');
      }
      return res.json();
    });
}

export default function ProductsList({ desiredCategory, desiredProductName }: { desiredCategory: string, desiredProductName: string }) {
  const [Products, setProducts] = useState<Product[]>([]);
  const orig = process.env.NEXT_PUBLIC_API_URL+'/api/images/products/';

  useEffect(() => {
    getProducts()
      .then((data) => setProducts(data))
      .catch((error) => {
        // Handle the error here
        console.error('Error fetching products:', error);
      });
  }, []);

  let filteredProducts = Products;

  if (desiredCategory !== "") {
    filteredProducts = filteredProducts.filter((product: Product) => product.category === desiredCategory);
  }

  if (desiredProductName !== '') {
    filteredProducts = filteredProducts.filter((product: Product) =>
      product.name.toLowerCase().includes(desiredProductName.toLowerCase())
    );
  }

  return (
    <ul>
      {filteredProducts.map((product: Product) => (
        <div key={product.id} className="product-item">
          <li>
            <img src={orig + product.id} width="100" height="100" style={{ float: 'left', marginRight: '10px' }} alt={product.name}/>
            <b>{product.name}</b> <br/> Category: {product.category}<br/> Brand: {product.brand} <br/>
            <Link href={`/product/${product.id}`}><button  className='btn btn-primary' >See Product</button></Link>
          </li>
        </div>
      ))}
    </ul>
  );
}
