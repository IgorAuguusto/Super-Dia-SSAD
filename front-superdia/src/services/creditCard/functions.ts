import { CreditCard, CreditCardResponse } from "../../lib/definitions"

export async function validateCreditCard(creditCard: CreditCard): Promise<CreditCardResponse>{
  const response = await fetch(`${import.meta.env.VITE_API_URL}valida-cartao`, {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify({
      numeroCartao: creditCard.cardNumber,
      dataExpiracao: creditCard.expDate
    })
  })

  return await response.json()
}