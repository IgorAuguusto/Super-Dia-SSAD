import { Import, Plus } from 'lucide-react'
import { Button } from '../../ui/button'
import ProductFormDialog from './Products/ProductFormDialog'
import ProductImportDialog from './Products/ProductImportDialog'

export default function Header() {
  return (
    <header className="w-full flex flex-row items-center justify-between">
        <h1 className="text-3xl font-bold">Produtos</h1>
        <div className="flex flex-row space-x-2 items-center">
          <ProductImportDialog>
            <Button variant={"default"} className="bg-cyan-700 hover:bg-cyan-800"><Import /> Importar produto</Button>
          </ProductImportDialog>
          <ProductFormDialog >
            <Button variant={"default"} className="bg-red-700 hover:bg-red-800"><Plus /> Adicionar produto</Button>
          </ProductFormDialog>
        </div>
      </header>
  )
}
