import { ApiResponse, Login, User } from "../../lib/definitions";

export async function login(login: Login): Promise<ApiResponse<User>>{
  const response = await fetch(`${import.meta.env.VITE_API_URL}usuarios/login`, {
    method: "POST",
    headers: {
      "Content-Type": "application/json"
    },
    body: JSON.stringify({
      ...login
    })
  })

  return await response.json()
}