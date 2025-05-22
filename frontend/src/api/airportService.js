import axios from 'axios';

const API_URL = 'http://localhost:8080/api/airports';

/**
 * Get all airports for a tenant (Admin only)
 * @param {string} tenantId
 * @param {string} token
 * @returns {Promise<Airport[]>}
 */
export const getAllAirports = (tenantId, token) => {
  return axios.get(API_URL, {
    headers: {
      'X-Tenant-ID': tenantId,
      Authorization: `Bearer ${token}`,
      'Content-Type': 'application/json',
    },
  });
};

/**
 * Create a new airport (No auth required but tenant must be set)
 * @param {object} airportData - { name, code, countryId, cityId, timezone }
 * @param {string} tenantId
 * @param {string} token
 * @returns {Promise<Airport>}
 */
export const createAirport = (airportData, tenantId, token) => {
  return axios.post(API_URL, airportData, {
    headers: {
      'X-Tenant-ID': tenantId,
      Authorization: `Bearer ${token}`,
      'Content-Type': 'application/json',
    },
  });
};

/**
 * Delete an airport by ID (Admin only)
 * @param {number} airportId
 * @param {string} tenantId
 * @param {string} token
 * @returns {Promise<void>}
 */
export const deleteAirport = (airportId, tenantId, token) => {
  return axios.delete(`${API_URL}/${airportId}`, {
    headers: {
      'X-Tenant-ID': tenantId,
      Authorization: `Bearer ${token}`,
      'Content-Type': 'application/json',
    },
  });
};
