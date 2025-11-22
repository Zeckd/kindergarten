import React, { useEffect, useState } from 'react';
import paymentService from '../api/paymentService';
import childService from '../api/childService';
import groupService from '../api/groupService';
import childGroupHistoryService from '../api/childGroupHistoryService';

const Payments = () => {
  const [payments, setPayments] = useState([]);
  const [childrenList, setChildrenList] = useState([]);
  const [groupsList, setGroupsList] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [isEditing, setIsEditing] = useState(false);
  const [showLastPaymentModal, setShowLastPaymentModal] = useState(false);
  const [selectedChildForLastPayment, setSelectedChildForLastPayment] = useState('');
  const [lastPaymentResult, setLastPaymentResult] = useState(null);
  
  // QR Wizard State
  const [showQrWizard, setShowQrWizard] = useState(false);
  const [qrStep, setQrStep] = useState(1);
  const [qrData, setQrData] = useState({ groupId: '', childId: '', period: '' });
  const [qrBill, setQrBill] = useState(null);
  const [filteredChildren, setFilteredChildren] = useState([]);

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
      const [paymentsRes, childrenRes, groupsRes] = await Promise.all([
        paymentService.getAll(),
        childService.getAll(0, 100),
        groupService.getAll(0, 100)
      ]);
      setPayments(paymentsRes.data);
      setChildrenList(childrenRes.data);
      setGroupsList(groupsRes.data);
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

  // QR Wizard Handlers
  const openQrWizard = () => {
    setShowQrWizard(true);
    setQrStep(1);
    setQrData({ groupId: '', childId: '', period: '' });
    setQrBill(null);
  };

  const handleQrGroupSelect = async (groupId) => {
    setQrData({ ...qrData, groupId });
    try {
      const res = await childService.getByGroup(groupId);
      setFilteredChildren(res.data);
      setQrStep(2);
    } catch (e) {
      alert('Ошибка при загрузке детей группы');
    }
  };

  const handleQrChildSelect = (childId) => {
    setQrData({ ...qrData, childId });
    setQrStep(3);
  };

  const handleQrPeriodSubmit = async () => {
    if (!qrData.period) {
      alert('Введите период');
      return;
    }
    const periodRegex = /^\d{2}\.\d{4}$/;
    if (!periodRegex.test(qrData.period)) {
      alert('Неверный формат периода. Используйте MM.yyyy (например, 10.2025)');
      return;
    }
    try {
      const res = await childGroupHistoryService.generateBillForPeriod(qrData.childId, qrData.period);
      setQrBill(res.data);
      setQrStep(4);
    } catch (e) {
      alert('Ошибка при генерации QR: ' + (e.response?.data?.message || e.message));
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
          <button className="btn btn-success" onClick={openQrWizard}>
            QR на оплату
          </button>
        </div>
      </div>

      {showQrWizard && (
        <div style={{
          position: 'fixed', top: 0, left: 0, right: 0, bottom: 0,
          backgroundColor: 'rgba(0,0,0,0.5)', display: 'flex', justifyContent: 'center', alignItems: 'center', zIndex: 1000
        }}>
          <div className="card" style={{ width: '500px', maxWidth: '90%' }}>
            <h2>QR на оплату (Шаг {qrStep}/4)</h2>
            
            {qrStep === 1 && (
              <div>
                <h3>Выберите группу</h3>
                <div style={{ maxHeight: '300px', overflowY: 'auto' }}>
                  {groupsList.map(g => (
                    <button key={g.id} className="btn btn-secondary" style={{ display: 'block', width: '100%', marginBottom: '5px' }} onClick={() => handleQrGroupSelect(g.id)}>
                      {g.name}
                    </button>
                  ))}
                </div>
                <button className="btn btn-secondary" style={{ marginTop: '10px' }} onClick={() => setShowQrWizard(false)}>Отмена</button>
              </div>
            )}

            {qrStep === 2 && (
              <div>
                <h3>Выберите ребенка</h3>
                <div style={{ maxHeight: '300px', overflowY: 'auto' }}>
                  {filteredChildren.length === 0 ? <p>В группе нет детей</p> : 
                    filteredChildren.map(c => (
                      <button key={c.id} className="btn btn-secondary" style={{ display: 'block', width: '100%', marginBottom: '5px' }} onClick={() => handleQrChildSelect(c.id)}>
                        {c.firstName} {c.lastName}
                      </button>
                    ))
                  }
                </div>
                <button className="btn btn-secondary" style={{ marginTop: '10px' }} onClick={() => setQrStep(1)}>Назад</button>
              </div>
            )}

            {qrStep === 3 && (
              <div>
                <h3>Введите период</h3>
                <div className="form-group">
                  <label>Период (MM.yyyy)</label>
                  <input
                    className="form-control"
                    type="text"
                    value={qrData.period}
                    onChange={(e) => setQrData({ ...qrData, period: e.target.value })}
                    placeholder="10.2025"
                  />
                </div>
                <div className="table-actions">
                  <button className="btn btn-primary" onClick={handleQrPeriodSubmit}>Сгенерировать QR</button>
                  <button className="btn btn-secondary" onClick={() => setQrStep(2)}>Назад</button>
                </div>
              </div>
            )}

            {qrStep === 4 && qrBill && (
              <div style={{ textAlign: 'center' }}>
                <h3>Счет на оплату</h3>
                <div style={{ fontSize: '1.2rem', marginBottom: '10px' }}>
                  Сумма: <strong style={{ color: '#10b981' }}>{qrBill.amount} сом</strong>
                </div>
                {qrBill.qrCode && (
                  <img src={qrBill.qrCode} alt="QR" style={{ width: '200px', height: '200px', border: '1px solid #ddd', borderRadius: '8px' }} />
                )}
                <div style={{ marginTop: '15px' }}>
                  <a href={qrBill.paymentLink} target="_blank" rel="noopener noreferrer" className="btn btn-primary">Оплатить</a>
                </div>
                <button className="btn btn-secondary" style={{ marginTop: '20px' }} onClick={() => setShowQrWizard(false)}>Закрыть</button>
              </div>
            )}
          </div>
        </div>
      )}

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
