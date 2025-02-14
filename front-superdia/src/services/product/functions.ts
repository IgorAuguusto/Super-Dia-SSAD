import { ApiResponse, Product } from "../../lib/definitions";

export async function createProduct(product: Product): Promise<ApiResponse<Product>>{
  const response = await fetch(`${import.meta.env.VITE_API_URL}produtos/criar`, {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify({
      ...product
    })
  })

  return await response.json()
}

export async function updateProduct(product: Product): Promise<ApiResponse<Product>>{
  const response = await fetch(`${import.meta.env.VITE_API_URL}produtos/alterar`, {
    method: "PUT",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify({
      ...product
    })
  })

  return await response.json()
}

export async function deleteProduct(product: Product): Promise<ApiResponse<Product>>{
  const response = await fetch(`${import.meta.env.VITE_API_URL}produtos/deletar`, {
    method: "DELETE",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify({
      ...product
    })
  })

  return await response.json()
}

export async function deleteProductById(productId: string): Promise<ApiResponse<Product>>{
  const response = await fetch(`${import.meta.env.VITE_API_URL}produtos/deletar/${productId}`, {
    method: "DELETE",
    headers: {
      "Content-Type": "application/json",
    },
  })

  return await response.json()
}

export async function importProducts(url: string): Promise<ApiResponse<Product>>{
  const response = await fetch(`${import.meta.env.VITE_API_URL}produtos/importar-produtos?url=${url}`, {
    method: "GET",
    headers: {
      "Content-Type": "application/json",
    },
  })

  return await response.json()
}