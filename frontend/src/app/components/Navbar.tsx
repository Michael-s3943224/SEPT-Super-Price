'use client';

import React, { useState, useEffect, useRef} from "react";
import logo from "../../../assets/images/logo.png";
import Link from "next/link";
import Search from "./Search";

const Navbar = () => {
  const [searchValue, setSearchValue] = useState("")
  const handleSearch = (value:string) => {
    setSearchValue(value)
  }

  return (
    <nav className="bg-white">
      <link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons"></link>
      <div className="container mx-auto px-4 py-8 flex items-center">
        <Link className="navbar-brand" href="/" style={{ textDecoration: "none" }}>
          <img src={logo.src} alt="SuperPrice" style={{ cursor: "pointer", width: "150px", height: "auto", marginRight: "8px"}}/>
        </Link>
        <Search onSearch={handleSearch}/>
          <ul className="navbar-items">
            <Link href="/checkout/cart" passHref>
              <i className="material-icons text-gray-500">shopping_cart</i>
            </Link>
            <Link href="/user/info" passHref>
              <i className="material-icons text-gray-500">account_circle</i>
            </Link>
          </ul>
      </div>
    </nav>
  )
}

export default Navbar