import axios from 'axios';

const SUPPORT_API_BASE = 'http://localhost:8080/api/support';

// Dërgo kërkesë për suport si user (me X-Tenant-ID)
export const sendSupportRequest = async (data, tenantId) => {
  return axios.post(SUPPORT_API_BASE, data, {
    headers: {
      'X-Tenant-ID': tenantId,
      'Content-Type': 'application/json',
    },
  });
};

export const getSupportRequests = async (token, tenantId) => {
  return axios.get(SUPPORT_API_BASE, {
    headers: {
      Authorization: `Bearer ${token}`,
      'X-Tenant-ID': tenantId,
      'Content-Type': 'application/json',
    },
  });
};

export const deleteSupportRequest = async (id, token, tenantId) => {
  return axios.delete(`${SUPPORT_API_BASE}/${id}`, {
    headers: {
      Authorization: `Bearer ${token}`,
      'X-Tenant-ID': tenantId,
      'Content-Type': 'application/json',
    },
  });
};