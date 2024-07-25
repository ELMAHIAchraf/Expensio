import axios from "axios";
import { useRef, useState, useEffect } from "react";
import { axiosInstance, setTokenInLocalStorage } from "../Axios";
import toast from "react-hot-toast";
import { useNavigate } from "react-router-dom";


export const SignUpForm = () => {

    const [avatar, setAvatar] = useState(0);
    const [fileName, setFileName] = useState('');
    const [invalideInputs, setInvalideInputs] = useState({fname:0, lname:0, currency:0, file:0});
    const [invalideInputs2, setInvalideInputs2] = useState({email:0, password:0, Cpassword:0});


    const fname = useRef('');
    const lname = useRef('');
    const currency = useRef('');
    const file = useRef(null);
    const email = useRef('');
    const password = useRef('');
    const Cpassword = useRef('')

    const navigate = useNavigate()

    
    const getData = () => {
      return {
        Fname : fname.current.value,
        Lname : lname.current.value,
        currency : currency.current.value,
        email : email.current.value,
        password : password.current.value,
        avatar : file.current.files[0]
      }
    }
    const getFileName = () => {
        const fileName=file.current.value
        return fileName.substring(fileName.lastIndexOf('\\')+1)
    }

    const validateData1 = () => {
        setInvalideInputs({fname:'', lname:'', currency:'', file:''})
        const data={
            fname: !fname.current.value.match(/^[a-zA-Z '-]{2,20}$/) ? 1 : 0,
            lname: !lname.current.value.match(/^[a-zA-Z '-]{2,20}$/) ? 1 : 0,
            currency: currency.current.value=="" ? 1 : 0,
            file: file.current.value=="" ? 1 : 0
        }
        const values = Object.values(data)
        values.every(value=>value===0) && setProgress(2)
        setInvalideInputs(data)
    }

    const validateData2 = () => {
        setInvalideInputs2({email:'', password:'', Cpassword:''})
        const data={
            email: !email.current.value.match(/^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/) ? 1 : 0,
            password: password.current.value.length<8 ? 1 : 0,
            Cpassword: Cpassword.current.value!==password.current.value ? 1 : 0,
        }
        const values = Object.values(data)
        values.every(value=>value===0) && register()
        setInvalideInputs2(data)
    }
    
    
    const register = async() => {
        try {
            const response = await axiosInstance.post('auth/register', getData(), {
                headers : {
                "Content-Type" : "multipart/form-data"
                }
            });
            setTokenInLocalStorage(response.data.jwtToken)
            navigate('/home')
        }catch(error) {
            toast.error(error.response.data.message)
        }
    }
    
    
    
    const [currencies, setCurrencies] = useState([]);
    
    const getCurrencies = async () =>{
        const response = await axios.get("https://restcountries.com/v3.1/all?fields=currencies")
        setCurrencies(response.data);
    }

    useEffect(() => {
        getCurrencies();
    }, []);
    
    const [progress, setProgress] = useState(1);
    

  return (
    <>
        <div className={progress===1 ? 'block' : 'hidden'}>
            <div className="bg-[#232222] p-6 rounded-lg mb-4">   
                <div className="mb-5">
                <label htmlFor="fname" className="block mb-2 text-sm font-medium text-gray-900 dark:text-white">First Name</label>
                    <input ref={fname} type="fname" id="fname" className={`border-[#303541] bg-gray-50 border  text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-[#232222] dark:border-${invalideInputs.fname===0? '[#323745]' : 'red-500'} dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500`} placeholder="John" required />
                    {invalideInputs.fname===1 ? <p className="mt-2 ml-1 text-sm text-red-600 dark:text-red-500">First Name is Required</p> : ''}
                </div>
                <div className="mb-5">
                    <label htmlFor="lname" className="block mb-2 text-sm font-medium text-gray-900 dark:text-white">Last Name</label>
                    <input ref={lname} type="lname" id="lname" className={`border-[#303541] bg-gray-50 border text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-[#232222] dark:border-${invalideInputs.lname===0? '[#323745]' : 'red-500'} dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500`} placeholder="Doe" required />
                    {invalideInputs.lname===1 ? <p className="mt-2 ml-1 text-sm text-red-600 dark:text-red-500">Last Name is Required</p> : ''}

                </div>  
                <div className="mb-5 ">
                    <label htmlFor="currencies" className="block mb-2 text-sm font-medium text-gray-900 dark:text-white">Currency</label>
                    <select ref={currency} id="currencies" className={`border-[#303541] bg-gray-50 border text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-[#2f3139] dark:border-${invalideInputs.currency===0 ? '[#323745]' : 'red-500'} dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500`}>
                        {currencies.map((currency)=>{
                            return <option  className="p-"
                                key={Object.keys(currency.currencies)[0]}
                                value={currency.currencies[Object.keys(currency.currencies)[0]]?.symbol ?? ''}>
                                    {Object.keys(currency.currencies)[0]}
                            </option>
                        })}
                    </select>
                    {invalideInputs.currency===1 ? <p className="mt-2 text-sm text-red-600 dark:text-red-500">Currency is Required</p> : ''}


                </div>

                <label className="block mb-2 text-sm font-medium text-gray-900 dark:text-white" htmlFor="multiple_files">Profile Picture</label>
                <div className={`border rounded-lg border-[#303541] h-12 cursor-pointer ${invalideInputs.file===0? '' : 'border-red-500'}`} onClick={()=>file.current.click()}>  
                    <div className="flex divide-x-2 divide-[#535353] h-full">
                        <div className="w-8/12 h-full flex items-center"><p className="text-[#787a7b] ml-3 truncate">{avatar===1? fileName : 'Upload file here'}</p></div>
                        <div className="h-full flex items-center"><img src="images/Add Image.svg" className=" ml-2" /><span className="text-white ml-2">Upload</span></div>
                    </div>
                    <input ref={file} accept="image/*" className="hidden w-full text-sm text-gray-900 border border-gray-300 rounded-lg cursor-pointer bg-gray-50 dark:text-gray-400 focus:outline-none dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400" id="multiple_files" type="file" onChange={()=>{setAvatar(1), setFileName(getFileName())}}/>
                </div>
                {invalideInputs.file===1 ? <p className="mt-2 ml-1 text-sm text-red-600 dark:text-red-500">Avatar is Required</p> : ''}

            </div>
            <button type="submit" className="text-white focus:ring-4 focus:outline-none focus:ring-blue-300 font-medium rounded-lg text-md  w-full h-12 px-5 py-2.5 text-center dark:bg-[#00dac6] dark:hover:bg-[#00b3a2] dark:focus:ring[#00b3a2]" onClick={validateData1}>Continue</button> 
         </div>  


        <div className={progress===2 ? 'block' : 'hidden'}>
            <div className="bg-[#232222] p-6 rounded-lg mb-2">   
                <div className="mb-5">
                    <label htmlFor="email" className="block mb-2 text-sm font-medium text-gray-900 dark:text-white">Email</label>
                    <input ref={email} type="email" id="email" className={`border-[#303541] bg-gray-50 border  text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-[#232222] dark:border-${invalideInputs2.email===0 ? '[#323745]' : 'red-500'} dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500`} placeholder="exemple@gmail.com" required />
                    {invalideInputs2.email===1 ? <p className="mt-2 ml-1 text-sm text-red-600 dark:text-red-500">Email is Required</p> : ''}

                </div>
                <div className="mb-5">
                    <label htmlFor="password" className="block mb-2 text-sm font-medium text-gray-900 dark:text-white">Password</label>
                    <input ref={password} type="password" id="password" className={`border-[#303541] bg-gray-50 border text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-[#232222] dark:border-${invalideInputs2.password===0 ? '[#323745]' : 'red-500'} dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500`} placeholder="********" required />
                    {invalideInputs2.password===1 ? <p className="mt-2 ml-1 text-sm text-red-600 dark:text-red-500">Password must be 8 characters minimum</p> : ''}

                </div>
                <div className="mb-5">
                    <label htmlFor="password" className="block mb-2 text-sm font-medium text-gray-900 dark:text-white">Password Confirmation</label>
                    <input ref={Cpassword} type="password" id="Cpassword" className={`border-[#303541] bg-gray-50 border text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-[#232222] dark:border-${invalideInputs2.Cpassword===0 ? '[#323745]' : 'red-500'} dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500`} placeholder="********" required />
                    {invalideInputs2.Cpassword===1 ? <p className="mt-2 ml-1 text-sm text-red-600 dark:text-red-500">Passwords do not match</p> : ''}

                </div>  

            </div>
            <div className="flex justify-end items-center mb-2">
            <button className="flex items-center justify-center px-3 h-8  text-sm font-medium text-gray-500 bg-white border border-gray-300 rounded-lg hover:bg-gray-100 hover:text-gray-700 dark:bg-[#232222] dark:border-gray-700 dark:text-gray-400 dark:hover:bg-[#373535] dark:hover:text-white" onClick={()=>setProgress(1)}>
                <svg className="w-3.5 h-3.5 me-2 rtl:rotate-180" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 14 10">
                    <path stroke="currentColor"  d="M13 5H1m0 0 4 4M1 5l4-4"/>
                </svg>
                Previous
            </button>
            </div>
            <button type="submit" className="text-white focus:ring-4 focus:outline-none focus:ring-blue-300 font-medium rounded-lg text-md  w-full h-12 px-5 py-2.5 text-center dark:bg-[#00dac6] dark:hover:bg-[#00b3a2] dark:focus:ring[#00b3a2]" onClick={validateData2}>Register</button>    
        </div>

    </>
  )
}
