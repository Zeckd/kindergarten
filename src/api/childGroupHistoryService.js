import axiosClient from './axiosClient';

const childGroupHistoryService = {
  getAll: (page = 0, size = 10) => axiosClient.get('/child-group-history/get-list', { params: { page, size } }),
  getById: (id) => axiosClient.get('/child-group-history/find-by-id', { params: { id } }),
  create: (data) => axiosClient.post('/child-group-history/create', data),
  update: (data, deleteStatus = 'ACTIVE') => {
    const { id, ...body } = data;
    return axiosClient.put('/child-group-history/update', body, { params: { id, delete: deleteStatus } });
  },
  delete: (id) => axiosClient.delete('/child-group-history/delete', { params: { id } }),
  getDebt: (childId) => axiosClient.get('/child-group-history/debt', { params: { childId } }),
  generateBill: (childId) => axiosClient.post('/child-group-history/bill', null, { params: { childId } }),
  getBillStatus: (billId) => axiosClient.get('/child-group-history/status', { params: { billId } }),
};

export default childGroupHistoryService;
