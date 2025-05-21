import axios from "axios";
import { getTenantIdFromSubdomain } from "./getTenantId";

const API = 'http://localhost:8080/api/auth/profile'

export const getUserProfile = async (token) => {
  return axios.get(API, {
    headers: {
      Authorization: `Bearer ${token}`,
      'X-Tenant-ID': getTenantIdFromSubdomain(),
      'Content-Type': 'application/json',
    },
  });
};