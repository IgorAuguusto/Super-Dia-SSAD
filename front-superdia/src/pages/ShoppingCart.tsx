import { useNavigate } from 'react-router'
import { Button } from '../components/ui/button'
import { useCartProducts } from '../hooks/use-cart-products'

export default function ShoppingCart() {
  const {productsOnCart, setProductsOnCart } = useCartProducts()
  const navigate = useNavigate()

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
      <div className='bg-white w-full h-fit flex flex-col p-4'>
        {productsOnCart.map(product => (
          <div key={product.id} className='w-full p-2'>
            <div className='flex flex-row items-center space-x-2'>
              <div className='size-20'>
                <img src={product.imageUrl ? product.imageUrl : "/t-shirt.svg"} alt={`Imagem do produto ${product.nome}`} />
              </div>
              <div className='flex flex-col space-y-1'>
                <span className='font-bold'>{product.nome}</span>
                <span className='text-sm'>{product.descricao}</span>
                <span>Preço: <span className='font-bold'>{product.preco.toLocaleString("pt-BR", {style: "currency", currency: "BRL"})}</span></span>
                <Button onClick={() => {
                  setProductsOnCart(productsOnCart.filter(productOnCart => productOnCart.id !== product.id))
                }} variant={"default"} className='bg-red-500 hover:bg-red-400 w-36'>Remover produto</Button>
              </div>
            </div>
          </div>
        ))}
      </div>
      <div className='bg-white text-nowrap w-full sm:w-2/3 h-fit flex flex-col p-5 space-y-5'>
        <div className='space-y-2'>
          <h2 className='text-xl font-bold'>Resumo</h2>
          <div className='w-full flex flex-row items-center justify-between'>
            <span>Preço dos produtos</span>
            <span className='font-bold'>{productsOnCart.reduce((n, {preco}) => n + preco, 0).toLocaleString("pt-BR", {style: "currency", currency: "BRL"})}</span>
          </div>
        </div>
        <Button onClick={() => {
          setProductsOnCart([])
        }} variant={"default"} className='bg-red-500 hover:bg-red-400'>Remover todos produtos</Button>
        <Button onClick={() => {
          navigate("/home/cliente/finalizar-compra")
        }} variant={"default"} className='bg-green-500 hover:bg-green-400'>Finalizar compra</Button>
      </div>
    </div>
  )
}
