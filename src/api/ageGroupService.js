import axiosClient from './axiosClient';

const ageGroupService = {
  getAll: (page = 0, size = 10) => axiosClient.get('/age-group/get-list', { params: { page, size } }),
  getById: (id) => axiosClient.get('/age-group/find-by-id', { params: { id } }),
  create: (data) => axiosClient.post('/age-group/create', data),
  update: (data, deleteStatus = 'ACTIVE') => {
    const { id, ...body } = data;
    return axiosClient.put('/age-group/update', body, { params: { id, delete: deleteStatus } });
  },
  delete: (id) => axiosClient.delete('/age-group/delete', { params: { id } }),
};

export default ageGroupService;
