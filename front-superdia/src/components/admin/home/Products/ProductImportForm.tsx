import { zodResolver } from "@hookform/resolvers/zod"
import { Dispatch, SetStateAction } from "react"
import { useForm } from "react-hook-form"
import { mutate } from "swr"
import { toast } from "../../../../hooks/use-toast"
import { ProductImport, productImportSchema } from "../../../../lib/definitions"
import { importProducts } from "../../../../services/product"
import { Button } from "../../../ui/button"
import { Form, FormControl, FormDescription, FormField, FormItem, FormLabel, FormMessage } from "../../../ui/form"
import { Input } from "../../../ui/input"



interface ProductImportFormProps {
  setOpen: Dispatch<SetStateAction<boolean>>
}

export default function ProductImportForm({setOpen}: ProductImportFormProps) {
  
  const form = useForm<ProductImport>({
    resolver: zodResolver(productImportSchema),
    defaultValues: {
      url: ""
    },
    mode: "onSubmit"
  })

  async function onSubmit(formData:ProductImport) {
       try {
        const response = await importProducts(formData.url)
        console.log(response)
        if(response.status === 200){
          mutate((key: string[]) => Array.isArray(key) && key[0].startsWith(`${import.meta.env.VITE_API_URL}produtos`))
          setOpen(false)
          toast({
            variant: "default",
            description: "Produtos importados com sucesso.",
            className: "bg-green-200 text-black ",
            duration: 3000,
          })
        } else {
          toast({
            variant: "destructive",
            description: "Erro ao importar produtos.",
            duration: 3000,
          })
        }
          
      } catch (error) {
        toast({
          variant: "destructive",
          description: "Erro ao importar produtos.",
          duration: 3000,
        })
        console.error(error)
      }
  }

  return (
    <Form {...form}>
      <form onSubmit={form.handleSubmit(onSubmit)}>
        <FormField
          control={form.control}
          name="url"
          render={({ field }) => (
            <FormItem>
              <FormLabel>URL</FormLabel>
              <FormControl>
                <Input placeholder="https://importar.produto..." {...field} />
              </FormControl>
              <FormMessage />
              <FormDescription>
                Forneça uma URL contendo um array de produtos para serem importados.
              </FormDescription>
            </FormItem>
          )}
        />
        <div className="mt-2">
          <Button type="submit" variant={"default"} className="bg-sky-400 text-white hover:bg-sky-300" >
            Importar
          </Button>
        </div>
        
      </form>
    </Form>
  )
}
