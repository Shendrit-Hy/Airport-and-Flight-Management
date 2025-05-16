// services/airportService.js
import axios from 'axios';
import { getTenantIdFromSubdomain } from '../utils/getTenantId';

const API_BASE = 'http://localhost:8080/api/airports';

const getAuthHeaders = () => {
  const token = localStorage.getItem('token');
  const tenantId = getTenantIdFromSubdomain();
  return {
    headers: {
      Authorization: `Bearer ${token}`,
      'X-Tenant-ID': tenantId,
    },
  };
};

export const getAirports = () => {
  return axios.get(API_BASE, getAuthHeaders());
};

export const createAirport = (airportData) => {
  return axios.post(API_BASE, airportData, getAuthHeaders());
};

export const deleteAirport = (id) => {
  return axios.delete(`${API_BASE}/${id}`, getAuthHeaders());
};
