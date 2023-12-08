'use client';
import { useState, useEffect } from 'react';
import { useRouter } from 'next/navigation';
import { fMoney } from '@/app/helper'
import UserNavbar from "@/app/components/UserNavbar";

function userOrdersCalculateTotalPrice(cart)
{
    let sum = 0;
    for (let item of cart) {
        sum += item.discountPrice;
    }
    return fMoney(sum);
}

export default function Orders()
{
    const [userOrders, setUserOrders] = useState(null);

    const login = "/login";
    const router = useRouter();

    useEffect(() => {
        const jwtToken = localStorage.getItem('jwtToken');
        if (!jwtToken) {
            router.push(login);
        }

        async function loadUserOrders() {
            try {
                const orderRes = await fetch(process.env.NEXT_PUBLIC_API_URL+'/api/user/orders', { headers: { "Authorization": `Bearer ${jwtToken}`} });
                if (!orderRes.ok) {
                    router.push(login);
                    return;
                }

                let json = await orderRes.json();
                setUserOrders(json);

            } catch (error) {
                console.error('Error fetching user info: ', error);
            }
        }
        loadUserOrders();
    }, []);

    return (
        <main>
            <UserNavbar/>
            <h1 className="text-center text-5xl font-bold text-pink-600 mb-8">User Orders</h1>
            {userOrders ?
            userOrders.map((order, index) =>
                <div className="w-3/5 rounded m-auto mb-12 bg-gray-200 p-8" key={index}>

                    <div className="mb-4">
                        <h2 className="text-center text-2xl font-bold text-pink-600">Order #{index}: </h2>
                        <div>
                            <h3><b>Name:</b> {order.details.name}</h3>
                            <h3><b>Email:</b> {order.details.email}</h3>
                            <h3><b>Mobile:</b> {order.details.mobile}</h3>
                            <h3><b>Delivery Address:</b> {order.details.deliveryAddress}</h3>
                            <h3><b>Delivery Method:</b> {order.details.deliveryMethod}</h3>
                            <h3><b>Delivery Time:</b> {order.details.deliveryTime}</h3>
                        </div>
                    </div>

                    <div className="overflow-auto mb-4">
                    <table className="border-collapse border border-gray-500 text-center">
                    <thead>
                        <tr className="bg-gray-300 outline-black">
                            <th className="border border-gray-500 p-2">Product</th>
                            <th className="border border-gray-500 p-2">Supermarket</th>
                            <th className="border border-gray-500 p-2">Name</th>
                            <th className="border border-gray-500 p-2">Quantity</th>
                            <th className="border border-gray-500 p-2">Price</th>
                            <th className="border border-gray-500 p-2">Total Price</th>
                        </tr>
                    </thead>
                    <tbody className="bg-white">
                        {order.cart.map((item, itemIndex) =>
                            <tr key={`${index}-${itemIndex}`}>
                                <td className="border border-gray-500">
                                    <img className="w-32 h-auto rounded" src={process.env.NEXT_PUBLIC_API_URL+`/api/images/products/${item.product.id}`} alt={item.supermarket.name} />
                                </td>
                                <td className="border border-gray-500">
                                    {item.supermarket.name}
                                </td>
                                <td className="border border-gray-500">
                                    {item.product.name}
                                </td>
                                <td className="border border-gray-500">
                                    {item.quantity}
                                </td>
                                <td className="border border-gray-500">
                                    ${fMoney(item.discountPrice)}
                                </td>
                                <td className="border border-gray-500">
                                    ${fMoney(item.quantity * item.discountPrice)}
                                </td>
                            </tr>
                        )}
                    </tbody>
                    </table >
                    </div>
                    <table className="border-collapse border border-gray-500 w-3/5 mx-auto">
                        <tbody>
                            <tr>
                                <td className="border border-gray-500 bg-gray-300 w-1/3 p-2 font-bold">Total Price:</td>
                                <td className="border border-gray-500 bg-white text-center p-2">${fMoney(userOrdersCalculateTotalPrice(order.cart))}</td>
                            </tr>
                        </tbody>
                    </table>
                </div>
            )
            : <p className="text-center text-xl">Loading...</p>}
        </main>
    );
}