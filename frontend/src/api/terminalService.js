// src/services/terminalService.js
import axios from 'axios';

const API_URL = 'http://localhost:8080/api/terminals';

/**
 * Get all terminals for a specific tenant.
 * No authentication required, but X-Tenant-ID is mandatory.
 * 
 * @param {string} tenantId - Tenant identifier from subdomain or context
 * @returns {Promise} Axios response promise
 */
export const getAllTerminals = (tenantId) => {
  return axios.get(API_URL, {
    headers: {
      'X-Tenant-ID': tenantId,
      'Content-Type': 'application/json',
    },
  });
};

/**
 * Create a new terminal (ADMIN only).
 * Requires JWT and tenant validation.
 * 
 * @param {Object} terminalDTO - Terminal data object
 * @param {string} tenantId - Tenant identifier
 * @param {string} token - JWT access token
 * @returns {Promise} Axios response promise
 */
export const createTerminal = (terminalDTO, tenantId, token) => {
  return axios.post(API_URL, terminalDTO, {
    headers: {
      'X-Tenant-ID': tenantId,
      Authorization: `Bearer ${token}`,
      'Content-Type': 'application/json',
    },
  });
};
