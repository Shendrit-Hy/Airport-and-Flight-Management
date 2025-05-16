

import axiosInstance from './axiosInstance';


export const getSupportTickets = () =>
  axiosInstance.get('/api/support');


export const createSupportTicket = (data) =>
  axiosInstance.post('/api/support', data);


export const deleteSupportTicket = (id) =>
  axiosInstance.delete(`/api/support/${id}`);


export const sendSupportRequest = async (data, token, tenantId) => {
  return axiosInstance.post('/support', data, {
import axios from 'axios';

const SUPPORT_API_BASE = 'http://localhost:8080/api/support';

export const sendSupportRequest = async (data, tenantId) => {
  return axios.post('http://localhost:8080/api/support', data, {

    
    headers: {
      'X-Tenant-ID': tenantId,
      'Content-Type': 'application/json'
    }
  });
};


  
export const getSupportRequests = async (token, tenantId) => {
  return axiosInstance.get('/support', {

    

// Get all support requests (for admin)
export const getSupportRequests = async (token, tenantId) => {
  return axios.get(SUPPORT_API_BASE, {

    
    headers: {
      Authorization: `Bearer ${token}`,
      'Content-Type': 'application/json',
      'X-Tenant-ID': tenantId,
    },
  });
};
