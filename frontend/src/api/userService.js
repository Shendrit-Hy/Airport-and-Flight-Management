import axiosInstance from './axiosInstance';

// Merr të gjithë përdoruesit
export const getUsers = () => axiosInstance.get('/api/users');

// Merr profilin e përdoruesit të kyçur
export const getProfile = () => axiosInstance.get('/api/auth/profile');

// Regjistro një përdorues të ri me rol specifik
export const registerUser = (data, role) =>
  axiosInstance.post(`/api/auth/register?role=${role}`, data);
