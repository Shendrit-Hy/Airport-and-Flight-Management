import axios from 'axios';
import axiosInstance from './axiosInstance';

const SUPPORT_API_BASE = 'http://localhost:8080/api/support';

/* -------------------- ADMIN -------------------- */

// Merr të gjitha kërkesat e suportit për adminin
export const getSupportTickets = () => axiosInstance.get('/api/support');

// Krijo kërkesë suporti si admin
export const createSupportTicket = (data) => axiosInstance.post('/api/support', data);

// Fshij kërkesë suporti si admin
export const deleteSupportTicket = (id) => axiosInstance.delete(`/api/support/${id}`);

/* -------------------- USER -------------------- */

// Dërgo kërkesë për suport si user (me X-Tenant-ID)
export const sendSupportRequest = async (data, tenantId) => {
  return axios.post(SUPPORT_API_BASE, data, {
    headers: {
      'X-Tenant-ID': tenantId,
      'Content-Type': 'application/json',
    },
  });
};

// Merr kërkesat e userit me autorizim
export const getSupportRequests = async (token, tenantId) => {
  return axios.get(SUPPORT_API_BASE, {
    headers: {
      Authorization: `Bearer ${token}`,
      'X-Tenant-ID': tenantId,
      'Content-Type': 'application/json',
    },
  });
};
