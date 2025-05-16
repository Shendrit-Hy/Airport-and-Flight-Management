import axiosInstance from './axiosInstance';

export const getAirports = () => axiosInstance.get('/api/airports');
export const createAirport = (data) => axiosInstance.post('/api/airports', data);
export const deleteAirport = (id) => axiosInstance.delete(`/api/airports/${id}`);
