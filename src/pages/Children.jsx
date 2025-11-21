import React, { useEffect, useState } from 'react';
import childService from '../api/childService';

const Children = () => {
  const [children, setChildren] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [isEditing, setIsEditing] = useState(false);
  const [currentChild, setCurrentChild] = useState({ firstName: '', lastName: '' });

  useEffect(() => {
    fetchChildren();
  }, []);

  const fetchChildren = async () => {
    try {
      const response = await childService.getAll();
      setChildren(response.data);
      setLoading(false);
    } catch (err) {
      setError('Ошибка при загрузке детей');
      setLoading(false);
    }
  };

  const handleDelete = async (id) => {
    if (window.confirm('Вы уверены, что хотите удалить этого ребенка?')) {
      try {
        await childService.delete(id);
        fetchChildren();
      } catch (err) {
        alert('Ошибка при удалении');
      }
    }
  };

  const handleEdit = (child) => {
    setCurrentChild(child);
    setIsEditing(true);
  };

  const handleCreate = () => {
    setCurrentChild({ firstName: '', lastName: '' });
    setIsEditing(true);
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      if (currentChild.id) {
        await childService.update(currentChild);
      } else {
        await childService.create(currentChild);
      }
      setIsEditing(false);
      fetchChildren();
    } catch (err) {
      alert('Ошибка при сохранении');
    }
  };

  const handleChange = (e) => {
    const { name, value } = e.target;
    setCurrentChild({ ...currentChild, [name]: value });
  };

  if (loading) return <div>Загрузка...</div>;
  if (error) return <div>{error}</div>;

  return (
    <div>
      <h1>Дети</h1>
      <button onClick={handleCreate} style={{ marginBottom: '20px', padding: '10px', cursor: 'pointer' }}>
        Добавить ребенка
      </button>

      {isEditing && (
        <div className="form-container" style={{ marginBottom: '20px', padding: '20px', border: '1px solid #ccc' }}>
          <h2>{currentChild.id ? 'Редактировать' : 'Создать'} ребенка</h2>
          <form onSubmit={handleSubmit}>
            <div style={{ marginBottom: '10px' }}>
              <label>Имя: </label>
              <input
                type="text"
                name="firstName"
                value={currentChild.firstName}
                onChange={handleChange}
                required
              />
            </div>
            <div style={{ marginBottom: '10px' }}>
              <label>Фамилия: </label>
              <input
                type="text"
                name="lastName"
                value={currentChild.lastName}
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
            <th>Действия</th>
          </tr>
        </thead>
        <tbody>
          {children.map((child) => (
            <tr key={child.id}>
              <td>{child.id}</td>
              <td>{child.firstName}</td>
              <td>{child.lastName}</td>
              <td>
                <button onClick={() => handleEdit(child)}>Редактировать</button>
                <button onClick={() => handleDelete(child.id)} style={{ marginLeft: '10px', color: 'red' }}>
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

export default Children;
