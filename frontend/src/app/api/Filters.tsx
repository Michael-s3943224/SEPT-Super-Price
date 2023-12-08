
export async function getFilters(){
  // Fetch data from the API
  const response = await fetch(`${process.env.NEXT_PUBLIC_API_URL}/api/items/filter-options`, { cache: "no-cache" });

  if (!response.ok) {
    throw new Error('Failed to fetch data'); // Handle errors
  }

  const { brand, supermarket, category } = await response.json();
    const filters = {
      brand: Array.isArray(brand) ? brand : [brand],
      supermarket: Array.isArray(supermarket) ? supermarket : [supermarket],
      category: Array.isArray(category) ? category : [category]
    };
    const data = { filters };
  return data;
}
