// src/services/gateService.js
import axios from 'axios';

const API_URL = 'http://localhost:8080/api/gates';

/**
 * Get all gates for a tenant.
 * @param {string} tenantId - Tenant ID from the X-Tenant-ID header.
 * @param {string} [token] - Optional JWT token for Authorization.
 * @returns {Promise} Axios response promise.
 */
export const getGates = async (tenantId, token) => {
  const headers = {
    'X-Tenant-ID': tenantId,
  };

  if (token) {
    headers.Authorization = `Bearer ${token}`;
  }

  const response = await axios.get(API_URL, { headers });
  return response.data; // This ensures only the gate array is returned
};

/**
 * Create a new gate (Admin only).
 * @param {string} tenantId - Tenant ID from the X-Tenant-ID header.
 * @param {Object} gateDTO - Gate data to be created.
 * @param {string} token - JWT token for Authorization header.
 * @returns {Promise} Axios response promise.
 */
export const createGate = (tenantId, gateDTO, token) => {
  return axios.post(API_URL, gateDTO, {
    headers: {
      Authorization: `Bearer ${token}`,
      'X-Tenant-ID': tenantId,
    },
  });
};

/**
 * Delete a gate by ID (Admin only).
 * @param {string} tenantId - Tenant ID from the X-Tenant-ID header.
 * @param {number} id - ID of the gate to delete.
 * @param {string} token - JWT token for Authorization header.
 * @returns {Promise} Axios response promise.
 */
export const deleteGate = (tenantId, id, token) => {
  return axios.delete(`${API_URL}/${id}`, {
    headers: {
      Authorization: `Bearer ${token}`,
      'X-Tenant-ID': tenantId,
    },
  });
};
