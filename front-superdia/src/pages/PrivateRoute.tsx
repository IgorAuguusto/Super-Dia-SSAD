import { useCookies } from "react-cookie"
import { Navigate, Outlet } from "react-router"

export default function PrivateRoute() {
  const [cookies] = useCookies()
  const id = cookies.session?.id
  const perfil = cookies.session?.perfil
  if(!id && !perfil) return <Navigate to={"/login"}/>
  return <Outlet />
}
