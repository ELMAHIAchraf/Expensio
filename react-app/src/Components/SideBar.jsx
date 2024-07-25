import { useState, useEffect } from "react";
import { useSelector, useDispatch } from "react-redux";
import {deleteUser} from "../State/userSlice";
import { axiosInstance } from "../Axios";
import { useNavigate } from "react-router-dom";

export const SideBar = () => {

   const [selection, setSelection] = useState('Home');
   const [open, setOpen] = useState(null);
   const [toggleState, setToggleState] = useState(false);
   const [isLargeWidth, setIsLargeWidth] = useState(window.innerWidth > 1024 ? true : false );
   const changeWidth = () => window.innerWidth > 1024 ? setIsLargeWidth(true) : setIsLargeWidth(false)
   const changeStat = () => isLargeWidth ? setOpen(true) : setOpen(false) 
   useEffect(() => {
      changeStat();
   }, [isLargeWidth]);
   addEventListener('resize', changeWidth)

   const toggleAvatar = () => {
      toggleState ? setToggleState(false) : setToggleState(true)
      console.log(toggleState)
   }
   

   const user = useSelector((state) => state.user);

   const dispatch = useDispatch();
   const navigate = useNavigate();
   const logout = async() => {
      try {
         const response = await axiosInstance.post("auth/logoutUser")
         dispatch(deleteUser());
         navigate("/login");
      } catch (error) {
         console.log(error)
      }
         
   }
   
  return (
    <>
        
   {
   !isLargeWidth &&
      <div className=" cursor-pointer bg-[#1b1b1b] py-4 fixed w-full top-0 left-0 lg:bg-transparent" onClick={()=>setOpen(1)}>
         <i className="fa-solid fa-bars text-[#00dac6] fa-lg ml-3"  ></i>
      </div>
   }

<div id="drawer-navigation"  className={`fixed top-0 left-0 z-40 h-screen p-4 ${open ? '': 'overflow-y-auto transition-transform -translate-x-full'} bg-white w-80 dark:bg-[#1b1b1b]`}>

    {
      !isLargeWidth &&
         <button type="button"  className="text-gray-400 bg-transparent hover:bg-gray-700 hover:text-gray-900 rounded-lg text-sm w-8 h-8 absolute top-2.5 end-2.5 inline-flex items-center justify-center dark:hover:bg-[#00786d] dark:hover:text-white" onClick={()=>setOpen(0)}>
         <svg className="w-3 h-3" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 14 14">
            <path stroke="currentColor"  d="m1 1 6 6m0 0 6 6M7 7l6-6M7 7l-6 6"/>
         </svg>
         <span className="sr-only">Close menu</span>
         </button>
    }
  <div className="py-4 overflow-y-auto relative">
      <ul className="relative space-y-2 font-medium ">
         <li>     
            <img type="button" className="w-24 h-24 rounded-full cursor-pointer m-auto" src="images/Gh0st.jpg" alt="User dropdown" onClick={toggleAvatar}/>
           
            <div  className={`w-54 z-10 absolute top-48 left-1/2 transform -translate-x-1/2 -translate-y-1/2   ${toggleState ? 'block' : 'hidden'}   bg-white divide-y divide-gray-200 rounded-lg shadow w-50 dark:bg-[#353535] dark:divide-[#140e0e]`}>
               <div className="px-4 py-3 text-sm text-gray-900 dark:text-white">
                  <div className="font-medium truncate">{user.email}</div>
               </div>
               <ul className="py-2 text-sm text-gray-700 dark:text-gray-200" aria-labelledby="avatarButton">
                  <li>
                  <a href="#" className="block px-4 py-2 hover:bg-gray-100 dark:hover:bg-[#008c7f] dark:hover:text-white">Change account details</a>
                  </li>
                  <li>
                  <a href="#" className="block px-4 py-2 hover:bg-gray-100 dark:hover:bg-[#008c7f] dark:hover:text-white">Change password</a>
                  </li>
               </ul>
               <li className="py-1">
                  <a href="#" className=" block px-4 py-2 text-sm text-gray-700 hover:bg-gray-100 dark:hover:bg-[#008c7f] dark:text-gray-200 dark:hover:text-white" onClick={logout}>Sign out</a>
               </li>
            </div>

            <div className='flex justify-center mb-8 mt-2'>   
               <p className='text-[#a4a4a4] font-bold text-md'>{user.fname} {user.lname}</p>
            </div>
         </li>
         <li>
            <a href="#" className={`flex items-center p-2 text-gray-900 rounded-lg dark:text-white hover:bg-gray-100 dark:hover:bg-[#454545] group h-14 cursor-pointer ${selection =='Home' ? 'bg-[#28282a] border-[#383838] border-2' : ''}`} onClick={()=>setSelection('Home')}>
               <i className="fa-sharp fa-regular fa-house fa-lg text-[#00dac6] ml-2"></i>
               <span className="ms-3 text-[#00dac6]">Home</span>
            </a>
         </li>
         <li>
            <a href="#" className={`flex items-center p-2 text-gray-900 rounded-lg dark:text-white hover:bg-gray-100 dark:hover:bg-[#454545] group h-14 cursor-pointer ${selection =='Expenses' ? 'bg-[#28282a] border-[#383838] border-2' : ''} `}  onClick={()=>setSelection('Expenses')}>
               <i className="fa-regular fa-credit-card fa-lg  text-[#00dac6] ml-2"></i>
               <span className="ms-3 text-[#00dac6]">Expenses</span>
            </a>
         </li>
         <li>
            <a href="#" className={`flex items-center p-2 text-gray-900 rounded-lg dark:text-white hover:bg-gray-100 dark:hover:bg-[#454545] group h-14 cursor-pointer ${selection =='Statistics' ? 'bg-[#28282a] border-[#383838] border-2' : ''}`}  onClick={()=>setSelection('Statistics')}>
            <i className="fa-solid fa-chart-pie fa-lg text-[#00dac6] ml-2"></i>           
               <span className="ms-3 text-[#00dac6]">Statistics</span>
            </a>
         </li>
         <li>
            <a href="#" className={`flex items-center p-2 text-gray-900 rounded-lg dark:text-white hover:bg-gray-100 dark:hover:bg-[#454545] group h-14 cursor-pointer ${selection =='Settings' ? 'bg-[#28282a] border-[#383838] border-2' : ''}`}  onClick={()=>setSelection('Settings')}>
            <i className="fa-solid fa-sliders fa-lg text-[#00dac6] ml-2"></i>           
               <span className="ms-3 text-[#00dac6]">Settings</span>
            </a>
         </li>
         <li>
            <a href="#" className={`flex items-center p-2 text-gray-900 rounded-lg dark:text-white hover:bg-gray-100 dark:hover:bg-[#454545] group h-14 cursor-pointer ${selection =='Support' ? 'bg-[#28282a] border-[#383838] border-2' : ''}`}  onClick={()=>setSelection('Support')}>
            <i className="fa-solid fa-headset fa-lg text-[#00dac6] ml-2"></i>           
               <span className="ms-3 text-[#00dac6]">Support</span>
            </a>
         </li>
         
      </ul>
   </div>
</div>

    </>
  )
}

