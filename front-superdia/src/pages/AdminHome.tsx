import { Icon } from '@iconify-icon/react';
import { SortingState } from '@tanstack/react-table';
import { useState } from 'react';
import { useCookies } from "react-cookie";
import useSWR, { mutate } from "swr";
import Header from "../components/admin/home/Header";
import { columns } from '../components/admin/home/Products/productsTable/columns';
import WarningAlert from "../components/admin/home/WarningAlert";
import { DataTable } from '../components/ui/data-table';
import { toast } from '../hooks/use-toast';
import { ApiResponse, Product } from "../lib/definitions";
import { convertStringToRole, fetcher } from "../lib/utils";
import { deleteProductById } from '../services/product';

export default function AdminHome() {
  const [cookies] = useCookies()
  const [sorting, setSorting] = useState<SortingState>([
    {
      id: "quantidadeEstoque",
      desc: false
    }
  ])
  const userId = cookies.session?.id
  const userRole = convertStringToRole(cookies.session?.perfil)

  const {data, error, isLoading} = useSWR([`${import.meta.env.VITE_API_URL}produtos/todos`, userId, userRole], ([url, userId, userRole]) => fetcher<ApiResponse<Product[]>>(url, userId, userRole))

	const handleDelete = async (id: string) => {
		try {
			const response = await deleteProductById(id)
			if (response.status === 200) {
				mutate((key: string[]) => Array.isArray(key) && key[0].startsWith(`${import.meta.env.VITE_API_URL}produtos`))
				toast({
					variant: "default",
					description: "Produto removido com sucesso.",
					className: "bg-green-200 text-black",
					duration: 3000,
				})
			} else {
				toast({
					variant: "destructive",
					description: "Houve um erro ao remover produto.",
					duration: 3000,
				})
			}
		} catch (error) {
      console.error(error)
			toast({
				variant: "destructive",
				description: "Houve um erro ao remover produto.",
				duration: 3000,
			})
		}
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
    const lowStockProducts = data.data.filter(product => product.quantidadeEstoque! <= product.estoqueMinimo!)
    const hasLowStockProducts = lowStockProducts.length > 0

    return (
    <div className="flex flex-col space-y-5 mt-5 w-full px-[5%] py-5">
      <Header />
      {hasLowStockProducts ? <WarningAlert products={lowStockProducts}/> : null}
      
      <DataTable columns={columns} data={data.data} handleDelete={handleDelete} sorting={sorting} setSorting={setSorting}/>
    </div>
  )
  }
  
}
