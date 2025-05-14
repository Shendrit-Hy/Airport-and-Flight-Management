import axiosInstance from './axiosInstance';

export const getBookings = () => axiosInstance.get('/api/bookings');
export const createBooking = (data) => axiosInstance.post('/api/bookings', data);
export const updateBooking = (id, data) => axiosInstance.put(`/api/bookings/${id}`, data);
export const deleteBooking = (id) => axiosInstance.delete(`/api/bookings/${id}`);