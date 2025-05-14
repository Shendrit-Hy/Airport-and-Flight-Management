import axiosInstance from './axiosInstance';

export const getPayments = () => axiosInstance.get('/api/payments');
export const getPaymentById = (id) => axiosInstance.get(`/api/payments/${id}`);
export const makePayment = (data) => axiosInstance.post('/api/payments', data);