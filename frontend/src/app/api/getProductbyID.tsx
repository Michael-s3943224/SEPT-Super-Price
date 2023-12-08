import React from 'react'

export type Product = {
  id: number,
  name: string,
  desc: string,
  image: string,
  brand: string,
  category: string
}

export async function getProductbyID(id: string | number) {
  // fetch data from our API
  const res = await fetch(process.env.NEXT_PUBLIC_API_URL+`/api/products/${id}`)
  
              
  // Recommendation: handle errors
  if (!res.ok) {
    // This will activate the closest `error.js` Error Boundary
    throw new Error('Failed to fetch data')
  }
  return res.json()
}
   
export default async function ProductDetails(id: any){
  //get products
  const product = await getProductbyID(id.id);

  return (
    <main> 
        <img src=''></img>
      <h1>{product.name}</h1> 
			<h2>Product details</h2>
			<p>{product.desc}</p>
    </main>
	);
}
