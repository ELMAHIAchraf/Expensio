import { Navigate, Outlet } from 'react-router-dom'
import { useEffect, useState } from 'react';
import { axiosInstance } from "../Axios";
// import axios from 'axios';
export const PrivateRoutes = () => {

    const [isAuth, setIsAuth] = useState(true);
    const checkAuth = async () => { 
    try {
      const token = `Bearer ${localStorage.getItem("token")}`
        const response = await axiosInstance.get("user", {
          headers:{
            Authorization : token
          }
        })
    } catch (error) {
        setIsAuth(false)
    }
    };
    useEffect(() => {
      checkAuth();
    }, []);
  
return (
    isAuth ? <Outlet/> : <Navigate to='/login'/>
  )
  
}