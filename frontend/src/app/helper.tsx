"use client"

export function fMoney(num: number): number {
    return (Math.round(num * 100) / 100).toFixed(2);
}

export function handleAddToCart(productId: number, supermarketId: number, productName: string, setCartMessage: any) {
    console.log({productId, supermarketId, productName, setCartMessage});
    const cartItems = JSON.parse(localStorage.getItem("cartItems") || "[]");

    // check if the product is already in the cart
    const existingCartItem = cartItems.find(
      (item: { productId: number; supermarketId: number }) =>
        item.productId === productId && item.supermarketId === supermarketId
    );

    if (existingCartItem) {
      // if the product is already in the cart, increase the quantity
      existingCartItem.quantity += 1;
    } else {
      // if product is not in cart, add it
      cartItems.push({
        productId: productId,
        supermarketId: supermarketId,
        quantity: 1,
      });
    }

    localStorage.setItem("cartItems", JSON.stringify(cartItems));

    setCartMessage(`Added ${productName} to cart!`);

    setTimeout(() => {
      setCartMessage("");
    }, 3000);
};