import axiosInstance from './axiosInstance';

export const getFlights = () => axiosInstance.get('/api/flights');
export const getFlightById = (id) => axiosInstance.get(`/api/flights/${id}`);
export const createFlight = (data) => axiosInstance.post('/api/flights', data);
export const updateFlight = (id, data) => axiosInstance.put(`/api/flights/${id}`, data);
export const deleteFlight = (id) => axiosInstance.delete(`/api/flights/${id}`);


// src/api/userService.js
import axiosInstance from './axiosInstance';

export const getUsers = () => axiosInstance.get('/api/users');
export const getProfile = () => axiosInstance.get('/api/auth/profile');
export const registerUser = (data, role) =>
  axiosInstance.post(`/api/auth/register?role=${role}`, data);