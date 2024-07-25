import { SideBar } from "../Components/SideBar"
import { useEffect } from "react";
import { BalanceCard } from "../Components/BalanceCard"
import { useDispatch } from "react-redux";
import { fetchUser } from "../State/userSlice";

export const Home = () => {
    const dispatch = useDispatch();
    useEffect(() => {
      dispatch(fetchUser());
    }, [dispatch]);
  return (
    <>
      <SideBar/>
      <div className="bg-black w-[1160px] h-[690px] absolute top-7 left-[350px] rounded-lg">
        <BalanceCard/>
      </div>
    </>
  )
}
