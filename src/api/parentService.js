import axiosClient from './axiosClient';

const parentService = {
  getAll: (page = 0, size = 10) => axiosClient.get('/parent/get-list', { params: { page, size } }),
  getById: (id) => axiosClient.get('/parent/find-by-id', { params: { id } }),
  create: (parentData, role) => axiosClient.post('/parent/create', parentData, { params: { status: role } }),
  update: (id, parentData, role, deleteStatus = 'ACTIVE') => axiosClient.put('/parent/update', parentData, { params: { id, status: role, delete: deleteStatus } }),
  delete: (id) => axiosClient.delete('/parent/delete', { params: { id } }),
};

export default parentService;
