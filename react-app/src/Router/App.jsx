import { BrowserRouter, Route,  Routes } from 'react-router-dom'
import { Home } from '../Views/Home'
import { Signup } from '../Views/Signup';
import { Login } from '../Views/Login';
import { NotFound } from '../Views/NotFound';
import {PrivateRoutes} from "../Router/PrivateRoutes"

 const App = () => {
  return (
    <BrowserRouter>
        <Routes>
            <Route path='/'>
              <Route index element={<Signup />} />
              <Route path="login" element={<Login/>} />
              <Route path="*" element={<NotFound/>} />  
              <Route element={<PrivateRoutes/>}>
                <Route path="home" element={<Home />} />
              </Route> 
            </Route>
        </Routes> 
    </BrowserRouter>
  )
}
export default App;
