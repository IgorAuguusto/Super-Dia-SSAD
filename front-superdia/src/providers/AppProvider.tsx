import { CookiesProvider } from "react-cookie"
import { Outlet } from "react-router"
import { Toaster } from "../components/ui/toaster"
import { TooltipProvider } from "../components/ui/tooltip"
export default function AppProvider() {
  return (
    <CookiesProvider>
      <TooltipProvider>
        <Toaster />
        <Outlet />
      </TooltipProvider>
    </CookiesProvider>
  )
}
