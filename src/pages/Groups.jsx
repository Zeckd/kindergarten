import React, { useEffect, useState } from 'react';
import groupService from '../api/groupService';

const Groups = () => {
  const [groups, setGroups] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [isEditing, setIsEditing] = useState(false);
  const [currentGroup, setCurrentGroup] = useState({ name: '' });

  useEffect(() => {
    fetchGroups();
  }, []);

  const fetchGroups = async () => {
    try {
      const response = await groupService.getAll();
      setGroups(response.data);
      setLoading(false);
    } catch (err) {
      setError('Ошибка при загрузке групп');
      setLoading(false);
    }
  };

  const handleDelete = async (id) => {
    if (window.confirm('Вы уверены, что хотите удалить эту группу?')) {
      try {
        await groupService.delete(id);
        fetchGroups();
      } catch (err) {
        alert('Ошибка при удалении');
      }
    }
  };

  const handleEdit = (group) => {
    setCurrentGroup(group);
    setIsEditing(true);
  };

  const handleCreate = () => {
    setCurrentGroup({ name: '' });
    setIsEditing(true);
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      if (currentGroup.id) {
        await groupService.update(currentGroup);
      } else {
        await groupService.create(currentGroup);
      }
      setIsEditing(false);
      fetchGroups();
    } catch (err) {
      alert('Ошибка при сохранении');
    }
  };

  const handleChange = (e) => {
    const { name, value } = e.target;
    setCurrentGroup({ ...currentGroup, [name]: value });
  };

  if (loading) return <div>Загрузка...</div>;
  if (error) return <div>{error}</div>;

  return (
    <div>
      <h1>Группы</h1>
      <button onClick={handleCreate} style={{ marginBottom: '20px', padding: '10px', cursor: 'pointer' }}>
        Добавить группу
      </button>

      {isEditing && (
        <div className="form-container" style={{ marginBottom: '20px', padding: '20px', border: '1px solid #ccc' }}>
          <h2>{currentGroup.id ? 'Редактировать' : 'Создать'} группу</h2>
          <form onSubmit={handleSubmit}>
            <div style={{ marginBottom: '10px' }}>
              <label>Название: </label>
              <input
                type="text"
                name="name"
                value={currentGroup.name}
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
            <th>Название</th>
            <th>Действия</th>
          </tr>
        </thead>
        <tbody>
          {groups.map((group) => (
            <tr key={group.id}>
              <td>{group.id}</td>
              <td>{group.name}</td>
              <td>
                <button onClick={() => handleEdit(group)}>Редактировать</button>
                <button onClick={() => handleDelete(group.id)} style={{ marginLeft: '10px', color: 'red' }}>
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

export default Groups;
