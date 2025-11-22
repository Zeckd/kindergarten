import axiosClient from './axiosClient';

const teacherService = {
  getAll: (page = 0, size = 10) => axiosClient.get('/teacher/get-list', { params: { page, size } }),
  getById: (id) => axiosClient.get('/teacher/find-by-id', { params: { id } }),
  create: (teacherData, position) => axiosClient.post('/teacher/create', teacherData, { params: { status: position } }),
  update: (id, teacherData, position, deleteStatus = 'ACTIVE') => axiosClient.put('/teacher/update', teacherData, { params: { id, status: position, delete: deleteStatus } }),
  delete: (id) => axiosClient.delete('/teacher/delete', { params: { id } }),
};

export default teacherService;
