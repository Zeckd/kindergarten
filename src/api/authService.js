import axiosClient from './axiosClient';

const authService = {
  register: (userData) => {
    return axiosClient.post('/auth/register', userData);
  },
  setRole: (userId, role) => {
    return axiosClient.put('/auth/admin/set-role', null, { params: { userId, role } });
  },
  getAllUsers: () => {
    return axiosClient.get('/auth/admin/getAll');
  },
  login: () => {
    return axiosClient.get('/auth/me'); 
  }
};

export default authService;
