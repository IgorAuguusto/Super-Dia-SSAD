import { useCookies } from "react-cookie";
import { Navigate } from "react-router";
import LoginForm from "../components/login/LoginForm";

export default function Login() {
  const [cookies] = useCookies()

  console.log(cookies.session)

  if(cookies.session){
    console.log("entrou")
    return <Navigate to={"/home"} />
  }

  return (
    <div className="flex items-center justify-center min-h-screen bg-gray-100">
      <div className="w-full max-w-md">
        <LoginForm />
      </div>
    </div>
  )
}
