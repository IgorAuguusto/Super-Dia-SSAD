import { useCookies } from 'react-cookie'
import { Outlet } from 'react-router'
import { Roles } from '../lib/definitions'
import { convertStringToRole } from '../lib/utils'

interface CheckUserRoleProps {
	roles: Roles[]
}

export default function CheckUserRoute({roles}: CheckUserRoleProps) {
  const [cookies] = useCookies(["session"])
  const perfil = cookies.session.perfil

  console.log(perfil)

  const hasPermission = roles.includes(convertStringToRole(perfil))

  if(hasPermission){
    return <Outlet />
  }

  return (
    <span>Acesso não autorizado.</span>
  )
}
