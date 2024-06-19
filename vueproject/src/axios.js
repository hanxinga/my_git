import axios from 'axios';

const apiClient = axios.create({
    baseURL: 'http://localhost:8080/api', // 后端API的基础路径
    withCredentials: true, // 允许携带cookie
    headers: {
        'Content-Type': 'application/json',
    },
});

export default apiClient;
