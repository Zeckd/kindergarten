import axiosClient from './axiosClient';

const childGroupHistoryService = {
  getAll: () => axiosClient.get('/child-group-history/get-list'),
  getById: (id) => axiosClient.get('/child-group-history/find-by-id', { params: { id } }),
  create: (data) => axiosClient.post('/child-group-history/create', data),
  update: (data) => axiosClient.put('/child-group-history/update', data),
  delete: (id) => axiosClient.delete('/child-group-history/delete', { params: { id } }),
  getDebt: (childId) => axiosClient.get('/child-group-history/debt', { params: { childId } }),
};

export default childGroupHistoryService;
