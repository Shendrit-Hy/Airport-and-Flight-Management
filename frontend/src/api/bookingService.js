// src/services/bookingService.js
import axios from 'axios';

const API_URL = 'http://localhost:8080/api/bookings';

/**
 * Create a new booking (authenticated users only).
 * @param {Object} bookingDTO - Booking data to be submitted.
 * @param {string} token - JWT token for Authorization header.
 * @returns {Promise} Axios response promise.
 */
export const createBooking = (bookingDTO, token) => {
  return axios.post(API_URL, bookingDTO, {
    headers: {
      Authorization: `Bearer ${token}`,
    },
  });
};

/**
 * Get all bookings (admin only).
 * @param {string} token - JWT token for Authorization header.
 * @returns {Promise} Axios response promise.
 */
export const getAllBookings = (token) => {
  return axios.get(API_URL, {
    headers: {
      Authorization: `Bearer ${token}`,
    },
  });
};

/**
 * Get booking by ID (admin only).
 * @param {number} id - Booking ID.
 * @param {string} token - JWT token for Authorization header.
 * @returns {Promise} Axios response promise.
 */
export const getBookingById = (id, token) => {
  return axios.get(`${API_URL}/${id}`, {
    headers: {
      Authorization: `Bearer ${token}`,
    },
  });
};

/**
 * Update a booking (admin only).
 * @param {number} id - Booking ID.
 * @param {Object} bookingDTO - Updated booking data.
 * @param {string} token - JWT token for Authorization header.
 * @returns {Promise} Axios response promise.
 */
export const updateBooking = (id, bookingDTO, token) => {
  return axios.put(`${API_URL}/${id}`, bookingDTO, {
    headers: {
      Authorization: `Bearer ${token}`,
    },
  });
};

/**
 * Delete a booking by ID (admin only).
 * @param {number} id - Booking ID.
 * @param {string} token - JWT token for Authorization header.
 * @returns {Promise} Axios response promise.
 */
export const deleteBooking = (id, token) => {
  return axios.delete(`${API_URL}/${id}`, {
    headers: {
      Authorization: `Bearer ${token}`,
    },
  });
};

/**
 * Update check-in status (authenticated users).
 * @param {number} id - Booking ID.
 * @param {boolean} checkedIn - Check-in status to set (true or false).
 * @param {string} token - JWT token for Authorization header.
 * @returns {Promise} Axios response promise.
 */
export const updateCheckInStatus = (id, checkedIn, token) => {
  return axios.put(`${API_URL}/${id}/checkin?checkedIn=${checkedIn}`, {}, {
    headers: {
      Authorization: `Bearer ${token}`,
    },
  });
};