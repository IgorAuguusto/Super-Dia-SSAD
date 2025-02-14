import { TriangleAlert } from 'lucide-react'
import { Product } from '../../../lib/definitions'

interface WarningAlertProps {
  products: Product[]
}

export default function WarningAlert({products}: WarningAlertProps) {
  return (
    <div className="flex flex-col space-y-2 p-5 bg-yellow-200 text-yellow-600 shadow-md">
        <div className="flex flex-row text-yellow-500 space-x-1 items-center">
           <TriangleAlert/>         
          <h2 className="text-xl font-bold ">Produtos em baixo estoque</h2>
        </div>
        <ul>
        {products.map(product => (
          <li key={product.id!}><span className="text-2xl">.</span> {product.nome} - {product.quantidadeEstoque} itens restantes</li>
        ))}
        </ul>
      </div>
  )
}
