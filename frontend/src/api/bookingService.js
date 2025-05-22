import axios from 'axios';

const API_URL = 'http://localhost:8080/api/bookings';

/**
 * Create a new booking (Authenticated users)
 * @param {object} bookingData - { flightId, passengerId, seatNumber, ... }
 * @param {string} tenantId
 * @param {string} token
 * @returns {Promise<Booking>}
 */
export const createBooking = (bookingData, tenantId, token) => {
  return axios.post(API_URL, bookingData, {
    headers: {
      'X-Tenant-ID': tenantId,
      Authorization: `Bearer ${token}`,
      'Content-Type': 'application/json',
    },
  });
};

/**
 * Get all bookings (Admin only)
 * @param {string} tenantId
 * @param {string} token
 * @returns {Promise<Booking[]>}
 */
export const getAllBookings = (tenantId, token) => {
  return axios.get(API_URL, {
    headers: {
      'X-Tenant-ID': tenantId,
      Authorization: `Bearer ${token}`,
      'Content-Type': 'application/json',
    },
  });
};

/**
 * Get booking by ID (Admin only)
 * @param {number} bookingId
 * @param {string} tenantId
 * @param {string} token
 * @returns {Promise<Booking>}
 */
export const getBookingById = (bookingId, tenantId, token) => {
  return axios.get(`${API_URL}/${bookingId}`, {
    headers: {
      'X-Tenant-ID': tenantId,
      Authorization: `Bearer ${token}`,
      'Content-Type': 'application/json',
    },
  });
};

/**
 * Update a booking (Admin only)
 * @param {number} bookingId
 * @param {object} updatedData
 * @param {string} tenantId
 * @param {string} token
 * @returns {Promise<Booking>}
 */
export const updateBooking = (bookingId, updatedData, tenantId, token) => {
  return axios.put(`${API_URL}/${bookingId}`, updatedData, {
    headers: {
      'X-Tenant-ID': tenantId,
      Authorization: `Bearer ${token}`,
      'Content-Type': 'application/json',
    },
  });
};

/**
 * Delete a booking (Admin only)
 * @param {number} bookingId
 * @param {string} tenantId
 * @param {string} token
 * @returns {Promise<void>}
 */
export const deleteBooking = (bookingId, tenantId, token) => {
  return axios.delete(`${API_URL}/${bookingId}`, {
    headers: {
      'X-Tenant-ID': tenantId,
      Authorization: `Bearer ${token}`,
      'Content-Type': 'application/json',
    },
  });
};
