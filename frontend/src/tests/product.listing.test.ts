import { describe, expect, test } from "vitest";
import { getProductbyID } from "../app/api/getProductbyID";
import {getProducts} from "../app/api/getFilteredProducts";
import { render, screen } from "@testing-library/react";

describe('Products listing page tests', async () => {
  
 
  test('getProducts works', async () => {
    const result = await getProducts('http://localhost:8080/api/products')
    expect(result).toHaveLength(51)
  })

  
})