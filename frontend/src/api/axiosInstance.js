import axios from 'axios';

const axiosInstance = axios.create({
  baseURL: 'http://localhost:8080', // Ndrysho sipas backend-it tënd
});

axiosInstance.interceptors.request.use((config) => {
  const token = localStorage.getItem('token');
  const tenantId = localStorage.getItem('tenantId');

  if (token) {
    config.headers['Authorization'] = `Bearer ${token}`;
  }

  if (tenantId) {
    config.headers['X-Tenant-ID'] = tenantId;
  }

  return config;
});

export default axiosInstance;
