'use client';
import { useState, useEffect } from 'react';
import { useRouter } from 'next/navigation';

export default function Profile()
{
    const [dropDownValues, setDropDownValues] = useState(null);
    const [formData, setFormData] = useState({
        name: '',
        email: '',
        mobile: '',
        deliveryAddress: '',
        deliveryMethod: null,
        deliveryTime: null,
    });
    const [formDataLoaded, setFormDataLoaded] = useState(false);
    const [isPending, setPending] = useState(false);
    const [cartItems, setCartItems] = useState(null);
    const router = useRouter();

    useEffect(() => {
        const storedCartItems = JSON.parse(localStorage.getItem('cartItems') || '[]');
        if (storedCartItems.length === 0) {
            router.push('/checkout/cart');
            return;
        }
        setCartItems(storedCartItems);

        const jwtToken = localStorage.getItem('jwtToken');

        async function loadUserProfileForm() {
            const dropDownValuesRes = await fetch('http://localhost:8080/api/drop-down/delivery');
            const dropDownValuesJson = await dropDownValuesRes.json();
            setDropDownValues(dropDownValuesJson);

            if (jwtToken) {
                const userRes = await fetch('http://localhost:8080/api/user/info', { headers: { "Authorization": `Bearer ${jwtToken}`} });
                if (!userRes.ok) {
                    router.push('/login');
                    return;
                }

                let userJson = await userRes.json();
                delete userJson.id;

                setFormData(userJson);
            }

            setFormDataLoaded(true);
        }
        loadUserProfileForm();
      }, []);

    const handleChange = (e) => {
        const { name, value } = e.target;
        setFormData({...formData, [name]: value});
    };

    const handleSubmit = async (e) => {
        e.preventDefault();

        setPending(true);

        const jwtToken = localStorage.getItem('jwtToken');
        const submission = {deliveryDetails: formData, deliveryCart: cartItems};
        let headers = {"Content-Type": "application/json"};

        if (jwtToken) {
            headers['Authorization'] = `Bearer ${jwtToken}`;
        }

        const res = await fetch("http://localhost:8080/api/delivery", {
            method: "POST",
            headers: headers,
            body: JSON.stringify(submission)
        });

        if (!res.ok) {
            if (res.status === 403) {
                router.push('/login');
                return;
            }
            const json = await res.json();
            alert(JSON.stringify(json));
        } else {
            localStorage.removeItem('cartItems');
            router.push('/');
        }

        setPending(false);
    }

    return (
        <main>
            <h1 className="text-center text-5xl font-bold text-pink-600 mb-16">Checkout</h1>
            {formDataLoaded ?
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
                            <input className="appearance-none border-2 border-gray-200 rounded w-full py-2 px-4 text-gray-700 leading-tight focus:outline-none focus:bg-white focus:border-gray-800"
                                type="text"
                                id="email"
                                name="email"
                                value={formData.email}
                                onChange={handleChange}
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
                                value={formData?.mobile || ''}
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
                                value={formData?.deliveryAddress || ''}
                            />
                        </div>
                    </div>
                    <div className="grid grid-cols-12 gap-3 mb-10">
                        <div className="flex items-center justify-end col-start-1 col-span-4 md:col-start-4 md:col-span-1">
                            <label className="text-gray-500 font-bold mb-1 md:mb-0 whitespace-nowrap mr-2" htmlFor="deliveryAddress">Delivery Method: </label>
                        </div>
                        <div className="flex items-center col-start-5 col-span-6 md:col-span-4">
                          <div className="select-wrapper flex items-center col-start-5 col-span-6 md:col-span-4">
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
                                          return <option key={i} value={m}>{m}</option>
                                      })
                                  }
                              </select>
                          </div>
                      </div>
                    </div>
                    <div className="grid grid-cols-12 gap-3 mb-10">
                        <div className="flex items-center justify-end col-start-1 col-span-4 md:col-start-4 md:col-span-1">
                            <label className="text-gray-500 font-bold mb-1 md:mb-0 whitespace-nowrap mr-2" htmlFor="deliveryTime">Delivery Time: </label>
                        </div>
                        <div className="flex items-center col-start-5 col-span-6 md:col-span-4">
                          <div className="select-wrapper">
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
                                          return <option key={i} value={m}>{m}</option>
                                      })
                                  }
                              </select>
                          </div>
                        </div>
                    </div>
                    <div className="grid grid-cols-12 gap-3 mb-8 flex items-center justify-center">
                        <div className="col-start-5 col-span-3 md:col-start-5 md:col-span-2">
                            <a className="btn btn-primary w-full" href="/checkout/cart">
                                Prev
                            </a>
                        </div>
                        <div className="col-start-8 col-span-3 md:col-start-7 md:col-span-2 ">
                        {!isPending && <button className="btn btn-primary w-full">Confirm</button>}
                        {isPending &&
                        <button disabled className="btn btn-primary w-full" disabled>
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