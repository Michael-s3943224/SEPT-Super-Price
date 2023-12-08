import { getFilters } from '@/app/api/Filters';
import { FilteredProducts } from '../api/getFilteredProducts';
import ClientPage from './client_page';

export default async function page({
  params,
  searchParams,
}: {
  params: { slug: string }
  searchParams: { [key: string]: string | string[] | undefined }
}) {
	var url = process.env.NEXT_PUBLIC_API_URL+'/api/items/browse?';

  const search_query = searchParams.search;
  const category = searchParams.category;
  const supermarket = searchParams.supermarket;
  const brand = searchParams.brand;
  const above_price = searchParams.above_price;
  const below_price = searchParams.below_price;

  if (search_query !== null && search_query !== '' && search_query !== undefined)  {
    url += '&term=' + search_query;
  }
  else {
    url += '&term=';
  }
  if (category !== null && category !== '' && category !== undefined) {
    url += '&category=' + category;
  }
  if (supermarket !== null && supermarket !== '' && supermarket !== undefined) {
    url += '&supermarket=' + supermarket;
  }
  if (brand !== null && brand !== '' && brand !== undefined) {
    url += '&brand=' + brand;
  }
  if (above_price !== null && above_price !== '' && above_price !== undefined) {
    url += '&priceAbove=' + above_price;
  }
  if (below_price !== null && below_price !== '' && below_price !== undefined) {
    url += '&priceBelow=' + below_price;
  }

  const data = await getFilters();
  
  return (
		<main>
			<ClientPage filters = {data}/>
			<FilteredProducts url = {url}/>
		</main>
  )
}
