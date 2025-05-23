// src/services/passengerService.js
import axios from 'axios';

const API_URL = 'http://localhost:8080/api/passengers';

/**
 * Get all passengers (ADMIN only).
 * @param {string} token - JWT token for Authorization header.
 * @param {string} tenantId - Tenant ID to be included in the request header (optional).
 * @returns {Promise} Axios response promise.
 */
export const getAllPassengers = (token, tenantId) => {
  return axios.get(API_URL, {
    headers: {
      Authorization: `Bearer ${token}`,
      ...(tenantId && { 'X-Tenant-ID': tenantId }),
    },
  });
};

/**
 * Update a passenger (ADMIN only).
 * @param {number} id - Passenger ID.
 * @param {Object} passengerDTO - Updated passenger data.
 * @param {string} token - JWT token for Authorization header.
 * @param {string} tenantId - Tenant ID to be included in the request header.
 * @returns {Promise} Axios response promise.
 */
export const updatePassenger = (id, passengerDTO, token, tenantId) => {
  return axios.put(`${API_URL}/${id}`, passengerDTO, {
    headers: {
      Authorization: `Bearer ${token}`,
      'X-Tenant-ID': tenantId,
    },
  });
};

/**
 * Create a new passenger (unauthenticated access allowed, tenant ID required in header).
 * @param {Object} passengerDTO - Passenger data to be created.
 * @param {string} tenantId - Tenant ID to be included in the request header.
 * @returns {Promise} Axios response promise.
 */
export const createPassenger = (passengerDTO, tenantId) => {
  return axios.post(API_URL, passengerDTO, {
    headers: {
      'X-Tenant-ID': tenantId,
    },
  });
};

/**
 * Delete a passenger (ADMIN only).
 * @param {number} id - Passenger ID.
 * @param {string} token - JWT token for Authorization header.
 * @param {string} tenantId - Tenant ID to be included in the request header.
 * @returns {Promise} Axios response promise.
 */
export const deletePassenger = (id, token, tenantId) => {
  return axios.delete(`${API_URL}/${id}`, {
    headers: {
      Authorization: `Bearer ${token}`,
      'X-Tenant-ID': tenantId,
    },
  });
};
