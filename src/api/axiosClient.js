import axios from 'axios';

const axiosClient = axios.create({
  baseURL: '/api', // Use relative path to trigger proxy
  headers: {
    'Content-Type': 'application/json',
  },
});

// Add a request interceptor to include the token if it exists
axiosClient.interceptors.request.use(
  (config) => {
    const token = localStorage.getItem('token');
    if (token) {
      config.headers.Authorization = token;
    }
    return config;
  },
  (error) => {
    return Promise.reject(error);
  }
);

export default axiosClient;
