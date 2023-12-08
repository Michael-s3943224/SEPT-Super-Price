import React, {useState} from 'react'
import ProductDetails from '@/app/api/getProductbyID'
import SupermarketList from '@/app/api/getMarketbyProd'
import { handleAddToCart } from '@/app/helper'

export default function Page({ params }: { params: { id: string } }) {
  const orig = process.env.NEXT_PUBLIC_API_URL+'/api/images/products/'
  return ( 
    <main className="container mx-auto px-16">
      <div className="grid grid-cols-2">
        <div>
          <img src = {orig + params.id} width="500" height="500"/>
        </div>
        <div className="product-details">
          <div className="product-description">
            <ProductDetails {...params}/>
          </div>
          <div className="product-configuration">
            <h2 className="mb-4">Supermarkets options</h2>
            <SupermarketList prod_id={parseInt(params.id, 10)}/>
          </div>
        </div>
      </div>
    </main>
  )
}