import axiosClient from './axiosClient';

const childService = {
  getAll: (page = 0, size = 10) => axiosClient.get('/child/get-list', { params: { page, size } }),
  getById: (id) => axiosClient.get('/child/find-by-id', { params: { id } }),
  create: (childData) => axiosClient.post('/child/create', childData),
  update: (id, childData, deleteStatus = 'ACTIVE') => axiosClient.put('/child/update', childData, { params: { id, delete: deleteStatus } }),
  delete: (id) => axiosClient.delete('/child/delete', { params: { id } }),
};

export default childService;
