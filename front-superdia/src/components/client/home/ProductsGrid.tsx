import { Icon } from "@iconify-icon/react/dist/iconify.mjs"
import { useCookies } from "react-cookie"
import useSWR from "swr"
import { useCartProducts } from "../../../hooks/use-cart-products"
import { toast } from "../../../hooks/use-toast"
import { ApiResponse, Product } from "../../../lib/definitions"
import { cn, convertStringToRole, fetcher } from "../../../lib/utils"
import { Card, CardContent, CardDescription, CardHeader, CardTitle } from "../../ui/card"
import { Tooltip, TooltipContent, TooltipTrigger } from "../../ui/tooltip"

export default function ProductsGrid() {
  const [cookies] = useCookies()
  const {productsOnCart, setProductsOnCart} = useCartProducts()
  console.log(productsOnCart)
  const userId = cookies.session?.id
  const userRole = convertStringToRole(cookies.session?.perfil)

  const {data, error, isLoading} = useSWR([`${import.meta.env.VITE_API_URL}produtos/disponiveis`, userId, userRole], ([url, userId, userRole]) => fetcher<ApiResponse<Product[]>>(url, userId, userRole))

  const handleAddProductToCart = (productToAdd: Product) => {
    setProductsOnCart([...productsOnCart, productToAdd])
    toast({
      variant: "default",
      description: <p>Produto <span className="font-bold">{productToAdd.nome}</span> adicionado com sucesso ao carrinho.</p>,
      className: "bg-green-200 text-black ",
      duration: 2000,
    })
  }

  const handleRemoveProductFromCart = (productToRemove: Product) => {
    setProductsOnCart(productsOnCart.filter(product => product.id !== productToRemove.id))
    toast({
      variant: "default",
      description: <p>Produto <span className="font-bold">{productToRemove.nome}</span> removido com sucesso do carrinho.</p>,
      className: "bg-green-200 text-black ",
      duration: 2000,
    })
  }

  const isProductOnCart = (productToCheck: Product) => {
    return productsOnCart.filter(product => product === productToCheck).length > 0
  }

  if(error){
    return <span>Houve um erro ao obter os produtos</span>
  }

  if(isLoading){
    <div className="mt-5 flex h-full w-full items-center justify-center px-[5%]">
				<Icon
					icon="eos-icons:loading"
					className="text-5xl text-secondaryGray-100 dark:text-white"
				/>
			</div>
  }

  if(data?.data){
    return (
    <div className="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3
    lg:grid-cols-4 gap-5">
      {data.data.map(product => (
        <Card key={product.id} className="shadow-lg hover:shadow-xl transition-shadow">
          <CardHeader>
            <div className="relative group">
              <img src={product.imageUrl ? product.imageUrl : "/t-shirt.svg"} className="w-full space-y-0 items-end" alt={`Imagem do produto ${product.nome}`} />
              <div className={cn("absolute group-hover:visible invisible top-0 right-0  cursor-pointer transition-colors rounded-full",
                isProductOnCart(product) ? "bg-red-300 hover:bg-red-400" : "bg-green-300 hover:bg-green-400"
              )}>
                {isProductOnCart(product) ?
                <Tooltip>
                  <TooltipTrigger asChild>
                    <div onClick={() => handleRemoveProductFromCart(product)} className="flex p-2 items-center">
                      <Icon icon="carbon:shopping-cart-minus" width="24" height="24" />
                    </div>
                  </TooltipTrigger>
                  <TooltipContent>
                    <p>Remover do carrinho</p>
                  </TooltipContent>
                </Tooltip> 
                 :
                 <Tooltip>
                  <TooltipTrigger asChild>
                    <div onClick={() => handleAddProductToCart(product)} className="flex p-2 items-center">
                      <Icon icon="carbon:shopping-cart-plus" width="24" height="24" />
                    </div>
                  </TooltipTrigger>
                  <TooltipContent>
                    <p>Adicionar ao carrinho</p>
                  </TooltipContent>
                </Tooltip>
                }
                
              </div>
            </div>
            <CardTitle>{product.nome}</CardTitle>
            <CardDescription>{product.descricao}</CardDescription>
          </CardHeader>
          <CardContent>
            <span className="font-bold text-sm w-fit">Vendido por</span>
            <div className="flex flex-row items-center justify-between">
                <span className="bg-red-200 rounded-md text-center p-1">{product.vendidoPor}</span>
                <span className="bg-green-200 rounded-md text-center p-1">{product.preco.toLocaleString("pt-BR", {style: "currency", currency: "BRL"})}</span>
            </div>
          </CardContent>
        </Card>
      ))}
    </div>
  )
  }
  
}
