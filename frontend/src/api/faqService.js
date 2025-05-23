// src/services/faqService.js
import axios from 'axios';

const API_URL = 'http://localhost:8080/api/faqs';

/**
 * Get all FAQs for a tenant (public or authenticated).
 * @param {string} tenantId - Tenant ID for multi-tenancy.
 * @param {string} [token] - Optional JWT token for Authorization header.
 * @returns {Promise} Axios response promise.
 */
export const getFaqs = (tenantId, token) => {
  const headers = {
    'X-Tenant-ID': tenantId,
  };

  if (token) {
    headers.Authorization = `Bearer ${token}`;
  }

  return axios.get(API_URL, { headers });
};

/**
 * Save or update an FAQ (Admin only).
 * @param {string} tenantId - Tenant ID for multi-tenancy.
 * @param {Object} faqDTO - FAQ object to be saved.
 * @param {string} token - JWT token for Authorization header.
 * @returns {Promise} Axios response promise.
 */
export const saveFaq = (tenantId, faqDTO, token) => {
  return axios.post(API_URL, faqDTO, {
    headers: {
      Authorization: `Bearer ${token}`,
      'X-Tenant-ID': tenantId,
    },
  });
};

/**
 * Delete an FAQ by ID (Admin only).
 * @param {string} tenantId - Tenant ID for multi-tenancy.
 * @param {number} id - ID of the FAQ to delete.
 * @param {string} token - JWT token for Authorization header.
 * @returns {Promise} Axios response promise.
 */
export const deleteFaq = (tenantId, id, token) => {
  return axios.delete(`${API_URL}/${id}`, {
    headers: {
      Authorization: `Bearer ${token}`,
      'X-Tenant-ID': tenantId,
    },
  });
};
