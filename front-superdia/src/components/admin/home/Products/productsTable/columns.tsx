import { ColumnDef } from "@tanstack/react-table"
import { Product } from "../../../../../lib/definitions"
import { cn } from "../../../../../lib/utils"
import { Button } from "../../../../ui/button"
import DialogDeleteItem from "../../../../ui/dialog-delete-item"
import ProductFormDialog from "../ProductFormDialog"

export const columns: ColumnDef<Product>[] = [
  {
    accessorKey: "nome",
    header: "Produto",
    cell: ({row}) => {
        return(
          <div className="flex flex-row space-x-2">
            <div className="size-10">
              <img src={row.original.imageUrl ? row.original.imageUrl : "/t-shirt.svg"} alt="Imagem do produto" />
            </div>
            <div className="flex flex-col space-y-1">
              <span className="font-bold">{row.original.nome}</span>
              <span className="font-light">{row.original.descricao}</span>
            </div>
          </div>
        )
    },
  },
  {
    accessorKey: "vendidoPor",
    header: "Vendido por"
  },
  {
    accessorKey: "preco",
    header: "Preço",
    cell: ({row}) => {
      return (
        <span>{row.original.preco?.toLocaleString("pt-BR", {style: "currency", currency: "BRL"})}</span>
      )
    },
    enableSorting: true,
    sortingFn: (rowA, rowB, ) => {
      return rowA.original.preco! < rowB.original.preco! ? - 1 : rowA.original.preco! === rowB.original.preco ? 0 : 1
    }
  },
  {
    accessorKey: "quantidadeEstoque",
    header: "Estoque",
    cell: ({row}) => {
      return(
        <span className={cn(
          row.original.quantidadeEstoque! > row.original.estoqueMinimo! ? "bg-green-300 text-green-600" : "bg-yellow-300 text-yellow-600",
          "p-1 text-center rounded-md"
        )} >{row.original.quantidadeEstoque} unidades</span>
      )
    },
  },
  {
    accessorKey: "estoqueMinimo",
    header: "Estoque mínimo",
    cell: ({row}) => {
      return (
        <span className="bg-yellow-300 text-yellow-600 p-1 text-center rounded-md">{row.original.estoqueMinimo} unidades</span>
      )
    },
  },
  {
    header: "Ações",
    cell: ({row, table}) => {
      return(
        <div className="flex flex-row space-x-1 items-center">
          <ProductFormDialog product={row.original}>
            <Button variant={"default"} className="bg-yellow-400 text-black hover:bg-yellow-300">Editar</Button>
          </ProductFormDialog>
          <DialogDeleteItem item="Produto" itemName={row.original.nome} itemId={row.original.id!} deleteFn={table.options.meta!.handleDelete}>
            <Button variant={"default"} className=" bg-red-400 hover:bg-red-300 text-black">Remover</Button>
          </DialogDeleteItem>
        </div>
      )
    }
  }
]