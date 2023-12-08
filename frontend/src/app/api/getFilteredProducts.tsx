import Link from 'next/link'
//import { useState } from 'react';

export async function getProducts(url: string){

  // fetch data from our API
  return fetch(url, {cache: "no-cache"})
    .then((res) => {
      // Recommendation: handle errors
      if (!res.ok) {
        // This will activate the closest `error.js` Error Boundary
        throw new Error('Failed to fetch data');
      }
      return res.json();
    });
}

/*const [currentPage, setCurrentPage] = useState(1);
const itemsPerPage = 4;*/


export async function FilteredProducts(url: any) {
  const orig = process.env.NEXT_PUBLIC_API_URL+'/api/images/products/';
  const filteredProducts = await getProducts(url.url);

  
 //const totalPageCount = Math.ceil(filteredProducts.length / itemsPerPage);

  /*// go to prev page of products
  const handlePrevPage = () => {
    setCurrentPage((prevPage) => Math.max(prevPage - 1, 1));
  };

  // go to next page of products
  const handleNextPage = () => {
    setCurrentPage((prevPage) => Math.min(prevPage + 1, totalPageCount));
  }; */

  return (
    <div className="container my-3">
			<ul>
				{filteredProducts.map((product: any) => (
					<div key={product.product.id} className="product-item">
						<li>
							<img src={orig + product.product.id} width="100" height="100" style={{ float: 'left', marginRight: '10px' }} alt={product.product.name}/>
							<b>{product.name}</b> <br/> Category: {product.product.category}<br/> Brand: {product.product.brand} <br/>
							<Link href={`/product/${product.product.id}`}><button  className='btn btn-primary' >See Product</button></Link>
						</li>
					</div>
				))}
			</ul>
		</div>
        /*<div className="container my-3">
          <div className="product-container">
            {filteredProducts.map((product: any) => (
            <div className="product-card">
              <Link className="product-card-link" 
                href={{
                  pathname: `/product/${product.product.id}`,
                  }}
              >
              <div className="product-card-img-group">
                <img
                  className="product-card-img-product"
                  src={process.env.NEXT_PUBLIC_API_URL+`/api/images/products/${product.product.id}`}
                  alt={product.product.name}
                />
              </div>

                <p><b>{product.product.name}</b> | Category: {product.product.category} | Brand: {product.product.brand}</p>
              </Link>
              <Link href={`/product/${product.product.id}`}><button  className='btn btn-primary' >See Product</button></Link>
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
    */
  );
}
