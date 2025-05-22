import axios from 'axios';

const API_URL = 'http://localhost:8080/api/flights';

/**
 * Get today's and upcoming flights (Public - requires X-Tenant-ID)
 * @param {string} tenantId
 * @returns {Promise<FlightDTO[]>}
 */
export const getUpcomingFlights = (tenantId) => {
  return axios.get(API_URL, {
    headers: {
      'X-Tenant-ID': tenantId,
    },
  });
};

/**
 * Get all flights (Admin only - requires Bearer token)
 * @param {string} token
 * @returns {Promise<FlightDTO[]>}
 */
export const getAllFlights = (token) => {
  return axios.get(`${API_URL}/all`, {
    headers: {
      Authorization: `Bearer ${token}`,
      'Content-Type': 'application/json',
    },
  });
};

/**
 * Add a new flight (Admin only)
 * @param {object} flightData
 * @param {string} token
 * @returns {Promise<FlightDTO>}
 */
export const addFlight = (flightData, token) => {
  return axios.post(API_URL, flightData, {
    headers: {
      Authorization: `Bearer ${token}`,
      'Content-Type': 'application/json',
    },
  });
};

/**
 * Delete a flight by ID (Admin only)
 * @param {number} flightId
 * @param {string} token
 * @returns {Promise<void>}
 */
export const deleteFlight = (flightId, token) => {
  return axios.delete(`${API_URL}/${flightId}`, {
    headers: {
      Authorization: `Bearer ${token}`,
    },
  });
};

/**
 * Filter flights (Public - requires X-Tenant-ID)
 * @param {object} filterParams - { from, to, startDate, endDate, passengers }
 * @param {string} tenantId
 * @returns {Promise<Flight[]>}
 */
export const filterFlights = (filterParams, tenantId) => {
  const { from, to, startDate, endDate, passengers } = filterParams;

  return axios.get(`${API_URL}/filter`, {
    headers: {
      'X-Tenant-ID': tenantId,
    },
    params: {
      from,
      to,
      startDate,
      endDate,
      passengers,
    },
  });
};
