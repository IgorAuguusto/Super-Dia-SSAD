import { useEffect } from "react"
import { useCookies } from "react-cookie"
import { useNavigate } from "react-router"
import { Roles } from "../lib/definitions"

export default function Home() {
  const [cookies] = useCookies()
  const perfil = cookies.session?.perfil
  const navigate = useNavigate()


  useEffect(() => {
    if (perfil === Roles.ADMINISTRADOR) {
      navigate("/home/admin");
    } else if (perfil === Roles.CLIENTE) {
      navigate("/home/cliente");
    } else {
      navigate("/home/caixa");
    }
  }, [perfil, navigate]);

  return null

}
