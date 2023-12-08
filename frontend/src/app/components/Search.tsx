'use client'
import React, {FormEvent, useState} from 'react'
import { useRouter } from 'next/navigation'

const Search = () => {
  const router = useRouter()
  const [inputSearch, setInputSearch] = useState("");
  
  const onChange = (e: any) => {
    setInputSearch(e.target.value);
  }

  // const [inputSelect, setInputSelect] = useState("");
  
  // const onSelect = (e: any) => {
  //   setInputSelect(e.target.value);
  // }
  
  const handleSubmit = (event: any) => {
    if (event.key === "Enter") {
      event.preventDefault();
      router.push(`/products_listing?search=${inputSearch}`);
      router.refresh();
    }
  }

  return (
    <div className="w-full max-w-xs xl:max-w-lg 2xl:max-w-2xl bg-gray-100 rounded-md hidden xl:flex items-center">
        {/* <select onChange={onSelect} className="bg-transparent uppercase text-sm p-4 mr-4 font-sans text-gray-600" name="category" id="category">
          <option value=''>all categories</option>
          <option>Food</option>
        </select> */}
        <div className="bg-transparent uppercase text-sm p-4 mr-4 font-sans text-gray-600" name="category" id="category">
          Search
        </div>
        <input className="border-l border-gray-300 bg-transparent text-sm pl-4" type={"search"} placeholder="I'm searching for..." name={"search"} onChange={onChange} onKeyDown={handleSubmit}/>
      <svg className="ml-auto h-5 px-4 text-gray-500" aria-hidden="true" focusable="false" data-prefix="far" data-icon="search" role="img" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 512 512" ><path fill="currentColor" d="M508.5 468.9L387.1 347.5c-2.3-2.3-5.3-3.5-8.5-3.5h-13.2c31.5-36.5 50.6-84 50.6-136C416 93.1 322.9 0 208 0S0 93.1 0 208s93.1 208 208 208c52 0 99.5-19.1 136-50.6v13.2c0 3.2 1.3 6.2 3.5 8.5l121.4 121.4c4.7 4.7 12.3 4.7 17 0l22.6-22.6c4.7-4.7 4.7-12.3 0-17zM208 368c-88.4 0-160-71.6-160-160S119.6 48 208 48s160 71.6 160 160-71.6 160-160 160z"></path></svg>
    </div>
  )
}

export default Search