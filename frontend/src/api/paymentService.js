// src/services/paymentService.js
import axios from 'axios';

const API_URL = 'http://localhost:8080/api/payments';

/**
 * Get all payments for the tenant (USER or ADMIN access).
 * @param {string} token - JWT token for authorization.
 * @param {string} tenantId - Tenant ID for the request header.
 * @returns {Promise} Axios response promise.
 */
export const getPayments = (token, tenantId) => {
  return axios.get(API_URL, {
    headers: {
      Authorization: `Bearer ${token}`,
      'X-Tenant-ID': tenantId,
    },
  });
};

/**
 * Save a new payment (ADMIN only).
 * @param {Object} paymentDTO - Payment data to be saved.
 * @param {string} token - JWT token for authorization.
 * @param {string} tenantId - Tenant ID for the request header.
 * @returns {Promise} Axios response promise.
 */
export const savePayment = (paymentDTO, token, tenantId) => {
  return axios.post(API_URL, paymentDTO, {
    headers: {
      Authorization: `Bearer ${token}`,
      'X-Tenant-ID': tenantId,
    },
  });
};

/**
 * Delete a payment by reference (ADMIN only).
 * @param {string} reference - Unique payment reference.
 * @param {string} token - JWT token for authorization.
 * @param {string} tenantId - Tenant ID for the request header.
 * @returns {Promise} Axios response promise.
 */
export const deletePayment = (reference, token, tenantId) => {
  return axios.delete(`${API_URL}/${reference}`, {
    headers: {
      Authorization: `Bearer ${token}`,
      'X-Tenant-ID': tenantId,
    },
  });
};
