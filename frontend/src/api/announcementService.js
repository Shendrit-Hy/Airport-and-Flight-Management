import axiosInstance from './axiosInstance';

export const getAnnouncements = () => axiosInstance.get('/api/announcements');
export const createAnnouncement = (data) => axiosInstance.post('/api/announcements', data);