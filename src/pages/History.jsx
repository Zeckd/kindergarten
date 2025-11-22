import React, { useEffect, useState } from 'react';
import childGroupHistoryService from '../api/childGroupHistoryService';
import childService from '../api/childService';
import groupService from '../api/groupService';

const History = () => {
  const [history, setHistory] = useState([]);
  const [childrenList, setChildrenList] = useState([]);
  const [groupsList, setGroupsList] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [isEditing, setIsEditing] = useState(false);
  const [currentRecord, setCurrentRecord] = useState({ childId: '', groupId: '' });
  const [billData, setBillData] = useState(null);
  const [showBillModal, setShowBillModal] = useState(false);

  useEffect(() => {
    fetchData();
  }, []);

  const fetchData = async () => {
    try {
      const [historyRes, childrenRes, groupsRes] = await Promise.all([
        childGroupHistoryService.getAll(),
        childService.getAll(0, 100),
        groupService.getAll(0, 100)
      ]);
      setHistory(historyRes.data);
      setChildrenList(childrenRes.data);
      setGroupsList(groupsRes.data);
      setLoading(false);
    } catch (err) {
      setError('Ошибка при загрузке данных');
      setLoading(false);
    }
  };

  const handleDelete = async (id) => {
    if (window.confirm('Вы уверены, что хотите удалить эту запись?')) {
      try {
        await childGroupHistoryService.delete(id);
        fetchData();
      } catch (err) {
        alert('Ошибка при удалении');
      }
    }
  };

  const handleEdit = (record) => {
    setCurrentRecord({
      id: record.id,
      childId: record.child ? record.child.id : '',
      groupId: record.group ? record.group.id : ''
    });
    setIsEditing(true);
  };

  const handleCreate = () => {
    setCurrentRecord({ childId: '', groupId: '' });
    setIsEditing(true);
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      if (currentRecord.id) {
        await childGroupHistoryService.update(currentRecord);
      } else {
        await childGroupHistoryService.create(currentRecord);
      }
      setIsEditing(false);
      fetchData();
    } catch (err) {
      alert('Ошибка при сохранении: ' + (err.response?.data?.message || err.message));
    }
  };

  const handleChange = (e) => {
    const { name, value } = e.target;
    setCurrentRecord({ ...currentRecord, [name]: value });
  };

  if (loading) return <div className="loading">Загрузка...</div>;
  if (error) return <div className="error">{error}</div>;

  return (
    <div>
      <div className="page-header">
        <h1>📊 История групп</h1>
        <button className="btn btn-primary" onClick={handleCreate}>
          + Добавить запись
        </button>
      </div>

      {showBillModal && billData && (
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
          <div className="card" style={{ width: '400px', maxWidth: '90%', textAlign: 'center' }}>
            <h2>🧾 Счет на оплату</h2>
            <div style={{ margin: '20px 0' }}>
              <div style={{ fontSize: '1.2rem', marginBottom: '10px' }}>
                Сумма к оплате: <strong style={{ color: '#10b981' }}>{billData.amount} сом</strong>
              </div>
              
              {billData.qrCode && (
                <div style={{ margin: '20px 0' }}>
                  <img 
                    src={billData.qrCode} 
                    alt="QR Code" 
                    style={{ width: '200px', height: '200px', border: '1px solid #ddd', borderRadius: '8px' }}
                  />
                  <p style={{ fontSize: '0.9rem', color: '#666', marginTop: '5px' }}>Сканируйте для оплаты</p>
                </div>
              )}

              <div style={{ marginTop: '15px' }}>
                <a href={billData.paymentLink} target="_blank" rel="noopener noreferrer" className="btn btn-primary" style={{ display: 'inline-block', textDecoration: 'none' }}>
                  Перейти к оплате
                </a>
              </div>
            </div>
            <button className="btn btn-secondary" onClick={() => setShowBillModal(false)}>Закрыть</button>
          </div>
        </div>
      )}

      {isEditing && (
        <div className="card">
          <h2>{currentRecord.id ? 'Редактировать' : 'Добавить'} запись</h2>
          <form onSubmit={handleSubmit}>
            <div className="form-row">
              <div className="form-group">
                <label>Ребенок</label>
                <select
                  className="form-control"
                  name="childId"
                  value={currentRecord.childId}
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
                <label>Группа</label>
                <select
                  className="form-control"
                  name="groupId"
                  value={currentRecord.groupId}
                  onChange={handleChange}
                  required
                >
                  <option value="">Выберите группу</option>
                  {groupsList.map(g => (
                    <option key={g.id} value={g.id}>{g.name}</option>
                  ))}
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
              <th>Группа</th>
              <th>Дата начала</th>
              <th>Дата окончания</th>
              <th>Действия</th>
            </tr>
          </thead>
          <tbody>
            {history.length === 0 ? (
              <tr>
                <td colSpan="6" className="empty-state">
                  <h3>Список пуст</h3>
                  <p>Добавьте первую запись</p>
                </td>
              </tr>
            ) : (
              history.map((record) => (
                <tr key={record.id}>
                  <td><span className="badge badge-primary">#{record.id}</span></td>
                  <td>
                    {record.child ? (
                      <div>
                        {record.child.firstName} {record.child.lastName}
                        <br/>
                        <span className="badge badge-info" style={{fontSize: '0.8em'}}>#{record.child.id}</span>
                      </div>
                    ) : '-'}
                  </td>
                  <td>
                    {record.group ? (
                      <div>
                        {record.group.name}
                        <br/>
                        <span className="badge badge-info" style={{fontSize: '0.8em'}}>#{record.group.id}</span>
                      </div>
                    ) : '-'}
                  </td>
                  <td>{record.startDate}</td>
                  <td>{record.endDate || '—'}</td>
                  <td>
                    <div className="table-actions">
                      <button className="btn btn-secondary btn-sm" onClick={() => handleEdit(record)}>
                        Ред.
                      </button>
                      <button className="btn btn-warning btn-sm" onClick={async () => {
                        try {
                          const res = await childGroupHistoryService.getDebt(record.child ? record.child.id : 0);
                          alert('Задолженность: ' + JSON.stringify(res.data, null, 2));
                        } catch (e) {
                          alert('Ошибка при получении задолженности');
                        }
                      }}>
                        Долг
                      </button>
                      <button className="btn btn-success btn-sm" onClick={async () => {
                        try {
                          const res = await childGroupHistoryService.generateBill(record.child ? record.child.id : 0);
                          setBillData(res.data);
                          setShowBillModal(true);
                        } catch (e) {
                          alert('Ошибка при создании счета: ' + (e.response?.data?.message || e.message));
                        }
                      }}>
                        Счет
                      </button>
                      <button className="btn btn-danger btn-sm" onClick={() => handleDelete(record.id)}>
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

export default History;
