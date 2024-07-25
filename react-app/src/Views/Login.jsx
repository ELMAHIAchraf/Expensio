
import { useRef, useState } from "react";
import { axiosInstance, setTokenInLocalStorage } from "../Axios";
import { Link, useNavigate } from "react-router-dom";
import toast from "react-hot-toast";


export const Login = () => {
    const email = useRef('');
    const password = useRef('');
    const navigate = useNavigate()

    const [invalideInputs, setInvalideInputs] = useState({email:0, password:0});
    const getData = () => {
        return {
          email : email.current.value,
          password : password.current.value
        }
      }
      const validateData = () => {
        setInvalideInputs({email:'', password:''})
        const data={
            email: !email.current.value.match(/^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/) ? 1 : 0,
            password: password.current.value.length<8 ? 1 : 0,
        }
        const values = Object.values(data)
        values.every(value=>value===0) && login()
        setInvalideInputs(data)
    }
    const login = async() => {
        try{
            const response = await axiosInstance.post('auth/login', getData());
            setTokenInLocalStorage(response.data.jwtToken)
            console.log(response.data.jwtToken);
            navigate('/home')
        }catch(error){
            toast.error(error.response.data)
        }
    }

  return (
    <div className="h-screen flex justify-center items-center flex-col ">
    <div className="w-4/6 md:w-1/3 lg:w-1/4">
        <div className="space-y-4 mb-8">
            <p className="text-white text-3xl font-bold">Welcome back!</p>
            <p className="text-[#7d7d8e]">Don&apos;t have an account? <Link className="text-[#00d8c4] cursor-pointer" to="/">Sign up</Link></p>
        </div>
        <div>
            <div className="bg-[#232222] p-6 rounded-lg mb-2">   
                <div className="mb-5">
                    <label htmlFor="email" className="block mb-2 text-sm font-medium text-gray-900 dark:text-white">Email</label>
                    <input ref={email} type="email" id="email" className={`border-[#303541] bg-gray-50 border  text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-[#232222] dark:border-${invalideInputs.email===0 ? '[#323745]' : 'red-500'} dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500`} placeholder="exemple@gmail.com" required />
                    {invalideInputs.email===1 ? <p className="mt-2 ml-1 text-sm text-red-600 dark:text-red-500">Email is Required</p> : ''}

                </div>
                <div className="mb-1">
                    <label htmlFor="password" className="block mb-2 text-sm font-medium text-gray-900 dark:text-white">Password</label>
                    <input ref={password} type="password" id="password" className={`border-[#303541] bg-gray-50 border text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-[#232222] dark:border-${invalideInputs.password===0 ? '[#323745]' : 'red-500'} dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500`} placeholder="********" required />
                    {invalideInputs.password===1 ? <p className="mt-2 ml-1 text-sm text-red-600 dark:text-red-500">Password must be 8 characters minimum</p> : ''}
                </div>

            </div>
            <div className="flex justify-between items-center my-4">
            <div className="flex items-center h-5">
                <input id="remember" type="checkbox" className="accent-[#00dac6] w-4 h-4 border border-gray-300 rounded bg-gray-50 focus:ring-3 focus:ring-blue-300 dark:bg-gray-700 dark:border-gray-600 dark:focus:ring-blue-600 dark:ring-offset-gray-800 dark:focus:ring-offset-gray-800" required />
                <label htmlFor="remember" className="ms-2 text-sm font-medium text-gray-900 dark:text-gray-300">Remember me</label>

            </div>
            <div><p className="text-[#00dac6] text-sm">Forgot your password?</p></div>
            </div>
            <button type="submit" className="text-white focus:ring-4 focus:outline-none focus:ring-blue-300 font-medium rounded-lg text-md  w-full h-12 px-5 py-2.5 text-center dark:bg-[#00dac6] dark:hover:bg-[#00b3a2] dark:focus:ring[#00b3a2]" onClick={validateData}>Signin</button>    
        </div>
        </div>
    </div>
  )
}
