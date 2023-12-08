'use client';
import { useState, useEffect } from 'react';
import { useRouter } from 'next/navigation';
import UserNavbar from "@/app/components/UserNavbar";

export default function Profile()
{
    const [dropDownValues, setDropDownValues] = useState(null);
    const [formData, setFormData] = useState(null);
    const [email, setEmail] = useState(null);
    const [isPending, setPending] = useState(false);
    const router = useRouter();

    const login = '/login';

    useEffect(() => {
        const jwtToken = localStorage.getItem('jwtToken');
        if (!jwtToken) {
            router.push(login);
        }

        async function loadUserProfileForm() {
            const dropDownValuesRes = await fetch(process.env.NEXT_PUBLIC_API_URL+'/api/drop-down/delivery');
            const dropDownValuesJson = await dropDownValuesRes.json();
            setDropDownValues(dropDownValuesJson);

            try {
            const userRes = await fetch(process.env.NEXT_PUBLIC_API_URL+'/api/user/info', { headers: { "Authorization": `Bearer ${jwtToken}`} });
            if (!userRes.ok) {
                router.push(login);
                return;
            }

            let userJson = await userRes.json();
            let email = userJson.email;

            userJson.password = null;
            userJson.passwordConfirm = null;
            delete userJson.id;
            delete userJson.email;

            setFormData(userJson);
            setEmail(email);

            } catch (error) {
                console.error('Error fetching user info: ', error);
            }
        }
        loadUserProfileForm();
      }, []);

    const handleChange = (e) => {
        const { name, value } = e.target;
        setFormData({...formData, [name]: value});
    };

    const handleSubmit = async (e) => {
        e.preventDefault();

        const jwtToken = localStorage.getItem('jwtToken');

        setPending(true)
        const res = await fetch(process.env.NEXT_PUBLIC_API_URL+"/api/user/info", {
            method: "PUT",
            headers: {
                "Content-Type": "application/json",
                "Authorization": `Bearer ${jwtToken}`
            },
            body: JSON.stringify(formData)
        });

        if (res.status === 403) {
            router.push(login)
            return;
        }

        if (!res.ok) {
            const json = await res.json();
            alert(JSON.stringify(json));
        } else {
            alert("Details updated!")
        }

        setPending(false);
    }

    return (
        <main>
            <UserNavbar />
            <h1 className="text-center text-5xl font-bold text-pink-600 mb-8">User Info</h1>
            {formData ?
            <div className="flex flex-wrap w-full">
                <form className="w-full" onSubmit={handleSubmit}>
                    <div className="grid grid-cols-12 gap-3 mb-10">
                        <div className="flex items-center justify-end col-start-1 col-span-4 md:col-start-4 md:col-span-1">
                            <label className="text-gray-500 font-bold mb-1 md:mb-0 whitespace-nowrap mr-2" htmlFor="name">Name: </label>
                        </div>
                        <div className="flex items-center col-start-5 col-span-6 md:col-span-4">
                            <input className="appearance-none border-2 border-gray-200 rounded w-full py-2 px-4 text-gray-700 leading-tight focus:outline-none focus:bg-white focus:border-gray-800"
                                type="text"
                                id="name"
                                name="name"
                                onChange={handleChange}
                                value={formData.name}
                            />
                        </div>
                    </div>
                    <div className="grid grid-cols-12 gap-3 mb-10">
                        <div className="flex items-center justify-end col-start-1 col-span-4 md:col-start-4 md:col-span-1">
                            <label className="text-gray-500 font-bold mb-1 md:mb-0 whitespace-nowrap mr-2" htmlFor="email">Email: </label>
                        </div>
                        <div className="flex items-center col-start-5 col-span-6 md:col-span-4">
                            <input disabled className="appearance-none border-2 border-gray-200 rounded w-full py-2 px-4 text-gray-700 leading-tight focus:outline-none focus:bg-white focus:border-gray-800"
                                type="text"
                                id="email"
                                name="email"
                                value={email}
                            />
                        </div>
                    </div>
                    <div className="grid grid-cols-12 gap-3 mb-10">
                        <div className="flex items-center justify-end col-start-1 col-span-4 md:col-start-4 md:col-span-1">
                            <label className="text-gray-500 font-bold mb-1 md:mb-0 whitespace-nowrap mr-2" htmlFor="mobile">Mobile: </label>
                        </div>
                        <div className="flex items-center col-start-5 col-span-6 md:col-span-4">
                            <input className="appearance-none border-2 border-gray-200 rounded w-full py-2 px-4 text-gray-700 leading-tight focus:outline-none focus:bg-white focus:border-gray-800"
                                type="text"
                                id="mobile"
                                name="mobile"
                                onChange={handleChange}
                                value={formData.mobile ? formData.mobile : ''}
                            />
                        </div>
                    </div>
                    <div className="grid grid-cols-12 gap-3 mb-10">
                        <div className="flex items-center justify-end col-start-1 col-span-4 md:col-start-4 md:col-span-1">
                            <label className="text-gray-500 font-bold mb-1 md:mb-0 whitespace-nowrap mr-2" htmlFor="deliveryAddress">Delivery Address: </label>
                        </div>
                        <div className="flex items-center col-start-5 col-span-6 md:col-span-4">
                            <input className="appearance-none border-2 border-gray-200 rounded w-full py-2 px-4 text-gray-700 leading-tight focus:outline-none focus:bg-white focus:border-gray-800"
                                type="text"
                                id="deliveryAddress"
                                name="deliveryAddress"
                                onChange={handleChange}
                                value={formData.deliveryAddress ? formData.deliveryAddress : ''}
                            />
                        </div>
                    </div>
                    <div className="grid grid-cols-12 gap-3 mb-10">
                        <div className="flex items-center justify-end col-start-1 col-span-4 md:col-start-4 md:col-span-1">
                            <label className="text-gray-500 font-bold mb-1 md:mb-0 whitespace-nowrap mr-2" htmlFor="deliveryAddress">Delivery Method: </label>
                        </div>
                        <div className="flex items-center col-start-5 col-span-6 md:col-span-4">
                            <select className="appearance-none border-2 border-gray-200 rounded w-full py-2 px-4 text-gray-700 leading-tight focus:outline-none focus:bg-white focus:border-gray-800"
                                type="text"
                                id="deliveryMethod"
                                name="deliveryMethod"
                                onChange={handleChange}
                                value={formData.deliveryMethod}
                            >
                                <option key={-1} value={null}>None</option>
                                {
                                    dropDownValues.deliveryMethod.map((m, i) => {
                                        return <option index={i} value={m}>{m}</option>
                                    })
                                }
                            </select>
                        </div>
                    </div>
                    <div className="grid grid-cols-12 gap-3 mb-10">
                        <div className="flex items-center justify-end col-start-1 col-span-4 md:col-start-4 md:col-span-1">
                            <label className="text-gray-500 font-bold mb-1 md:mb-0 whitespace-nowrap mr-2" htmlFor="deliveryTime">Delivery Time: </label>
                        </div>
                        <div className="flex items-center col-start-5 col-span-6 md:col-span-4">
                            <select className="appearance-none border-2 border-gray-200 rounded w-full py-2 px-4 text-gray-700 leading-tight focus:outline-none focus:bg-white focus:border-gray-800"
                                type="text"
                                id="deliveryTime"
                                name="deliveryTime"
                                onChange={handleChange}
                                value={formData.deliveryTime}

                            >
                            <option key={-1} value={null}>None</option>
                            {
                                dropDownValues.deliveryTimeSlot.map((m, i) => {
                                    return <option index={i} value={m}>{m}</option>
                                })
                            }
                            </select>
                        </div>
                    </div>
                    <div className="grid grid-cols-12 gap-3 mb-10">
                        <div className="flex items-center justify-end col-start-1 col-span-4 md:col-start-4 md:col-span-1">
                            <label className="text-gray-500 font-bold mb-1 md:mb-0 whitespace-nowrap mr-2" htmlFor="password">New Password: </label>
                        </div>
                        <div className="flex items-center col-start-5 col-span-6 md:col-span-4">
                            <input className="appearance-none border-2 border-gray-200 rounded w-full py-2 px-4 text-gray-700 leading-tight focus:outline-none focus:bg-white focus:border-gray-800"
                                type="text"
                                id="password"
                                name="password"
                                onChange={handleChange}
                            />
                        </div>
                    </div>
                    <div className="grid grid-cols-12 gap-3 mb-10">
                        <div className="flex items-center justify-end col-start-1 col-span-4 md:col-start-4 md:col-span-1">
                            <label className="text-gray-500 font-bold mb-1 md:mb-0 whitespace-nowrap mr-2" htmlFor="passwordConfirm">Confirm Password: </label>
                        </div>
                        <div className="flex items-center col-start-5 col-span-6 md:col-span-4">
                            <input className="appearance-none border-2 border-gray-200 rounded w-full py-2 px-4 text-gray-700 leading-tight focus:outline-none focus:bg-white focus:border-gray-800"
                                type="password"
                                id="passwordConfirm"
                                name="passwordConfirm"
                                onChange={handleChange}
                            />
                        </div>
                    </div>
                    <div className="grid grid-cols-12 gap-3 mb-8">
                        <div className="flex items-center justify-center col-start-5 col-span-6 md:col-span-4 md:col-start-5">
                            {!isPending && <button className="btn btn-primary w-full md:w-1/3">Save</button>}
                            {isPending &&
                            <button disabled className="btn btn-primary w-full md:w-1/3" disabled>
                                    <div
                                    className="inline-block h-6 w-6 animate-spin rounded-full border-4 border-solid border-current border-r-transparent align-[-0.125em] motion-reduce:animate-[spin_1.5s_linear_infinite]"
                                    role="status">
                                    </div>
                            </button>
                            }
                        </div>
                    </div>
                </form>
            </div>
            : <p className="text-center text-xl">Loading...</p>}
        </main>
    )
}