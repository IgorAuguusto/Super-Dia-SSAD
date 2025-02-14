import { z } from "zod";

export const NO_CARD_NUMBER = 1001
export const NO_EXP_DATE = 1002
export const INVALID_CARD_TYPE = 1003
export const INVALID_CARD_LENGTH = 1004
export const BAD_MOD10 = 1005
export const BAD_EXP_DATE = 1006


export const productSchema = z.object({
  id: z.number().optional(),
  nome: z.string().min(3, {
    message: "Mínimo de 3 caracteres"
  }),
  descricao: z.string().max(200, {
    message: "Máximo de 200 caracteres"
  }),
  preco: z.coerce.number().refine((value) => value > 0, {
    message: "Forneça o preço do produto"
  }),
  estoqueMinimo: z.coerce.number(),
  quantidadeEstoque: z.coerce.number(),
  imageUrl: z.string().url({message: "Forneça uma URL válida"}).or(z.literal("")),
  vendidoPor: z.string().min(3, {
    message: "Forneça a loja que vende o produto"
  })
})

export type Product = z.infer<typeof productSchema>

export const loginSchema = z.object({
  login: z.string({message: "Forneça o login"}),
  senha: z.string({message: "Forneça a senha"}).min(3, {
    message: "Senha deve ter no mínimo 3 caracteres"
  })
})

export type Login = z.infer<typeof loginSchema>

export const personSchema = z.object({
  id: z.number().optional(),
  cpf: z.string(),
  dataNascimento: z.date(),
  email: z.string().email(),
  endereco: z.string(),
  nome: z.string(),
  telefone: z.string()
})

export enum Roles {
  ADMINISTRADOR = "ADMINISTRADOR",
  CAIXA = "CAIXA",
  CLIENTE = "CLIENTE"
}

export const userSchema = z.object({
  id: z.number().optional(),
  perfil: z.nativeEnum(Roles),
  pessoa: personSchema,
})

export type User = z.infer<typeof userSchema>

export type ApiResponse<T> = {
  status: number
  data: T
  message: string
}

export type CreditCardResponse = {
  valido: boolean
  mensagem: string
  codigoRetorno: number
}

export const productImportSchema = z.object({
  url: z.string().url({message: "Forneça uma URL válida"})
})

export type ProductImport = z.infer<typeof productImportSchema>

export const creditCardSchema = z.object({
  cardNumber: z.string().min(16, {
    message: "Cartão deve ter 16 números."
  }),
  expDate: z.string().min(4, {
    message: "Forneça a data de expiração do cartão."
  })
})

export type CreditCard = z.infer<typeof creditCardSchema>

export type Purchase = {
  produtos: Product[],
  usuario: User,
  cartao: string
}