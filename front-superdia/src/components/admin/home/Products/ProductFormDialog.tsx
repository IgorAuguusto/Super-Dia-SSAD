import { ShoppingCart } from "lucide-react";
import { ReactElement, useState } from "react";
import { Product } from "../../../../lib/definitions";
import { Dialog, DialogContent, DialogDescription, DialogHeader, DialogTitle, DialogTrigger } from "../../../ui/dialog";
import { Separator } from "../../../ui/separator";
import ProductForm from "./ProductForm";

interface ProductFormDialogProps {
  product?: Product
  children: ReactElement
}

export default function ProductFormDialog({product, children}: ProductFormDialogProps) {
  const [open, setOpen] = useState<boolean>(false)

  return (
    <Dialog open={open} onOpenChange={setOpen}>
      <DialogTrigger asChild>
        {children}
      </DialogTrigger>
      <DialogContent className="max-w-md max-h-[650px] overflow-y-auto bg-white">
        <DialogHeader className="space-y-2">
            <div className="w-100 flex flex-col items-center justify-center space-y-3">
              <div className="flex items-center justify-center rounded-full bg-red-400 p-4 text-black">
                <ShoppingCart size={35}/>
              </div>
              <div className="flex flex-col items-center">
                  <DialogTitle>{product ? "Editando Produto" : "Criando Produto"}</DialogTitle>
                  <DialogDescription className="sr-only">{product ? "Editando Produto" : "Criando Produto"}</DialogDescription>
              </div>
            </div>
            <Separator orientation="horizontal" className="h-[0.5px] bg-red-400" />
            <ProductForm product={product} setOpen={setOpen}/>
          </DialogHeader>
      </DialogContent>
    </Dialog>
  )
}
