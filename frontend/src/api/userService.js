import axios from 'axios';

const API_URL = 'https://localhost:8080/api/auth/profile';

export const getUserProfile = (tenantId, token) => {
  return axios.get(`${API_URL}/me`, {
    headers: {
      'X-Tenant-ID': tenantId,
      Authorization: `Bearer ${token}`,
      'Content-Type': 'application/json',
    },
  });
};

export const getUserFlights = (tenantId, token) => {
  return axios.get(`${API_URL}/flights`, {
    headers: {
      'X-Tenant-ID': tenantId,
      Authorization: `Bearer ${token}`,
      'Content-Type': 'application/json',
    },
  });
};

export const cancelFlight = (flightId, tenantId, token) => {
  return axios.post(
    `${API_URL}/flights/${flightId}/cancel`,
    {},
    {
      headers: {
        'X-Tenant-ID': tenantId,
        Authorization: `Bearer ${token}`,
        'Content-Type': 'application/json',
      },
    }
  );
};

export const checkInFlight = (flightId, tenantId, token) => {
  return axios.post(
    `${API_URL}/flights/${flightId}/checkin`,
    {},
    {
      headers: {
        'X-Tenant-ID': tenantId,
        Authorization: `Bearer ${token}`,
        'Content-Type': 'application/json',
      },
    }
  );
};
