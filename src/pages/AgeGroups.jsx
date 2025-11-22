import React, { useEffect, useState } from 'react';
import ageGroupService from '../api/ageGroupService';

const AgeGroups = () => {
  const [ageGroups, setAgeGroups] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [isEditing, setIsEditing] = useState(false);
  const [currentAgeGroup, setCurrentAgeGroup] = useState({ name: '', minAge: '', maxAge: '' });

  useEffect(() => {
    fetchAgeGroups();
  }, []);

  const fetchAgeGroups = async () => {
    try {
      const response = await ageGroupService.getAll();
      setAgeGroups(response.data);
      setLoading(false);
    } catch (err) {
      setError('Ошибка при загрузке возрастных групп');
      setLoading(false);
    }
  };

  const handleDelete = async (id) => {
    if (window.confirm('Вы уверены, что хотите удалить эту возрастную группу?')) {
      try {
        await ageGroupService.delete(id);
        fetchAgeGroups();
      } catch (err) {
        alert('Ошибка при удалении');
      }
    }
  };

  const handleEdit = (ageGroup) => {
    setCurrentAgeGroup(ageGroup);
    setIsEditing(true);
  };

  const handleCreate = () => {
    setCurrentAgeGroup({ name: '', minAge: '', maxAge: '' });
    setIsEditing(true);
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      if (currentAgeGroup.id) {
        await ageGroupService.update(currentAgeGroup);
      } else {
        await ageGroupService.create(currentAgeGroup);
      }
      setIsEditing(false);
      fetchAgeGroups();
    } catch (err) {
      alert('Ошибка при сохранении');
    }
  };

  const handleChange = (e) => {
    const { name, value } = e.target;
    setCurrentAgeGroup({ ...currentAgeGroup, [name]: value });
  };

  if (loading) return <div>Загрузка...</div>;
  if (error) return <div>{error}</div>;

  return (
    <div>
      <h1>Возрастные группы</h1>
      <button onClick={handleCreate} style={{ marginBottom: '20px', padding: '10px', cursor: 'pointer' }}>
        Добавить возрастную группу
      </button>

      {isEditing && (
        <div className="form-container" style={{ marginBottom: '20px', padding: '20px', border: '1px solid #ccc' }}>
          <h2>{currentAgeGroup.id ? 'Редактировать' : 'Создать'} возрастную группу</h2>
          <form onSubmit={handleSubmit}>
            <div style={{ marginBottom: '10px' }}>
              <label>Название: </label>
              <input
                type="text"
                name="name"
                value={currentAgeGroup.name}
                onChange={handleChange}
                required
              />
            </div>
            <div style={{ marginBottom: '10px' }}>
              <label>Мин. возраст: </label>
              <input
                type="number"
                name="minAge"
                value={currentAgeGroup.minAge}
                onChange={handleChange}
                required
              />
            </div>
            <div style={{ marginBottom: '10px' }}>
              <label>Макс. возраст: </label>
              <input
                type="number"
                name="maxAge"
                value={currentAgeGroup.maxAge}
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
            <th>Мин. возраст</th>
            <th>Макс. возраст</th>
            <th>Действия</th>
          </tr>
        </thead>
        <tbody>
          {ageGroups.map((group) => (
            <tr key={group.id}>
              <td>{group.id}</td>
              <td>{group.name}</td>
              <td>{group.minAge}</td>
              <td>{group.maxAge}</td>
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

export default AgeGroups;
