import { BrowserRouter, Navigate, Route, Routes } from 'react-router'
import { Roles } from './lib/definitions'
import AdminHome from './pages/AdminHome'
import CheckUserRoute from './pages/CheckUserRoute'
import ClienteHome from './pages/ClienteHome'
import FinishPurchase from './pages/FinishPurchase'
import Home from './pages/Home'
import LayoutRoute from './pages/LayoutRoute'
import Login from './pages/Login'
import PrivateRoute from './pages/PrivateRoute'
import ShoppingCart from './pages/ShoppingCart'
import AppProvider from './providers/AppProvider'

function App() {

  return (
    <BrowserRouter>
      <Routes>
        <Route element={<AppProvider />}>
          <Route path='/' element={<Navigate replace to={"/login"} />}/>
          <Route path='/login' element={<Login />} />
          <Route element={<PrivateRoute />}>
            <Route element={<LayoutRoute />}>
              <Route path='/home' element={<Home />} />
              <Route element={<CheckUserRoute roles={[Roles.ADMINISTRADOR]}/>}>
                <Route path='/home/admin' element={<AdminHome />}/>
              </Route>
              <Route element={<CheckUserRoute roles={[Roles.ADMINISTRADOR, Roles.CLIENTE]}/>}>
                <Route path='/home/cliente' element={<ClienteHome />}/>
                <Route path='/home/cliente/carrinho' element={<ShoppingCart />}/>
                <Route path='/home/cliente/finalizar-compra' element={<FinishPurchase />}/>

              </Route>
            </Route>
          </Route>
        </Route>
      </Routes>
    </BrowserRouter>
  )
}

export default App
