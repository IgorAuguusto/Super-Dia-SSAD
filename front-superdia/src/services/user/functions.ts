import { ApiResponse, User } from "../../lib/definitions"

export async function getUserById(id: number): Promise<ApiResponse<User>>{
  const response = await fetch(`${import.meta.env.VITE_API_URL}usuarios/${id}`, {
    method: "GET",
    headers: {
      "Content-Type": "application/json",
    },
  })

  return await response.json()
}