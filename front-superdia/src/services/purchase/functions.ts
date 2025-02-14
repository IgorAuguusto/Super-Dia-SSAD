import { ApiResponse, Purchase } from "../../lib/definitions"

export async function createPurchase(purchase: Purchase): Promise<ApiResponse<Purchase>>{
  const response = await fetch(`${import.meta.env.VITE_API_URL}vendas/criar`, {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify({
      ...purchase
    })
  })

  return await response.json()
}