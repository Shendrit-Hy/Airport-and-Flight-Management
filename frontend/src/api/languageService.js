import axios from 'axios';

const API_BASE_URL = 'http://localhost:8080/api/languages';

const getLanguages = async (tenantId) => {
  const response = await axios.get(API_BASE_URL, {
    headers: {
      'X-Tenant-ID': tenantId,
    },
  });
  return response.data;
};

const addLanguage = async (language, tenantId) => {
  const response = await axios.post(API_BASE_URL, language, {
    headers: {
      'X-Tenant-ID': tenantId,
    },
  });
  return response.data;
};

const deleteLanguage = async (id, tenantId) => {
  await axios.delete(`${API_BASE_URL}/${id}`, {
    headers: {
      'X-Tenant-ID': tenantId,
    },
  });
};

const languageService = {
  getLanguages,
  addLanguage,
  deleteLanguage,
};

export default languageService;
