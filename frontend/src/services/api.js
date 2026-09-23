import axios from 'axios'

const api = axios.create({
    baseURL: '/' // kontrol etmek lazım proxy nereye yönlendiriyor
})

api.interceptors.request.use((config) => {

    const token = localStorage.getItem("patient-management-token");

    if (token){
        config.headers.Authorization = `Bearer ${token}`;
    }

    return config;
})

export default api;