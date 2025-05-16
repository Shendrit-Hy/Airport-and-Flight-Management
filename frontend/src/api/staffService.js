import axios from './axiosInstance';

export const getStaffList = async () => {
  const res = await axios.get('/staff');
  return res.data;
};

export const deleteStaffById = async (id) => {
  await axios.delete(`/staff/${id}`);
};
export const addStaff = async (data) => {
  return await axios.post('/staff', data);
};
