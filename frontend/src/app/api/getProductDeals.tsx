export function ProductDeals() {
  console.log(process.env.NEXT_PUBLIC_API_URL);
    return fetch(process.env.NEXT_PUBLIC_API_URL+'/api/items/deals')
      .then(response => {
        if (!response.ok) {
          throw new Error('Network response was not ok');
        }
        return response.json();
      });
  }