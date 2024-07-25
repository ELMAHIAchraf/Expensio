import { Link } from "react-router-dom"
import { SignUpForm } from "../Components/SignUpForm"

export const Signup = () => {
  return (
    
    <div className="h-screen flex justify-center items-center flex-col ">
    <div className="w-4/6 md:w-1/3 lg:w-1/4">
        <div className="space-y-4 mb-8">
            <p className="text-white text-3xl font-bold">Get&apos;s started</p>
            <p className="text-[#7d7d8e]">Already have an account? <Link className="text-[#00d8c4] cursor-pointer" to="/login">Sign in</Link></p>
        </div>
          <SignUpForm/>
        </div>
    </div>
  )
}
