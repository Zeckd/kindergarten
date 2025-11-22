import React, { useEffect, useState } from 'react';
import childGroupHistoryService from '../api/childGroupHistoryService';

const History = () => {
  const [history, setHistory] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [isEditing, setIsEditing] = useState(false);
  const [currentRecord, setCurrentRecord] = useState({ childId: '', groupId: '', startDate: '', endDate: '' });

  useEffect(() => {
    fetchHistory();
  }, []);

  const fetchHistory = async () => {
    try {
      const response = await childGroupHistoryService.getAll();
      setHistory(response.data);
      setLoading(false);
    } catch (err) {
      setError('Ошибка при загрузке истории');
      setLoading(false);
    }
  };

  const handleDelete = async (id) => {
    if (window.confirm('Вы уверены, что хотите удалить эту запись?')) {
      try {
        await childGroupHistoryService.delete(id);
        fetchHistory();
      } catch (err) {
        alert('Ошибка при удалении');
      }
    }
  };

  const handleEdit = (record) => {
    setCurrentRecord(record);
    setIsEditing(true);
  };

  const handleCreate = () => {
    setCurrentRecord({ childId: '', groupId: '', startDate: '', endDate: '' });
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
      fetchHistory();
    } catch (err) {
      alert('Ошибка при сохранении');
    }
  };

  const handleChange = (e) => {
    const { name, value } = e.target;
    setCurrentRecord({ ...currentRecord, [name]: value });
  };

  if (loading) return <div>Загрузка...</div>;
  if (error) return <div>{error}</div>;

  return (
    <div>
      <h1>История групп</h1>
      <button onClick={handleCreate} style={{ marginBottom: '20px', padding: '10px', cursor: 'pointer' }}>
        Добавить запись
      </button>

      {isEditing && (
        <div className="form-container" style={{ marginBottom: '20px', padding: '20px', border: '1px solid #ccc' }}>
          <h2>{currentRecord.id ? 'Редактировать' : 'Создать'} запись</h2>
          <form onSubmit={handleSubmit}>
            <div style={{ marginBottom: '10px' }}>
              <label>ID Ребенка: </label>
              <input
                type="text"
                name="childId"
                value={currentRecord.childId}
                onChange={handleChange}
                required
              />
            </div>
            <div style={{ marginBottom: '10px' }}>
              <label>ID Группы: </label>
              <input
                type="text"
                name="groupId"
                value={currentRecord.groupId}
                onChange={handleChange}
                required
              />
            </div>
            <div style={{ marginBottom: '10px' }}>
              <label>Дата начала: </label>
              <input
                type="date"
                name="startDate"
                value={currentRecord.startDate}
                onChange={handleChange}
                required
              />
            </div>
            <div style={{ marginBottom: '10px' }}>
              <label>Дата окончания: </label>
              <input
                type="date"
                name="endDate"
                value={currentRecord.endDate}
                onChange={handleChange}
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
            <th>ID Ребенка</th>
            <th>ID Группы</th>
            <th>Дата начала</th>
            <th>Дата окончания</th>
            <th>Действия</th>
          </tr>
        </thead>
        <tbody>
          {history.map((record) => (
            <tr key={record.id}>
              <td>{record.id}</td>
              <td>{record.childId}</td>
              <td>{record.groupId}</td>
              <td>{record.startDate}</td>
              <td>{record.endDate}</td>
              <td>
                <button onClick={() => handleEdit(record)}>Редактировать</button>
                <button onClick={() => handleDelete(record.id)} style={{ marginLeft: '10px', color: 'red' }}>
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

export default History;
