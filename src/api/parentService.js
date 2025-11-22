import axiosClient from './axiosClient';

const parentService = {
  getAll: (page = 0, size = 10) => axiosClient.get('/parent/get-list', { params: { page, size } }),
  getById: (id) => axiosClient.get('/parent/find-by-id', { params: { id } }),
  create: (parentData, role) => axiosClient.post('/parent/create', parentData, { params: { role } }),
  update: (id, parentData, role, deleteStatus = 'ACTIVE') => axiosClient.put('/parent/update', parentData, { params: { id, role, delete: deleteStatus } }),
  delete: (id) => axiosClient.delete('/parent/delete', { params: { id } }),
  getChildren: (parentId) => axiosClient.get('/parent/children', { params: { parentId } }),
};

export default parentService;
