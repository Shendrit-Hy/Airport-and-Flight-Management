import axios from './axiosInstance';

export const sendSupportRequest = async (data, token, tenantId) => {
  return axios.post('/support', data, {
    headers: {
      Authorization: `Bearer ${token}`,
      'X-Tenant-ID': tenantId
    }
  });
};

export const getSupportRequests = async (token, tenantId) => {
  return axios.get('/support', {
    headers: {
      Authorization: `Bearer ${token}`,
      'X-Tenant-ID': tenantId
    }
  });
};
