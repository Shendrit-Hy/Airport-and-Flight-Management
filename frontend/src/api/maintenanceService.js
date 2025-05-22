import { getTenantIdFromSubdomain } from '../utils/getTenantId';
import axiosInstance from './axiosInstance';

export const getMaintenances = () => axiosInstance.get('/api/maintenance');

export const createMaintenance = (data) =>
  axiosInstance.post('/api/maintenance', data);

export const deleteMaintenance = (id) => {
  const tenantId = getTenantIdFromSubdomain
  return axiosInstance.delete(`/api/maintenance/${id}`, {
    headers: {
      'X-Tenant-ID': tenantId
    }
  });
};
