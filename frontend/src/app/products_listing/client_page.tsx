'use client';
import React, {useState, useEffect} from 'react'
import { useRouter,usePathname,useSearchParams } from 'next/navigation'



export default function ClientPage({filters} : any) {
  const data = filters;
  const brands = data.filters.brand;
  const category = data.filters.category;
  const supermarket = data.filters.supermarket;
  const router = useRouter()
  const pathname = usePathname();
  const searchParams = useSearchParams();
  const [inputBrand, setInputBrand] = useState('');
  const [inputCategory, setInputCategory] = useState('');
  const [inputSupermarket, setInputSupermarket] = useState('');
  const [inputMinPrice, setInputMinPrice] = useState('');
  const [inputMaxPrice, setInputMaxPrice] = useState('');

  const handleFormSubmit = (event: React.FormEvent<HTMLFormElement>) => {
    event.preventDefault();
    const newUrl = `${window.location.pathname}?search=${searchParams.get('search')}&brand=${inputBrand}&category=${inputCategory}&supermarket=${inputSupermarket}&above_price=${inputMinPrice}&below_price=${inputMaxPrice}`;
    router.push(newUrl);
    window.location.reload();
  };

  return (
    <main className="container my-3">
      <h1>
        <b>Products</b>
      </h1>
      <div>
        <h1>
          <br />
          <b>Select a category:</b>
        </h1>
        <form onSubmit={handleFormSubmit}>
          <label>Brand:</label>
          <select className="filtering"
            name="brand"
            id="brand"
            value={inputBrand}
            onChange={(e) => setInputBrand(e.target.value)}
          >
            {brands.map((brand: string) => (
              <option key={brand}>{brand}</option>
            ))}
          </select>

          <label>Supermarket:</label>
          <select className="filtering"
            name="supermarket"
            id="supermarket"
            value={inputSupermarket}
            onChange={(e) => setInputSupermarket(e.target.value)}
          >
            {supermarket.map((supermarket: string) => (
              <option key={supermarket}>{supermarket}</option>
            ))}
          </select>

          <label>Category:</label>
          <select className="filtering"
            name="category"
            id="category"
            value={inputCategory}
            onChange={(e) => setInputCategory(e.target.value)}
          >
            {category.map((category: string) => (
              <option key={category}>{category}</option>
            ))}
          </select>

          <label>Minimum Price:</label>
          <input className="filtering"
            type="text"
            id="minPrice"
            name="minPrice"
            className="dollar-symbol"
            placeholder="$"
            value={inputMinPrice}
            onChange={(e) => setInputMinPrice(e.target.value)}
          />

          <label>Maximum Price:</label>
          <input className="filtering"
            type="text"
            id="maxPrice"
            name="maxPrice"
            className="dollar-symbol"
            placeholder="$"
            value={inputMaxPrice}
            onChange={(e) => setInputMaxPrice(e.target.value)}
          />

          <input type="submit" value="Apply Filters" onClick={() => window.location.reload()} />
        </form>
        <a className='searching'>Searching for {searchParams.get('search')}</a>
      </div>
    </main>
  );
};
