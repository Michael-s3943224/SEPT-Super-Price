'use client'
import React from 'react';
import Link from "next/link";
import { useRouter } from 'next/navigation';

export default function UserNavbar() {
    const router = useRouter();

    function handleLogout() {
        localStorage.removeItem('jwtToken');
        localStorage.removeItem('cartItems');
        router.push('/login');
    }

    return (<nav className="w-3/4 mx-auto md:w-1/2 flex flex-wrap items-center mb-6 rounded">
        <Link className="text-lg text-white text-center bg-gray-500 hover:bg-gray-600 hover:text-gray-100 py-2 rounded-l-lg flex-1" href="/user/info">User Info</Link>
        <div className="bg-white w-0.5 h-6"></div>
        <Link className="text-lg text-white text-center bg-gray-500 hover:bg-gray-600 hover:text-gray-100 py-2 flex-1" href="/user/orders">User Orders</Link>
        <div className="bg-white w-0.5 h-6"></div>
        <button
        onClick={handleLogout}
        className="bg-red-600 text-lg text-white text-center py-2 rounded-r-lg hover:bg-red-700 hover:text-gray-100 flex-1">Logout</button>
    </nav>);
}