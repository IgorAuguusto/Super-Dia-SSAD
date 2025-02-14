import { create } from 'zustand'
import { Product } from '../lib/definitions'

type CartProducts = {
  productsOnCart: Product[]
  productsCount: number
  setProductsOnCart: (products: Product[]) => void
}

export const useCartProducts = create<CartProducts>()((set) => ({
  productsOnCart: [],
  productsCount: 0,
  setProductsOnCart: (products: Product[]) => set(() => ({productsOnCart: products, productsCount: products.length}))
}))