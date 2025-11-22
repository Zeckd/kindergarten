import axiosClient from './axiosClient';

const paymentService = {
  getAll: () => axiosClient.get('/payment/get-list'),
  getById: (id) => axiosClient.get('/payment/find-by-id', { params: { id } }),
  create: (paymentData) => axiosClient.post('/payment/create', paymentData),
  update: (paymentData) => axiosClient.put('/payment/update', paymentData),
  delete: (id) => axiosClient.delete('/payment/delete', { params: { id } }),
};

export default paymentService;
