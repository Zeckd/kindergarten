import axiosClient from './axiosClient';

const ageGroupService = {
  getAll: () => axiosClient.get('/age-group/get-list'),
  getById: (id) => axiosClient.get('/age-group/find-by-id', { params: { id } }),
  create: (data) => axiosClient.post('/age-group/create', data),
  update: (data) => axiosClient.put('/age-group/update', data),
  delete: (id) => axiosClient.delete('/age-group/delete', { params: { id } }),
};

export default ageGroupService;
