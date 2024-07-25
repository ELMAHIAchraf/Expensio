
import toast from 'react-hot-toast'

export const EditBalance = () => {
  return (
    <>
        

<i data-modal-target="authentication-modal" data-modal-toggle="authentication-modal" className="fa-solid fa-pen-to-square fa-xl ml-4 " style={{color: "#00dac6"}}></i>

<div id="authentication-modal"  aria-hidden="true" className="hidden overflow-y-auto overflow-x-hidden fixed top-0 right-0 left-0 z-50 justify-center items-center w-full md:inset-0 h-screen bg-[#0000008b]">
    <div className="relative p-4 w-full max-w-md max-h-full">
        <div className="relative bg-white rounded-lg shadow dark:bg-[#1b1b1b]">
            <div className="flex items-center justify-between p-4 md:p-5 border-b rounded-t dark:border-gray-600">
                <h3 className="text-xl font-semibold text-gray-900 dark:text-[#bababa]">
                    Edit your income
                </h3>
                <button type="button" className="end-2.5 text-gray-400 bg-transparent hover:bg-gray-200 hover:text-gray-900 rounded-lg text-sm w-8 h-8 ms-auto inline-flex justify-center items-center dark:hover:bg-gray-600 dark:hover:text-white" data-modal-hide="authentication-modal">
                    <svg className="w-3 h-3" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 14 14">
                        <path stroke="currentColor"  d="m1 1 6 6m0 0 6 6M7 7l6-6M7 7l-6 6"/>
                    </svg>
                    <span className="sr-only">Close modal</span>
                </button>
            </div>
            <div className="p-4 md:p-5">
                    <div>
                        <label htmlFor="email" className="block mb-2 text-sm font-medium text-gray-900 dark:text-[#bababa]">Your income</label>
                        <input type="text" name="text" id="text" className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-[#525252] dark:border-gray-500 dark:text-white" value={4000} required />
                    </div>
                    
                   <br />
                    <button className="w-full text-white focus:ring-4 focus:outline-none  font-medium rounded-lg text-sm px-5 py-2.5 text-center dark:bg-[#017167] dark:hover:bg-[#003d38] dark:focus:ring-0" data-modal-hide="authentication-modal" onClick={()=>toast.success('Your income has been updated!')}>Edit</button>
            </div>
        </div>
    </div>
</div> 

    </>
  )
}
