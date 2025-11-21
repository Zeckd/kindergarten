import axiosClient from './axiosClient';

const teacherService = {
  getAll: () => axiosClient.get('/teacher/get-list'),
  getById: (id) => axiosClient.get('/teacher/find-by-id', { params: { id } }),
  create: (teacherData) => axiosClient.post('/teacher/create', teacherData),
  update: (teacherData) => axiosClient.put('/teacher/update', teacherData),
  delete: (id) => axiosClient.delete('/teacher/delete', { params: { id } }),
};

export default teacherService;
