import axiosInstance from './axiosInstance';

export const getMaintenances = () => axiosInstance.get('/api/maintenance');

export const createMaintenance = (data) =>
  axiosInstance.post('/api/maintenance', data);
