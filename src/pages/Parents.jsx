import React, { useEffect, useState } from 'react';
import parentService from '../api/parentService';

const Parents = () => {
  const [parents, setParents] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [isEditing, setIsEditing] = useState(false);
  const [currentParent, setCurrentParent] = useState({ firstName: '', lastName: '', role: '' });

  useEffect(() => {
    fetchParents();
  }, []);

  const fetchParents = async () => {
    try {
      const response = await parentService.getAll();
      setParents(response.data);
      setLoading(false);
    } catch (err) {
      setError('Ошибка при загрузке родителей');
      setLoading(false);
    }
  };

  const handleDelete = async (id) => {
    if (window.confirm('Вы уверены, что хотите удалить этого родителя?')) {
      try {
        await parentService.delete(id);
        fetchParents();
      } catch (err) {
        alert('Ошибка при удалении');
      }
    }
  };

  const handleEdit = (parent) => {
    setCurrentParent(parent);
    setIsEditing(true);
  };

  const handleCreate = () => {
    setCurrentParent({ firstName: '', lastName: '', role: '' });
    setIsEditing(true);
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      if (currentParent.id) {
        await parentService.update(currentParent);
      } else {
        await parentService.create(currentParent);
      }
      setIsEditing(false);
      fetchParents();
    } catch (err) {
      alert('Ошибка при сохранении');
    }
  };

  const handleChange = (e) => {
    const { name, value } = e.target;
    setCurrentParent({ ...currentParent, [name]: value });
  };

  if (loading) return <div>Загрузка...</div>;
  if (error) return <div>{error}</div>;

  return (
    <div>
      <h1>Родители</h1>
      <button onClick={handleCreate} style={{ marginBottom: '20px', padding: '10px', cursor: 'pointer' }}>
        Добавить родителя
      </button>

      {isEditing && (
        <div className="form-container" style={{ marginBottom: '20px', padding: '20px', border: '1px solid #ccc' }}>
          <h2>{currentParent.id ? 'Редактировать' : 'Создать'} родителя</h2>
          <form onSubmit={handleSubmit}>
            <div style={{ marginBottom: '10px' }}>
              <label>Имя: </label>
              <input
                type="text"
                name="firstName"
                value={currentParent.firstName}
                onChange={handleChange}
                required
              />
            </div>
            <div style={{ marginBottom: '10px' }}>
              <label>Фамилия: </label>
              <input
                type="text"
                name="lastName"
                value={currentParent.lastName}
                onChange={handleChange}
                required
              />
            </div>
            <div style={{ marginBottom: '10px' }}>
              <label>Роль: </label>
              <input
                type="text"
                name="role"
                value={currentParent.role}
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
            <th>Имя</th>
            <th>Фамилия</th>
            <th>Роль</th>
            <th>Действия</th>
          </tr>
        </thead>
        <tbody>
          {parents.map((parent) => (
            <tr key={parent.id}>
              <td>{parent.id}</td>
              <td>{parent.firstName}</td>
              <td>{parent.lastName}</td>
              <td>{parent.role}</td>
              <td>
                <button onClick={() => handleEdit(parent)}>Редактировать</button>
                <button onClick={() => handleDelete(parent.id)} style={{ marginLeft: '10px', color: 'red' }}>
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

export default Parents;
