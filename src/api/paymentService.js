import axiosClient from './axiosClient';

const paymentService = {
  getAll: (page = 0, size = 100) => axiosClient.get('/payment/get-list', { params: { page, size } }),
  getById: (id) => axiosClient.get('/payment/find-by-id', { params: { id } }),
  create: (paymentData, paymentType) => {
    const params = new URLSearchParams({ paymentType });
    return axiosClient.post('/payment/create?' + params.toString(), paymentData);
  },
  update: (id, paymentData, paymentType, deleteStatus = 'ACTIVE') => {
    const params = new URLSearchParams({ id, paymentType, delete: deleteStatus });
    return axiosClient.put('/payment/update?' + params.toString(), paymentData);
  },
  delete: (id) => axiosClient.delete('/payment/delete', { params: { id } }),
  getByChild: (childId) => axiosClient.get('/payment/by-child', { params: { childId } }),
  getSumByMonth: (childId, month, year) => axiosClient.get('/payment/sum-by-month', { params: { childId, month, year } }),
  getLastPayment: (childId) => axiosClient.get('/payment/last-payment', { params: { childId } }),
};

export default paymentService;
