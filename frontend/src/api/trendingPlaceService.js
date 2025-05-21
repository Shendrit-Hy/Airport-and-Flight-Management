import axios from './axiosInstance';

const BASE_URL = '/api/trending-places';

export const getTrendingPlaces = () => {
  return axios.get(BASE_URL);
};

export const createTrendingPlace = (data) => {
  return axios.post(BASE_URL, data);
};
