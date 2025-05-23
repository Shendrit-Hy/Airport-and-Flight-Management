// src/services/cityService.js
import axios from 'axios';

const API_URL = 'http://localhost:8080/api/cities';

/**
 * Get all cities for the tenant.
 * @param {string} tenantId - Tenant ID for multi-tenancy.
 * @param {string} token - JWT token for Authorization header.
 * @returns {Promise} Axios response promise.
 */
export const getAllCities = (tenantId, token) => {
  return axios.get(API_URL, {
    headers: {
      Authorization: `Bearer ${token}`,
      'X-Tenant-ID': tenantId,
    },
  });
};

/**
 * Get cities by country ID.
 * @param {string} tenantId - Tenant ID for multi-tenancy.
 * @param {number} countryId - ID of the country.
 * @param {string} token - JWT token for Authorization header.
 * @returns {Promise} Axios response promise.
 */
export const getCitiesByCountry = (tenantId, countryId, token) => {
  return axios.get(`${API_URL}/country/${countryId}`, {
    headers: {
      Authorization: `Bearer ${token}`,
      'X-Tenant-ID': tenantId,
    },
  });
};

/**
 * Create a new city (Admin only).
 * @param {string} tenantId - Tenant ID for multi-tenancy.
 * @param {Object} cityDTO - Data for the city to create.
 * @param {string} token - JWT token for Authorization header.
 * @returns {Promise} Axios response promise.
 */
export const createCity = (tenantId, cityDTO, token) => {
  return axios.post(API_URL, cityDTO, {
    headers: {
      Authorization: `Bearer ${token}`,
      'X-Tenant-ID': tenantId,
    },
  });
};

/**
 * Delete a city by ID (Admin only).
 * @param {string} tenantId - Tenant ID for multi-tenancy.
 * @param {number} cityId - ID of the city to delete.
 * @param {string} token - JWT token for Authorization header.
 * @returns {Promise} Axios response promise.
 */
export const deleteCity = (tenantId, cityId, token) => {
  return axios.delete(`${API_URL}/${cityId}`, {
    headers: {
      Authorization: `Bearer ${token}`,
      'X-Tenant-ID': tenantId,
    },
  });
};
