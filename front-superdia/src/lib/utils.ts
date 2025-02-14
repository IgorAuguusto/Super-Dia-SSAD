import { clsx, type ClassValue } from "clsx"
import { twMerge } from "tailwind-merge"
import { Roles } from "./definitions"

export function cn(...inputs: ClassValue[]) {
  return twMerge(clsx(inputs))
}

export async function fetcher<T>(url: string, userId: string, userRole: Roles ): Promise<T> {
	const requestOptions = {
		headers: { 
      "Content-Type": "application/json", 
      "user-id": `${userId}`,
      "user-role": `${userRole}` 
    },
	}
	const res = await fetch(url, requestOptions)
	return await res.json()
}

export function isValidRole(role: string): role is Roles {
  return Object.values(Roles).includes(role as Roles);
}

export function convertStringToRole(value: string): Roles {
  if (isValidRole(value)) {
    return value as Roles; 
  } else {
    throw new Error(`Valor inválido para Roles: ${value}`);
  }
}