import axiosClient from './axiosClient';

const groupService = {
  getAll: (page = 0, size = 10) => axiosClient.get('/group/get-list', { params: { page, size } }),
  getById: (id) => axiosClient.get('/group/find-by-id', { params: { id } }),
  create: (groupData) => axiosClient.post('/group/create', groupData),
  update: (groupData, deleteStatus = 'ACTIVE') => {
    const { id, ...body } = groupData;
    return axiosClient.put('/group/update', body, { params: { id, delete: deleteStatus } });
  },
  delete: (id) => axiosClient.delete('/group/delete', { params: { id } }),
  addMember: (data) => axiosClient.put('/group/add-teacher-assistant-child', null, { params: data }),
  removeTeacher: (id) => axiosClient.put('/group/remove-teacher', null, { params: { id } }),
  removeAssistant: (id) => axiosClient.put('/group/remove-assistant', null, { params: { id } }),
  removeChild: (groupId, childId) => axiosClient.put('/group/remove-child', null, { params: { groupId, childId } }),
};

export default groupService;
