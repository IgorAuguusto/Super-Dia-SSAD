import { zodResolver } from "@hookform/resolvers/zod"
import { Dispatch, SetStateAction } from "react"
import { useForm } from "react-hook-form"
import { mutate } from "swr"
import { toast } from "../../../../hooks/use-toast"
import { Product, productSchema } from "../../../../lib/definitions"
import { createProduct, updateProduct } from "../../../../services/product"
import { Button } from "../../../ui/button"
import { Form, FormControl, FormDescription, FormField, FormItem, FormLabel, FormMessage } from "../../../ui/form"
import { Input } from "../../../ui/input"
import { Textarea } from "../../../ui/textarea"

interface ProductFormProps {
  product?: Product
  setOpen: Dispatch<SetStateAction<boolean>>
}

const initializeDefaultValues = (): Product => {
    return {
      nome: "",
      descricao: "",
      estoqueMinimo: 0,
      preco: 0,
      quantidadeEstoque: 0,
      imageUrl: "",
      vendidoPor: "",
    }
  }

export default function ProductForm({product, setOpen}: ProductFormProps) {

  const form = useForm<Product>({
    resolver: zodResolver(productSchema),
    defaultValues: initializeDefaultValues(),
    values: product,
    mode: "onSubmit"
  })
  
  async function onSubmit(formData: Product){
    if(product){
      try {
        console.log(formData)
        const response = await updateProduct(formData)
        console.log(response)
        if(response.status === 200){
          mutate((key: string[]) => Array.isArray(key) && key[0].startsWith(`${import.meta.env.VITE_API_URL}produtos`))
          setOpen(false)
          toast({
            variant: "default",
            description: "Produto atualizado com sucesso.",
            className: "bg-green-200 text-black ",
            duration: 3000,
          })
        } else {
          toast({
            variant: "destructive",
            description: "Erro ao atualizar produto.",
            duration: 3000,
          })
        }
          
      } catch (error) {
        toast({
          variant: "destructive",
          description: "Erro ao atualizar produto.",
          duration: 3000,
        })
        console.error(error)
      }
    } else {
      try {
        const response = await createProduct(formData)
        console.log(response)
        if(response.status === 201){
          mutate((key: string[]) => Array.isArray(key) && key[0].startsWith(`${import.meta.env.VITE_API_URL}produtos`))
          setOpen(false)
          toast({
            variant: "default",
            description: "Produto criado com sucesso.",
            className: "bg-green-200 text-black ",
            duration: 3000,
          })
        } else {
          toast({
            variant: "destructive",
            description: "Erro ao criar produto.",
            duration: 3000,
          })
        }
          
      } catch (error) {
        toast({
          variant: "destructive",
          description: "Erro ao criar produto.",
          duration: 3000,
        })
        console.error(error)
      }
    }
  }

  return (
    <Form {...form}>
      <form onSubmit={form.handleSubmit(onSubmit)}>
        <div className="grid grid-cols-1 gap-4 mt-2 sm:grid-cols-2">
          <FormField
              control={form.control}
              name="nome"
              render={({ field }) => (
                <FormItem>
                  <FormLabel>Nome</FormLabel>
                  <FormControl>
                    <Input placeholder="Ex: Blusa, camiseta, tênis..." {...field} />
                  </FormControl>
                  <FormDescription>
                  </FormDescription>
                  <FormMessage />
                </FormItem>
              )}
            />
            <FormField
              control={form.control}
              name="vendidoPor"
              render={({ field }) => (
                <FormItem>
                  <FormLabel>Vendido por</FormLabel>
                  <FormControl>
                    <Input placeholder="Ex: Redbull, Adidas..." {...field} />
                  </FormControl>
                  <FormMessage />
                </FormItem>
              )}
            />
            <FormField
              control={form.control}
              name="preco"
              render={({ field }) => (
                <FormItem>
                  <FormLabel>Preço</FormLabel>
                  <FormControl>
                    <Input type="number" placeholder="Ex: 20,50; 32; 43,59..." {...field}/>
                  </FormControl>
                  <FormMessage />
                </FormItem>
              )}
            />
            <FormField
              control={form.control}
              name="estoqueMinimo"
              render={({ field }) => (
                <FormItem>
                  <FormLabel>Estoque mínimo</FormLabel>
                  <FormControl>
                    <Input type="number" placeholder="Ex: 5, 10, 20..." {...field}/>
                  </FormControl>
                  <FormMessage />
                </FormItem>
              )}
            />
            <FormField
              control={form.control}
              name="quantidadeEstoque"
              render={({ field }) => (
                <FormItem>
                  <FormLabel>Quantidade em estoque</FormLabel>
                  <FormControl>
                    <Input type="number" placeholder="Ex: 5, 10, 20..." {...field}/>
                  </FormControl>
                  <FormMessage />
                </FormItem>
              )}
            />
            <FormField
              control={form.control}
              name="imageUrl"
              render={({ field }) => (
                <FormItem>
                  <FormLabel>Imagem</FormLabel>
                  <FormControl>
                    <Input placeholder="https://imagem.produto..." {...field} />
                  </FormControl>
                  <FormMessage />
                </FormItem>
              )}
            />

            <FormField
              control={form.control}
              name="descricao"
              render={({ field }) => (
                <FormItem className="col-span-1 sm:col-span-2">
                  <FormLabel>Descrição</FormLabel>
                  <FormControl>
                    <Textarea placeholder="Este produto têm xxxx..." {...field}/>
                  </FormControl>
                  <FormMessage />
                </FormItem>
              )}
            />
            
            <div className="w-full flex flex-row items-center space-x-2">
              <Button type="submit" disabled={product && Object.keys(form.formState.dirtyFields).length === 0} variant={"default"} className="bg-sky-400 text-white hover:bg-sky-300" >{product ? "Editar" : "Criar produto"}</Button>
              {product ? null : <Button type="button" variant={"default"} className="bg-gray-400 text-white hover:bg-gray-300" onClick={() => form.reset()}>Limpar</Button>}
              
            </div>
        </div>
      </form>
    </Form>
  )
}
