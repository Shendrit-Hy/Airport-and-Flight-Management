import axios from 'axios';

const API_URL = 'http://localhost:8080/api/flights';

export const getFlights = (tenantId, token) => {
  return axios.get(API_URL, {
    headers: {
      'X-Tenant-ID': tenantId,
      Authorization: `Bearer ${token}`
    }
  });
};

export const createFlight = (flightData, tenantId, token) => {
  return axios.post(API_URL, flightData, {
    headers: {
      'X-Tenant-ID': tenantId,
      Authorization: `Bearer ${token}`
    }
  });
};

export const deleteFlight = (id, tenantId, token) => {
  return axios.delete(`${API_URL}/${id}`, {
    headers: {
      'X-Tenant-ID': tenantId,
      Authorization: `Bearer ${token}`
    }
  });
};
