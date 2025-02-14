import { ReactElement, useState } from "react"
import { Button } from "./button"
import { Dialog, DialogClose, DialogContent, DialogDescription, DialogHeader, DialogTitle, DialogTrigger } from "./dialog"
import { Separator } from "./separator"

interface DialogDeleteItemProps {
  item: string
  itemName: string
  itemId: number
  deleteFn: (id: string) => void
  children: ReactElement
}

export default function DialogDeleteItem({children, item, itemName, deleteFn, itemId}: DialogDeleteItemProps) {
  const [open, setOpen] = useState<boolean>(false)

  return (
    <Dialog open={open} onOpenChange={setOpen}>
      <DialogTrigger asChild>
        {children}
      </DialogTrigger>
      <DialogContent className="max-w-md max-h-[650px] overflow-y-auto bg-white">
        <DialogHeader>
          <DialogTitle className="text-xl">Excluindo {item}</DialogTitle>
          <DialogDescription className="sr-only">Deletando item</DialogDescription>
        </DialogHeader>
        <Separator orientation="horizontal" className="h-[0.5px] bg-red-400" />
        <p>Tem certeza que deseja excluir o item <span className="font-bold">{itemName}</span>?</p>
        <div className="flex flex-row space-x-2 items-center">
          <DialogClose asChild>
            <Button onClick={() => {
              deleteFn(itemId.toString())
            }} variant={"destructive"} className="bg-red-400 hover:bg-red-300" >Excluir</Button>
          </DialogClose>
          <DialogClose asChild>
            <Button variant={"default"} className="bg-gray-400 text-white hover:bg-gray-300">Cancelar</Button>
          </DialogClose>
        </div>
      </DialogContent>
    </Dialog>
  )
}
