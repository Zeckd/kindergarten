import React, { useEffect, useState } from 'react';
import paymentService from '../api/paymentService';
import childService from '../api/childService';

const Payments = () => {
  const [payments, setPayments] = useState([]);
  const [childrenList, setChildrenList] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [isEditing, setIsEditing] = useState(false);
  const [showLastPaymentModal, setShowLastPaymentModal] = useState(false);
  const [selectedChildForLastPayment, setSelectedChildForLastPayment] = useState('');
  const [lastPaymentResult, setLastPaymentResult] = useState(null);
  const [currentPayment, setCurrentPayment] = useState({
    childId: '',
    paymentSum: '',
    period: '',
    paymentType: 'CASH'
  });

  useEffect(() => {
    fetchData();
  }, []);

  const fetchData = async () => {
    try {
      const [paymentsRes, childrenRes] = await Promise.all([
        paymentService.getAll(),
        childService.getAll(0, 100)
      ]);
      setPayments(paymentsRes.data);
      setChildrenList(childrenRes.data);
      setLoading(false);
    } catch (err) {
      setError('Ошибка при загрузке данных');
      setLoading(false);
    }
  };

  const handleDelete = async (id) => {
    if (window.confirm('Вы уверены, что хотите удалить этот платеж?')) {
      try {
        await paymentService.delete(id);
        fetchData();
      } catch (err) {
        alert('Ошибка при удалении');
      }
    }
  };

  const handleEdit = (payment) => {
    setCurrentPayment({
      id: payment.id,
      childId: payment.child ? payment.child.id : '',
      paymentSum: payment.paymentSum,
      period: payment.period,
      paymentType: payment.paymentType
    });
    setIsEditing(true);
  };

  const handleCreate = () => {
    setCurrentPayment({
      childId: '',
      paymentSum: '',
      period: '',
      paymentType: 'CASH'
    });
    setIsEditing(true);
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    const payload = {
      childId: currentPayment.childId,
      paymentSum: currentPayment.paymentSum,
      period: currentPayment.period
    };
    const paymentType = currentPayment.paymentType;

    try {
      if (currentPayment.id) {
        await paymentService.update(currentPayment.id, payload, paymentType);
      } else {
        await paymentService.create(payload, paymentType);
      }
      setIsEditing(false);
      fetchData();
    } catch (err) {
      alert('Ошибка при сохранении: ' + (err.response?.data?.message || err.message));
    }
  };

  const handleChange = (e) => {
    const { name, value } = e.target;
    setCurrentPayment({ ...currentPayment, [name]: value });
  };

  const handleLastPaymentSearch = async () => {
    if (!selectedChildForLastPayment) {
      alert('Выберите ребенка');
      return;
    }
    try {
      const res = await paymentService.getLastPayment(selectedChildForLastPayment);
      setLastPaymentResult(res.data);
    } catch (e) {
      alert('Платеж не найден или произошла ошибка');
      setLastPaymentResult(null);
    }
  };

  if (loading) return <div className="loading">Загрузка...</div>;
  if (error) return <div className="error">{error}</div>;

  return (
    <div>
      <div className="page-header">
        <h1>💵 Платежи</h1>
        <div style={{ display: 'flex', gap: '10px' }}>
          <button className="btn btn-primary" onClick={handleCreate}>
            + Добавить платеж
          </button>
          <button className="btn btn-secondary" onClick={() => {
            const childId = prompt('Введите ID ребенка');
            if(childId) {
              paymentService.getByChild(childId).then(res => {
                alert('Платежи: ' + JSON.stringify(res.data, null, 2));
              }).catch(e => alert('Ошибка'));
            }
          }}>
            По ребенку
          </button>
          <button className="btn btn-secondary" onClick={() => {
            setShowLastPaymentModal(true);
            setLastPaymentResult(null);
            setSelectedChildForLastPayment('');
          }}>
            Последний
          </button>
          <button className="btn btn-secondary" onClick={() => {
            const childId = prompt('Введите ID ребенка');
            const month = prompt('Введите месяц (1-12)');
            const year = prompt('Введите год');
            if(childId && month && year) {
              paymentService.getSumByMonth(childId, month, year).then(res => {
                alert('Сумма: ' + res.data);
              }).catch(e => alert('Ошибка'));
            }
          }}>
            Сумма за месяц
          </button>
        </div>
      </div>

      {showLastPaymentModal && (
        <div style={{
          position: 'fixed',
          top: 0,
          left: 0,
          right: 0,
          bottom: 0,
          backgroundColor: 'rgba(0,0,0,0.5)',
          display: 'flex',
          justifyContent: 'center',
          alignItems: 'center',
          zIndex: 1000
        }}>
          <div className="card" style={{ width: '500px', maxWidth: '90%' }}>
            <h2>Последний платеж</h2>
            <div className="form-group">
              <label>Выберите ребенка</label>
              <select
                className="form-control"
                value={selectedChildForLastPayment}
                onChange={(e) => setSelectedChildForLastPayment(e.target.value)}
              >
                <option value="">-- Выберите --</option>
                {childrenList.map(c => (
                  <option key={c.id} value={c.id}>
                    {c.firstName} {c.lastName}
                  </option>
                ))}
              </select>
            </div>
            <div className="table-actions" style={{ marginBottom: '20px' }}>
              <button className="btn btn-primary" onClick={handleLastPaymentSearch}>Найти</button>
              <button className="btn btn-secondary" onClick={() => setShowLastPaymentModal(false)}>Закрыть</button>
            </div>

            {lastPaymentResult && (
              <div style={{ background: '#f3f4f6', padding: '15px', borderRadius: '8px' }}>
                <div style={{ display: 'grid', gridTemplateColumns: '1fr 1fr', gap: '10px' }}>
                  <div><strong>ID Платежа:</strong></div>
                  <div>#{lastPaymentResult.id}</div>
                  
                  <div><strong>Сумма:</strong></div>
                  <div style={{ color: '#10b981', fontWeight: 'bold' }}>{lastPaymentResult.paymentSum} сом</div>
                  
                  <div><strong>Период:</strong></div>
                  <div>{lastPaymentResult.period}</div>
                  
                  <div><strong>Тип:</strong></div>
                  <div><span className="badge badge-success">{lastPaymentResult.paymentType}</span></div>
                  
                  <div><strong>Дата:</strong></div>
                  <div>{new Date(lastPaymentResult.paymentDate).toLocaleString('ru-RU')}</div>
                </div>
              </div>
            )}
          </div>
        </div>
      )}

      {isEditing && (
        <div className="card">
          <h2>{currentPayment.id ? 'Редактировать' : 'Добавить'} платеж</h2>
          <form onSubmit={handleSubmit}>
            <div className="form-row">
              <div className="form-group">
                <label>Ребенок</label>
                <select
                  className="form-control"
                  name="childId"
                  value={currentPayment.childId}
                  onChange={handleChange}
                  required
                >
                  <option value="">Выберите ребенка</option>
                  {childrenList.map(c => (
                    <option key={c.id} value={c.id}>
                      {c.firstName} {c.lastName}
                    </option>
                  ))}
                </select>
              </div>
              <div className="form-group">
                <label>Сумма</label>
                <input
                  className="form-control"
                  type="number"
                  name="paymentSum"
                  value={currentPayment.paymentSum}
                  onChange={handleChange}
                  required
                  placeholder="Введите сумму"
                />
              </div>
            </div>
            <div className="form-row">
              <div className="form-group">
                <label>Период (MM.yyyy)</label>
                <input
                  className="form-control"
                  type="text"
                  name="period"
                  value={currentPayment.period}
                  onChange={handleChange}
                  placeholder="10.2024"
                  required
                  pattern="(0[1-9]|1[0-2])\.\d{4}"
                />
              </div>
              <div className="form-group">
                <label>Тип оплаты</label>
                <select
                  className="form-control"
                  name="paymentType"
                  value={currentPayment.paymentType}
                  onChange={handleChange}
                  required
                >
                  <option value="CASH">Наличные</option>
                  <option value="CARD">Карта</option>
                  <option value="TRANSFER">Перевод</option>
                  <option value="QR">QR</option>
                </select>
              </div>
            </div>
            <div className="table-actions">
              <button type="submit" className="btn btn-success">Сохранить</button>
              <button type="button" className="btn btn-secondary" onClick={() => setIsEditing(false)}>
                Отмена
              </button>
            </div>
          </form>
        </div>
      )}

      <div className="table-container">
        <table className="styled-table">
          <thead>
            <tr>
              <th>ID</th>
              <th>Ребенок</th>
              <th>Сумма</th>
              <th>Период</th>
              <th>Тип оплаты</th>
              <th>Действия</th>
            </tr>
          </thead>
          <tbody>
            {payments.length === 0 ? (
              <tr>
                <td colSpan="6" className="empty-state">
                  <h3>Список пуст</h3>
                  <p>Добавьте первый платеж</p>
                </td>
              </tr>
            ) : (
              payments.map((payment) => (
                <tr key={payment.id}>
                  <td><span className="badge badge-primary">#{payment.id}</span></td>
                  <td>
                    {payment.child ? (
                      <div>
                        {payment.child.firstName} {payment.child.lastName}
                        <br/>
                        <span className="badge badge-info" style={{fontSize: '0.8em'}}>#{payment.child.id}</span>
                      </div>
                    ) : '-'}
                  </td>
                  <td><strong>{payment.paymentSum} сом</strong></td>
                  <td>{payment.period}</td>
                  <td><span className="badge badge-success">{payment.paymentType}</span></td>
                  <td>
                    <div className="table-actions">
                      <button className="btn btn-secondary btn-sm" onClick={() => handleEdit(payment)}>
                        Ред.
                      </button>
                      <button className="btn btn-danger btn-sm" onClick={() => handleDelete(payment.id)}>
                        Удалить
                      </button>
                    </div>
                  </td>
                </tr>
              ))
            )}
          </tbody>
        </table>
      </div>
    </div>
  );
};

export default Payments;
