// src/services/airlineService.js
import axios from 'axios';

const API_URL = 'http://localhost:8080/api/airlines';

/**
 * Get all airlines for a specific tenant.
 *
 * @param {string} tenantId - Tenant identifier
 * @returns {Promise} Axios response promise
 */
export const getAllAirlines = (tenantId) => {
  return axios.get(API_URL, {
    headers: {
      'X-Tenant-ID': tenantId,
      'Content-Type': 'application/json',
    },
  });
};

/**
 * Create a new airline (ADMIN only).
 *
 * @param {Object} airlineDTO - Airline data object
 * @param {string} tenantId - Tenant identifier
 * @param {string} token - JWT access token
 * @returns {Promise} Axios response promise
 */
export const createAirline = (airlineDTO, tenantId, token) => {
  return axios.post(API_URL, airlineDTO, {
    headers: {
      'X-Tenant-ID': tenantId,
      Authorization: `Bearer ${token}`,
      'Content-Type': 'application/json',
    },
  });
};

/**
 * Delete an airline by ID (ADMIN only).
 *
 * @param {string} airlineId - The ID of the airline to delete
 * @param {string} tenantId - Tenant identifier
 * @param {string} token - JWT access token
 * @returns {Promise} Axios response promise
 */
export const deleteAirline = (airlineId, tenantId, token) => {
  return axios.delete(`${API_URL}/${airlineId}`, {
    headers: {
      'X-Tenant-ID': tenantId,
      Authorization: `Bearer ${token}`,
      'Content-Type': 'application/json',
    },
  });
};
