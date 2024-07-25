import axios from "axios";
export const axiosInstance = axios.create({
    baseURL: 'http://localhost:8080/api/',
    timeout: 2000,
  });

axiosInstance.interceptors.request.use(
  function(config){
    const token = localStorage.getItem('token');
    if(token){
      config.headers.Authorization = `Bearer ${token}`;
    }
    return config
  }
)

export function setTokenInLocalStorage(token) {
    localStorage.setItem("token" , token);
}

