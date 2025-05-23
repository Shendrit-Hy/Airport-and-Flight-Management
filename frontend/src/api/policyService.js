import axios from 'axios';

const API_URL = 'http://localhost:8080/api/policies';

/**
 * Fetch all policies for the given tenant.
 * Requires USER or ADMIN role.
 * 
 * @param {string} tenantId - Tenant identifier.
 * @returns {Promise} Axios response promise.
 */
export const getAllPolicies = async (tenantId) => {
  try {
    const response = await axios.get(API_URL, {
      headers: {
        'X-Tenant-ID': tenantId,
      },
    });
    return response.data; // ← This should be the array
  } catch (error) {
    console.error('Error fetching policies:', error);
    throw error;
  }
};


/**
 * Create a new policy.
 * Requires ADMIN role.
 * 
 * @param {Object} policyDTO - Policy data including title, content, type.
 * @param {string} token - JWT token for authentication.
 * @param {string} tenantId - Tenant identifier.
 * @returns {Promise} Axios response promise.
 */
export const createPolicy = (policyDTO, token, tenantId) => {
  return axios.post(API_URL, policyDTO, {
    headers: {
      Authorization: `Bearer ${token}`,
      'X-Tenant-ID': tenantId,
    },
  });
};

/**
 * Delete a policy by its ID.
 * Requires ADMIN role.
 * 
 * @param {number} id - ID of the policy to delete.
 * @param {string} token - JWT token for authentication.
 * @param {string} tenantId - Tenant identifier.
 * @returns {Promise} Axios response promise.
 */
export const deletePolicy = (id, token, tenantId) => {
  return axios.delete(`${API_URL}/${id}`, {
    headers: {
      Authorization: `Bearer ${token}`,
      'X-Tenant-ID': tenantId,
    },
  });
};
