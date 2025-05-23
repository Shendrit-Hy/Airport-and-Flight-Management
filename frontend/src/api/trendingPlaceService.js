// src/services/trendingPlaceService.js
import axios from 'axios';

const API_BASE_URL = 'http://localhost:8080/api/trending-places';

/**
 * Fetch all trending places for a specific tenant.
 * @param {string} tenantId - The tenant ID to include in the request header.
 * @returns {Promise<Array>} - List of trending places.
 */
const getTrendingPlaces = async (tenantId) => {
  const response = await axios.get(API_BASE_URL, {
    headers: {
      'X-Tenant-ID': tenantId,
    },
  });
  return response.data;
};

/**
 * Create a new trending place.
 * @param {Object} trendingPlaceDTO - DTO containing trending place data.
 * @param {string} tenantId - The tenant ID to include in the request header.
 * @returns {Promise<Object>} - The created trending place.
 */
const createTrendingPlace = async (trendingPlaceDTO, tenantId) => {
  const response = await axios.post(API_BASE_URL, trendingPlaceDTO, {
    headers: {
      'X-Tenant-ID': tenantId,
    },
  });
  return response.data;
};

/**
 * Delete a trending place by ID.
 * @param {number} id - The ID of the trending place to delete.
 * @param {string} tenantId - The tenant ID to include in the request header.
 */
const deleteTrendingPlace = async (id, tenantId) => {
  await axios.delete(`${API_BASE_URL}/${id}`, {
    headers: {
      'X-Tenant-ID': tenantId,
    },
  });
};

const trendingPlaceService = {
  getTrendingPlaces,
  createTrendingPlace,
  deleteTrendingPlace,
};

export default trendingPlaceService;
