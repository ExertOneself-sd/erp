import axios from "axios";

//自己创建service对象，用来完成对系统的axios进行封装，自己创建service可以和系统的axios
//
const service=axios.create({
    //baseURL:process.env.REACT_APP_API_BASE_URL,
    //timeout:5000,
    withCredentials:true,
    /*
    headers:{
        'Content-Type':'application/json',
    }
    */
})
export default service;