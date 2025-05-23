// src/services/seatService.js
import axios from 'axios';

const API_URL = 'http://localhost:8080/api/seats';

/**
 * Get available (not booked) seats for a specific flight.
 * Public access, no token required.
 * @param {number} flightId - ID of the flight.
 * @param {string} tenantId - Tenant ID from header.
 * @returns {Promise} Axios response promise.
 */
export const getAvailableSeats = (flightId, tenantId) => {
  return axios.get(`${API_URL}/available/${flightId}`, {
    headers: {
      'X-Tenant-ID': tenantId,
    },
  });
};

/**
 * Get all seats (available and unavailable) for a specific flight.
 * Requires USER or ADMIN role.
 * @param {number} flightId - ID of the flight.
 * @param {string} token - JWT token for authentication.
 * @returns {Promise} Axios response promise.
 */
export const getAllSeats = (flightId, token) => {
  return axios.get(`${API_URL}/all/${flightId}`, {
    headers: {
      Authorization: `Bearer ${token}`,
    },
  });
};

/**
 * Mark a seat as unavailable (e.g. after booking).
 * Requires USER or ADMIN role.
 * @param {number} seatId - ID of the seat to update.
 * @param {string} token - JWT token for authentication.
 * @returns {Promise} Axios response promise.
 */
export const markSeatAsUnavailable = (seatId, token) => {
  return axios.put(`${API_URL}/${seatId}/unavailable`, null, {
    headers: {
      Authorization: `Bearer ${token}`,
    },
  });
};
