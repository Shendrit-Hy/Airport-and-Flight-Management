import axios from 'axios';

const API_URL = 'http://localhost:8080/api/auth';

export const login = (username, password, tenantId) => {
  return axios.post(
    `${API_URL}/login`,
    { username, password },
    { headers: { 'X-Tenant-ID': tenantId } }
  );
};

export const register = (username, email, password, fullname, country, tenantId) => {
  return axios.post(
    `${API_URL}/register`,
    { username, email, password, fullname, country },
    { headers: { 'X-Tenant-ID': tenantId } }  // No role parameter here
  );
};

export const getProfile = (tenantId, token) => {
  return axios.get(`${API_URL}/profile`, {
    headers: {
      'X-Tenant-ID': tenantId,
      Authorization: `Bearer ${token}`
    }
  });
};

export const getFlights = (tenantId) => {
  return axios.get('http://localhost:8080/api/flights', {
    headers: {
      'X-Tenant-ID': tenantId
    }
  });
};