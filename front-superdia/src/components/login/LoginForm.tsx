"use client"

import { zodResolver } from "@hookform/resolvers/zod"
import { EyeIcon, EyeOffIcon } from "lucide-react"
import { useState } from "react"
import { useCookies } from "react-cookie"
import { useForm } from "react-hook-form"
import { useNavigate } from "react-router"
import { toast } from "../../hooks/use-toast"
import { Login, loginSchema } from "../../lib/definitions"
import { login } from "../../services/login"
import { Button } from "../ui/button"
import { Card, CardContent } from "../ui/card"
import { Form, FormControl, FormField, FormItem, FormLabel, FormMessage } from "../ui/form"
import { Input } from "../ui/input"

export default function LoginForm() {
  const [showPassword, setShowPassword] = useState(false)
  const [,setCookies] = useCookies()
  const navigate = useNavigate()

  const form = useForm<Login>({
    resolver: zodResolver(loginSchema),
    defaultValues: {
      login: "",
      senha: ""
    },
    mode: "onSubmit"
  })

  async function onSubmit(formData:Login) {
    try {
      const response = await login(formData)
      if(response.status === 400){
        toast({
					variant: "destructive",
					description: "Usuário ou senha incorreto.",
					duration: 3000,
				})
      } else if(response.status === 200){
          const expirationDate = new Date()
          expirationDate.setDate(expirationDate.getDate() + 1)
          setCookies("session", {id: response.data.id, perfil: response.data.perfil, user: response.data.pessoa.nome}, {expires: expirationDate, path: "/"})
          navigate("/home")
        
      } else {
        toast({
					variant: "destructive",
					description: "Houve um erro ao enviar o formulário.",
					duration: 3000,
				})
      }
      console.log(response)
    } catch (error) {
      console.error(error)
      toast({
					variant: "destructive",
					description: "Houve um erro ao enviar o formulário.",
					duration: 3000,
			})
    }
  }

  const togglePasswordVisibility = () => {
    setShowPassword(!showPassword)
  }

  return (
    <Card>
      <CardContent className="p-5">
        <Form {...form}>
          <form className="space-y-4" onSubmit={form.handleSubmit(onSubmit)}>
            <FormField
              control={form.control}
              name="login"
              render={({ field }) => (
                <FormItem>
                  <FormLabel>Login</FormLabel>
                  <FormControl>
                    <Input placeholder="CPF ou Email" {...field} />
                  </FormControl>
                  <FormMessage />
                </FormItem>
              )}
            />
            <FormField
              control={form.control}
              name="senha"
              render={({ field }) => (
                <FormItem>
                  <FormLabel>Login</FormLabel>
                  <FormControl>
                    <div className="relative">
                      <Input type={showPassword ? "text" : "password"} placeholder="Senha" {...field} />
                      <Button
                        type="button"
                        variant="ghost"
                        size="icon"
                        className="absolute right-2 top-1/2 transform -translate-y-1/2"
                        onClick={togglePasswordVisibility}
                      >
                        {showPassword ? <EyeOffIcon className="h-4 w-4" /> : <EyeIcon className="h-4 w-4" />}
                      </Button>
                    </div>
                  </FormControl>
                  <FormMessage />
                </FormItem>
              )}
            />
            <div className="space-y-2">
              
            </div>
            <Button type="submit" className="w-full">
              Entrar
            </Button>
          </form>
        </Form>
        
      </CardContent>
    </Card>
  )
}

