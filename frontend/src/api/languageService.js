import axiosInstance from '../utils/axiosInstance';

/**
 * Merr të gjitha gjuhët për tenant-in aktiv.
 * GET /api/languages
 */
export const getLanguages = async () => {
  try {
    const response = await axiosInstance.get('/api/languages');
    return response.data;
  } catch (error) {
    console.error('❌ Error loading languages:', error);
    throw error;
  }
};

/**
 * Shton një gjuhë të re për tenantin e kyçur.
 * POST /api/languages
 * @param {Object} data - { name: string, code: string }
 * @param {string} tenantId - ID i tenantit për header
 */
export const createLanguage = async (data, tenantId) => {
  try {
    const response = await axiosInstance.post('/api/languages', data, {
      headers: {
        'X-Tenant-ID': tenantId,
      },
    });
    return response.data;
  } catch (error) {
    console.error('❌ Error creating language:', error);
    throw error;
  }
};

/**
 * Fshin një gjuhë sipas ID për tenantin e kyçur.
 * DELETE /api/languages/{id}
 * @param {number} id - ID e gjuhës
 * @param {string} tenantId - ID i tenantit për header
 */
export const deleteLanguage = async (id, tenantId) => {
  try {
    await axiosInstance.delete(`/api/languages/${id}`, {
      headers: {
        'X-Tenant-ID': tenantId,
      },
    });
  } catch (error) {
    console.error('❌ Error deleting language:', error);
    throw error;
  }
};
