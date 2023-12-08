'use client';

import { useState } from 'react';
import { useRouter } from 'next/navigation';

export default function LoginPage()
{
    const [formData, setFormData] = useState({ email: "", password: ""});
    const [isPending, setPending] = useState(false);
    const router = useRouter();

    const handleChange = (e) => {
        const { name, value } = e.target;
        setFormData({...formData, [name]: value});
    };

    const handleSubmit = async (e) => {
        e.preventDefault();

        setPending(true);
        const res = await fetch(process.env.NEXT_PUBLIC_API_URL+'/api/auth/authenticate', {
            method: 'POST',
            headers: { "Content-Type": "application/json"},
            body: JSON.stringify(formData)
        });

        if (!res.ok) {
            alert("Invalid Email or Password!");
        } else {
            const json = await res.json();
            localStorage.removeItem('cartItems');
            localStorage.setItem("jwtToken", json.token);
            router.push('/user/info');
        }

        setPending(false);
    };

    return (
        <main>
            <h1 className="text-center text-5xl font-bold text-pink-600 mb-16">Login</h1>
            <div className="flex flex-wrap w-full">
                <form className="w-full" onSubmit={handleSubmit}>
                    <div className="grid grid-cols-12 gap-3 mb-10">
                        <div className="flex items-center justify-end col-start-1 col-span-4 md:col-start-4 md:col-span-1">
                            <label className="text-gray-500 font-bold mb-1 md:mb-0 whitespace-nowrap mr-2" htmlFor="email">Email: </label>
                        </div>
                        <div className="flex items-center col-start-5 col-span-6 md:col-span-4">
                            <input className="appearance-none border-2 border-gray-200 rounded w-full py-2 px-4 text-gray-700 leading-tight focus:outline-none focus:bg-white focus:border-gray-800"
                                type="text"
                                id="email"
                                name="email"
                                onChange={handleChange}
                            />
                        </div>
                    </div>
                    <div className="grid grid-cols-12 gap-3 mb-10">
                        <div className="flex items-center justify-end col-start-1 col-span-4 md:col-start-4 md:col-span-1">
                            <label className="text-gray-500 font-bold mb-1 md:mb-0 whitespace-nowrap mr-2" htmlFor="password">Password: </label>
                        </div>
                        <div className="flex items-center col-start-5 col-span-6 md:col-span-4">
                            <input className="appearance-none border-2 border-gray-200 rounded w-full py-2 px-4 text-gray-700 leading-tight focus:outline-none focus:bg-white focus:border-gray-800"
                                type="password"
                                id="password"
                                name="password"
                                onChange={handleChange}
                            />
                        </div>
                    </div>
                    <div className="grid grid-cols-12 gap-3 mb-8">
                        <div className="flex items-center justify-center col-start-5 col-span-6 md:col-span-4 md:col-start-5">
                            {!isPending && <button className="btn btn-primary w-full md:w-1/3">Login</button>}
                            {isPending &&
                            <button className="btn btn-primary w-full md:w-1/3" disabled>
                                    <div
                                    className="inline-block h-6 w-6 animate-spin rounded-full border-4 border-solid border-current border-r-transparent align-[-0.125em] motion-reduce:animate-[spin_1.5s_linear_infinite]"
                                    role="status">
                                    </div>
                            </button>
                            }
                        </div>
                    </div>
                    <div className="inline-flex items-center justify-center w-full">
                        <hr className="w-96 h-px my-8 bg-gray-200 border-0 dark:bg-gray-700"/>
                        <span className="absolute px-3 text-gray-500 -translate-x-1/2 bg-white left-1/2 dark:text-white dark:bg-gray-900">New to SuperPrice?</span>
                    </div>
                    <a href="/register" className="block text-center underline text-blue-600 hover:text-blue-800 visited:text-purple-600 hover:cursor-pointer">Register</a>
                </form>
            </div>
        </main>
    )
}