import axiosClient from './axiosClient';

const groupService = {
  getAll: () => axiosClient.get('/group/get-list'),
  getById: (id) => axiosClient.get('/group/find-by-id', { params: { id } }),
  create: (groupData) => axiosClient.post('/group/create', groupData),
  update: (groupData) => axiosClient.put('/group/update', groupData),
  delete: (id) => axiosClient.delete('/group/delete', { params: { id } }),
  addMember: (data) => axiosClient.put('/group/add-teacher-assistant-child', data),
};

export default groupService;
