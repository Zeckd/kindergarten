import React, { useEffect, useState } from 'react';
import paymentService from '../api/paymentService';

const Payments = () => {
  const [payments, setPayments] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [isEditing, setIsEditing] = useState(false);
  const [currentPayment, setCurrentPayment] = useState({ amount: '', paymentType: '' });

  useEffect(() => {
    fetchPayments();
  }, []);

  const fetchPayments = async () => {
    try {
      const response = await paymentService.getAll();
      setPayments(response.data);
      setLoading(false);
    } catch (err) {
      setError('Ошибка при загрузке платежей');
      setLoading(false);
    }
  };

  const handleDelete = async (id) => {
    if (window.confirm('Вы уверены, что хотите удалить этот платеж?')) {
      try {
        await paymentService.delete(id);
        fetchPayments();
      } catch (err) {
        alert('Ошибка при удалении');
      }
    }
  };

  const handleEdit = (payment) => {
    setCurrentPayment(payment);
    setIsEditing(true);
  };

  const handleCreate = () => {
    setCurrentPayment({ amount: '', paymentType: '' });
    setIsEditing(true);
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      if (currentPayment.id) {
        await paymentService.update(currentPayment);
      } else {
        await paymentService.create(currentPayment);
      }
      setIsEditing(false);
      fetchPayments();
    } catch (err) {
      alert('Ошибка при сохранении');
    }
  };

  const handleChange = (e) => {
    const { name, value } = e.target;
    setCurrentPayment({ ...currentPayment, [name]: value });
  };

  if (loading) return <div>Загрузка...</div>;
  if (error) return <div>{error}</div>;

  return (
    <div>
      <h1>Платежи</h1>
      <button onClick={handleCreate} style={{ marginBottom: '20px', padding: '10px', cursor: 'pointer' }}>
        Добавить платеж
      </button>

      {isEditing && (
        <div className="form-container" style={{ marginBottom: '20px', padding: '20px', border: '1px solid #ccc' }}>
          <h2>{currentPayment.id ? 'Редактировать' : 'Создать'} платеж</h2>
          <form onSubmit={handleSubmit}>
            <div style={{ marginBottom: '10px' }}>
              <label>Сумма: </label>
              <input
                type="number"
                name="amount"
                value={currentPayment.amount}
                onChange={handleChange}
                required
              />
            </div>
            <div style={{ marginBottom: '10px' }}>
              <label>Тип оплаты: </label>
              <input
                type="text"
                name="paymentType"
                value={currentPayment.paymentType}
                onChange={handleChange}
                required
              />
            </div>
            <button type="submit">Сохранить</button>
            <button type="button" onClick={() => setIsEditing(false)} style={{ marginLeft: '10px' }}>
              Отмена
            </button>
          </form>
        </div>
      )}

      <table border="1" cellPadding="10" style={{ width: '100%', borderCollapse: 'collapse' }}>
        <thead>
          <tr>
            <th>ID</th>
            <th>Сумма</th>
            <th>Тип оплаты</th>
            <th>Действия</th>
          </tr>
        </thead>
        <tbody>
          {payments.map((payment) => (
            <tr key={payment.id}>
              <td>{payment.id}</td>
              <td>{payment.amount}</td>
              <td>{payment.paymentType}</td>
              <td>
                <button onClick={() => handleEdit(payment)}>Редактировать</button>
                <button onClick={() => handleDelete(payment.id)} style={{ marginLeft: '10px', color: 'red' }}>
                  Удалить
                </button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
};

export default Payments;
