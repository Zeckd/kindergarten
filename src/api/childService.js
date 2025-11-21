import axiosClient from './axiosClient';

const childService = {
  getAll: () => axiosClient.get('/child/get-list'),
  getById: (id) => axiosClient.get('/child/find-by-id', { params: { id } }),
  create: (childData) => axiosClient.post('/child/create', childData),
  update: (childData) => axiosClient.put('/child/update', childData),
  delete: (id) => axiosClient.delete('/child/delete', { params: { id } }),
};

export default childService;
