import { ChevronDown, LogOut, ShoppingCart, User } from "lucide-react";
import { useState } from "react";
import { useCookies } from "react-cookie";
import { Outlet, useNavigate } from "react-router";
import { Badge } from "../components/ui/badge";
import { Popover, PopoverContent, PopoverTrigger } from "../components/ui/popover";
import { Tooltip, TooltipContent, TooltipTrigger } from "../components/ui/tooltip";
import { useCartProducts } from "../hooks/use-cart-products";
import { Roles } from "../lib/definitions";
import { convertStringToRole } from "../lib/utils";

export default function LayoutRoute() {
  const [open, setOpen] = useState<boolean>(false)
  // const {count, } = useCounter()
  const {productsCount} = useCartProducts()
  const [cookies, , removeCookies] = useCookies()
  const navigate = useNavigate()
  const user = cookies.session?.user
  const perfil = convertStringToRole(cookies.session?.perfil)

  return (
    <div className='w-full flex flex-col bg-sky-100 min-h-screen'>
      <header className="w-full flex flex-row items-center justify-between bg-red-500 text-white px-10 py-2 text-xl ">
        <div onClick={() => navigate("/home")} className="cursor-pointer">
          <img src="/logo-dia.svg" alt="Logo do supermercado dia" className="size-24" />
        </div>
        <div className="flex flex-row items-center space-x-2">
          <span>Olá</span>
          <User />
          <Popover open={open} onOpenChange={setOpen}>
            <PopoverTrigger asChild>
              <span 
                onClick={() => setOpen(!open)} 
                onMouseEnter={() => setOpen(true)} 
                onMouseLeave={() => setTimeout(() => {setOpen(false)}, 500)} 
                className="font-bold cursor-pointer flex flex-row items-center">
                {user}
                <ChevronDown />
              </span>
            </PopoverTrigger>
            <PopoverContent className="w-40 p-0">
              <span onClick={() => {
                removeCookies("session")
                navigate("/login")
              }} className="flex cursor-pointer hover:bg-slate-200 p-4 flex-row items-center space-x-1"><LogOut /> Logout</span>
            </PopoverContent>
          </Popover>
          {perfil === Roles.CLIENTE ? (
            <Tooltip>
              <TooltipTrigger asChild>
                <div onClick={() => navigate("/home/cliente/carrinho")} className="flex flex-row items-center space-x-2 cursor-pointer group">
                  <ShoppingCart className="size-12 group-hover:text-gray-200"/>
                  <Badge variant="secondary" className="text-lg rounded-full px-2 py-0 hover:bg-opacity-100 group-hover:bg-gray-200">{productsCount}</Badge>
                </div>
              </TooltipTrigger>
              <TooltipContent side="bottom">
                <p>Ver produtos no carrinho</p>
              </TooltipContent>
            </Tooltip>
            
          ) : null}
        </div>
        
      </header>
      <main className="flex flex-1">
        <Outlet />
      </main>
    </div>
  )
}
