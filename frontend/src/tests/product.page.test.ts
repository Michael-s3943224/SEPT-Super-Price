import { describe, expect, test } from "vitest";
import { getProductbyID } from "../app/api/getProductbyID";
import { getMarketbyProd } from "../app/api/getMarketbyProd";
import { getProducts } from "../app/api/getProducts";
import { render, screen } from "@testing-library/react";

describe('Product details page tests', async () => {

  test('getProductbyID works', async () => {
    const result = await getProductbyID(1)
    expect(result.name).toBe("Coca - Cola Zero Sugar Soft Drink Bottle 1.25l")
    
  })

  test('getMarketbyProd works', async () => {
    const result = await getMarketbyProd(1)
    expect(result.supermarkets).toHaveLength(3)
  })

  test('getProductbyID with invalid id', async () => {
    const result = await getProductbyID(-1)
    expect(result).toBe(null)
    
  })



  
})