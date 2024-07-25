import { useState } from "react";
import { EditBalance } from "./EditBalance";
export const BalanceCard = () => {
  const [hover, setHover] = useState(false);

  return (
    <div id="card" className="relative bg-gradient-to-r from-[#000000] to-[#124241]  mt-6 ml-20 w-1/4 h-48 border-2 border-emerald-600 rounded-2xl " onMouseOver={()=>setHover(true)} onMouseOut={()=>setHover(false)}>
        <img src="images/Lines.svg" className="w-full h-full object-cover"/>

        <div className="absolute inset-0 grid grid-rows-3 grid-cols-3 items-center">
        <div className={`col-start-1 justify-self-start transition-opacity ${hover ? 'opacity-100' : 'opacity-0 '} cursor-pointer `}>
          <EditBalance />
        </div>
        <div className="col-start-3 justify-self-end mr-4">
          <img src="images/nfc.png" className="w-10"/>
        </div>
        <div className="col-start-2 justify-self-center flex justify-center items-center flex-col -mt-4">
          <p className="text-[#00dac6] text-sm font-light">Total balance</p>
          <p className="text-[#00dac6] font-bold text-2xl">1660$</p>
        </div>
        <div className="col-start-1 justify-self-center flex justify-center items-center flex-col">
          <p className="text-[#00dac6] text-xs font-light">Income</p>
          <p className="text-[#00dac6] font-bold text-md">4000$</p>
        </div>
        <div className="col-start-3 justify-self-center flex justify-center items-center flex-col">
          <p className="text-[#00dac6] text-xs font-light">Expensed</p>
          <p className="text-[#00dac6] font-bold text-md">2340$</p>
        </div>
        </div>
    </div>
  )
}
