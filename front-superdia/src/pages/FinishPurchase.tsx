import { zodResolver } from "@hookform/resolvers/zod"
import { useCookies } from "react-cookie"
import { useForm } from "react-hook-form"
import { useNavigate } from "react-router"
import { mutate } from "swr"
import { Button } from "../components/ui/button"
import { Form, FormControl, FormDescription, FormField, FormItem, FormLabel, FormMessage } from "../components/ui/form"
import { Input } from "../components/ui/input"
import { useCartProducts } from "../hooks/use-cart-products"
import { toast } from "../hooks/use-toast"
import { BAD_MOD10, CreditCard, creditCardSchema, INVALID_CARD_LENGTH, INVALID_CARD_TYPE, NO_CARD_NUMBER, NO_EXP_DATE } from "../lib/definitions"
import { validateCreditCard } from "../services/creditCard"
import { createPurchase } from "../services/purchase"
import { getUserById } from "../services/user"

export default function FinishPurchase() {
  const [cookies] = useCookies()
  const {productsOnCart, setProductsOnCart} = useCartProducts()
  const navigate = useNavigate()
  const userId = cookies.session.id

  const form = useForm<CreditCard>({
      resolver: zodResolver(creditCardSchema),
      defaultValues: {
        cardNumber: "",
        expDate: ""
      },
      mode: "onBlur"
  })

  function showDescritiveError(errorCode: number){
    if(errorCode === NO_CARD_NUMBER){
      form.setError("cardNumber", {
        type: "custom",
        message: "Nenhum número de cartão fornecido",
      })
    } else if(errorCode === NO_EXP_DATE){
      form.setError("expDate", {
        type: "custom",
        message: "Nenhuma data de expiração fornecida",
      })
    } else if(errorCode === INVALID_CARD_TYPE){
      form.setError("cardNumber", {
        type: "custom",
        message: "Tipo de cartão inválido",
      })
    } else if(errorCode === INVALID_CARD_LENGTH){
      form.setError("cardNumber", {
        type: "custom",
        message: "Tamanho do cartão inválido",
      })
    } else if(errorCode === BAD_MOD10){
      form.setError("cardNumber", {
        type: "custom",
        message: "Checagem base de 10 inválida",
      })
    } else{
      form.setError("expDate", {
        type: "custom",
        message: "Data de expiração inválida",
      })
    }
  }

  async function onSubmit(formData:CreditCard) {
    try {
        const response = await validateCreditCard(formData)

        if(response.valido){
          console.log(response.mensagem)
          try {
            const userResponse = await getUserById(userId)
            if(userResponse.status === 200){
              try {
                const createSell = await createPurchase({
                cartao: formData.cardNumber,
                produtos: productsOnCart,
                usuario: userResponse.data
              })
              if(createSell.status === 201){
                mutate((key: string[]) => Array.isArray(key) && key[0].startsWith(`${import.meta.env.VITE_API_URL}produtos`))
                navigate("/home/cliente")
                toast({
                  variant: "default",
                  description: "Compra efetuada com sucesso. Obrigado por comprar conosco.",
                  className: "bg-green-200 text-black ",
                  duration: 3000,
                })
                setProductsOnCart([])
              } else {
                  toast({
                    variant: "destructive",
                    description: "Houve um erro ao criar a venda.",
                    duration: 3000,
                  })
                }
              } catch (error) {
                console.error(error)
                toast({
                  variant: "destructive",
                  description: "Houve um erro ao criar a venda.",
                  duration: 3000,
                })
              }
              
            } else {
              toast({
                variant: "destructive",
                description: "Houve um erro ao obter o usuário.",
                duration: 3000,
              })
            }
          } catch (error) {
            console.error(error)
            toast({
              variant: "destructive",
              description: "Houve um erro ao obter o usuário.",
              duration: 3000,
            })
          }
        } else {
          showDescritiveError(response.codigoRetorno)
        }
    } catch (error) {
      console.error(error)
      toast({
				variant: "destructive",
				description: "Houve um erro ao validar o cartão.",
				duration: 3000,
			})
    }
  }

  if(productsOnCart.length === 0){
     return (
      <div className='flex flex-col justify-center items-center w-full px-[5%] py-5'>
        <div className='flex flex-col items-center space-y-2'>
          <span className='text-2xl'>Ops, parece que você não tem nenhum produto no carrinho</span>
          <Button onClick={() => {
            navigate("/home")
          }} variant={"default"} className='bg-red-500 hover:bg-red-400 transition-colors w-40'>Continuar comprando</Button>
        </div>
      </div>
    )
  }

  return (
    <div className='flex sm:flex-row sm:space-y-0 sm:space-x-5 flex-col mt-5 w-full sm:px-[5%] space-y-5 py-2 px-5'>
      <div className='bg-white w-full h-fit flex flex-col p-4 space-y-5'>
        <h2 className="text-xl font-bold">Forma de pagamento</h2>
        <div className="flex flex-row items-center w-full space-x-5">
          <div className="w-1/3">
            <img src="/credit-card.svg" alt="Imagem cartão de crédito" />
          </div>
          <div className="flex flex-col w-full">
            <Form {...form}>
              <form onSubmit={form.handleSubmit(onSubmit)} className="space-y-3">
                <FormField
                  control={form.control}
                  name="cardNumber"
                  render={({ field }) => (
                    <FormItem>
                      <FormLabel>Número do cartão</FormLabel>
                      <FormControl>
                        <Input placeholder="XXXX XXXX XXXX XXXX" maxLength={16}{...field} />
                      </FormControl>
                      <FormMessage />
                    </FormItem>
                  )}
                />
                <FormField
                  control={form.control}
                  name="expDate"
                  render={({ field }) => (
                    <FormItem>
                      <FormLabel>Data de expiração</FormLabel>
                      <FormControl>
                        <Input placeholder="XXXX" maxLength={4}{...field} />
                      </FormControl>
                      <FormMessage />
                      <FormDescription>
                        Obs: Forneça no formato mmYY.
                        <p>Ex: 0827, 1129</p>
                      </FormDescription>
                    </FormItem>
                  )}
                />
                <Button type="submit" variant={"default"} className="bg-green-500 hover:bg-green-400">Efetuar compra</Button>
              </form>
            </Form>
          </div>
        </div>
      </div>

      <div className='bg-white text-nowrap w-full sm:w-2/3 h-fit flex flex-col p-5 space-y-5'>
        <div className='space-y-2'>
          <h2 className='text-xl font-bold'>Resumo</h2>
          <div className='w-full flex flex-row items-center justify-between'>
            <span>Preço dos produtos</span>
            <span className='font-bold'>{productsOnCart.reduce((n, {preco}) => n + preco, 0).toLocaleString("pt-BR", {style: "currency", currency: "BRL"})}</span>
          </div>
        </div>
      </div>
    </div>
  )
}
