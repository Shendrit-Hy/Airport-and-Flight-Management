// src/services/countryService.js
import axios from 'axios';

const API_URL = 'http://localhost:8080/api/countries';

/**
 * Get all countries for a tenant.
 * @param {string} tenantId - Tenant ID for multi-tenancy.
 * @param {string} token - JWT token for Authorization header.
 * @returns {Promise} Axios response promise.
 */
export const getAllCountries = (tenantId, token) => {
  return axios.get(API_URL, {
    headers: {
      Authorization: `Bearer ${token}`,
      'X-Tenant-ID': tenantId,
    },
  });
};

/**
 * Create a new country (Admin only).
 * @param {string} tenantId - Tenant ID for multi-tenancy.
 * @param {Object} countryDTO - Data for the country to create.
 * @param {string} token - JWT token for Authorization header.
 * @returns {Promise} Axios response promise.
 */
export const createCountry = (tenantId, countryDTO, token) => {
  return axios.post(API_URL, countryDTO, {
    headers: {
      Authorization: `Bearer ${token}`,
      'X-Tenant-ID': tenantId,
    },
  });
};

/**
 * Delete a country by its code (Admin only).
 * @param {string} tenantId - Tenant ID for multi-tenancy.
 * @param {string} id - Country id to delete.
 * @param {string} token - JWT token for Authorization header.
 * @returns {Promise} Axios response promise.
 */
export const deleteCountry = (tenantId, id, token) => {
  return axios.delete(`${API_URL}/${id}`, {
    headers: {
      Authorization: `Bearer ${token}`,
      'X-Tenant-ID': tenantId,
    },
  });
};
