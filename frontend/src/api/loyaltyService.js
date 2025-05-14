
import axiosInstance from './axiosInstance';

export const getLoyaltyPrograms = () => axiosInstance.get('/api/loyalty');
export const createLoyaltyProgram = (data) => axiosInstance.post('/api/loyalty', data);