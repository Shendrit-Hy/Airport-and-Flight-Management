import axios from 'axios';

const API_URL = 'http://localhost:8080/api/staff';

export const getStaffList = (tenantId, token) => {
  return axios.get(`${API_URL}/all`, {
    headers: {
      'X-Tenant-ID': tenantId,
      Authorization: `Bearer ${token}`,
      'Content-Type': 'application/json',
    },
  });
};

export const deleteStaffById = (id, tenantId, token) => {
  return axios.delete(`${API_URL}/${id}`, {
    headers: {
      'X-Tenant-ID': tenantId,
      Authorization: `Bearer ${token}`,
      'Content-Type': 'application/json',
    },
  });
};

export const addStaff = (staffData, tenantId, token) => {
  const body = {
    name: staffData.name,
    role: staffData.role,
    email: staffData.email,
    shiftStart: staffData.shiftStart,
    shiftEnd: staffData.shiftEnd,
    tenantId: tenantId
  };

  return axios.post(API_URL, body, {
    headers: {
      'X-Tenant-ID': tenantId,
      Authorization: `Bearer ${token}`,
      'Content-Type': 'application/json',
    },
  });
};
