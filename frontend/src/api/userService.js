import axios from 'axios';

const API_URL = 'https://localhost:8080/api/auth/profile';

// Merr profilin e përdoruesit
export const getUserProfile = (tenantId, token) => {
  return axios.get(API_URL, {
    headers: {
      'X-Tenant-ID': tenantId,
      Authorization: `Bearer ${token}`,
      'Content-Type': 'application/json',
    },
  });
};

// Përditëso profilin e përdoruesit
export const updateUserProfile = (updatedData, tenantId, token) => {
  return axios.put(API_URL, updatedData, {
    headers: {
      'X-Tenant-ID': tenantId,
      Authorization: `Bearer ${token}`,
      'Content-Type': 'application/json',
    },
  });
};

// Merr fluturimet e përdoruesit
export const getUserFlights = (tenantId, token) => {
  return axios.get(`${API_URL}/flights`, {
    headers: {
      'X-Tenant-ID': tenantId,
      Authorization: `Bearer ${token}`,
      'Content-Type': 'application/json',
    },
  });
};

// Anulo një fluturim
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

// Check-in në një fluturim
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
