import axios from 'axios';

const axiosInstance = axios.create({
  baseURL: 'http://localhost:8080',
});

axiosInstance.interceptors.request.use(
  (config) => {
    const token = localStorage.getItem('token');
    const tenantId = localStorage.getItem('tenantId');

    if (tenantId) {
      config.headers['X-Tenant-ID'] = tenantId;
      console.log("X-Tenant-ID set:", tenantId);
    } else {
      console.warn("tenantId mungon në localStorage");
    }

    if (token) {
      config.headers['Authorization'] = `Bearer ${token}`;
    }

    return config;
  },
  (error) => {
    return Promise.reject(error);
  }
);

export default axiosInstance;
