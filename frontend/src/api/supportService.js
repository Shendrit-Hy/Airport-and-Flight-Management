import axiosInstance from './axiosInstance';


export const getSupportTickets = () =>
  axiosInstance.get('/api/support');


export const createSupportTicket = (data) =>
  axiosInstance.post('/api/support', data);


export const deleteSupportTicket = (id) =>
  axiosInstance.delete(`/api/support/${id}`);


export const sendSupportRequest = async (data, token, tenantId) => {
  return axiosInstance.post('/support', data, {
    headers: {
      Authorization: `Bearer ${token}`,
      'X-Tenant-ID': tenantId
    }
  });
};


export const getSupportRequests = async (token, tenantId) => {
  return axiosInstance.get('/support', {
    headers: {
      Authorization: `Bearer ${token}`,
      'X-Tenant-ID': tenantId
    }
  });
};
