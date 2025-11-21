import axiosClient from './axiosClient';

const parentService = {
  getAll: () => axiosClient.get('/parent/get-list'),
  getById: (id) => axiosClient.get('/parent/find-by-id', { params: { id } }),
  create: (parentData) => axiosClient.post('/parent/create', parentData),
  update: (parentData) => axiosClient.put('/parent/update', parentData),
  delete: (id) => axiosClient.delete('/parent/delete', { params: { id } }),
};

export default parentService;
