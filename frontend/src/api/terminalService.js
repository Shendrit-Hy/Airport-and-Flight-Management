import axios from 'axios';

const API_URL = '/api/terminals';

/**
 * Fetch all terminals for the tenant.
 * @param {string} tenantId
 * @returns {Promise}
 */
export const getAllTerminals = (tenantId) => {
  return axios.get(API_URL, {
    headers: {
      'X-Tenant-ID': tenantId,
    },
  });
};

/**
 * Create a new terminal.
 * @param {{ terminalname: string, airportID: string }} terminalData
 * @param {string} tenantId
 * @param {string} token
 * @returns {Promise}
 */
export const createTerminal = (terminalData, tenantId, token) => {
  return axios.post(API_URL, terminalData, {
    headers: {
      'X-Tenant-ID': tenantId,
      'Authorization': `Bearer ${token}`,
      'Content-Type': 'application/json',
    },
  });
};

/**
 * Delete a terminal by ID.
 * @param {number} terminalId
 * @param {string} tenantId
 * @param {string} token
 * @returns {Promise}
 */
export const deleteTerminal = (terminalId, tenantId, token) => {
  return axios.delete(`${API_URL}/${terminalId}`, {
    headers: {
      'X-Tenant-ID': tenantId,
      'Authorization': `Bearer ${token}`,
    },
  });
};
