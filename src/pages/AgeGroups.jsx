import React, { useEffect, useState } from 'react';
import ageGroupService from '../api/ageGroupService';

const AgeGroups = () => {
  const [ageGroups, setAgeGroups] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [isEditing, setIsEditing] = useState(false);
  const [currentAgeGroup, setCurrentAgeGroup] = useState({ name: '', ageGroup: '', price: '' });

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
    setCurrentAgeGroup({ name: '', ageGroup: '', price: '' });
    setIsEditing(true);
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    const payload = {
      name: currentAgeGroup.name,
      ageGroup: currentAgeGroup.ageGroup,
      price: currentAgeGroup.price
    };

    try {
      if (currentAgeGroup.id) {
        await ageGroupService.update({ ...payload, id: currentAgeGroup.id });
      } else {
        await ageGroupService.create(payload);
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

  if (loading) return <div className="loading">Загрузка...</div>;
  if (error) return <div className="error">{error}</div>;

  return (
    <div>
      <div className="page-header">
        <h1>🎂 Возрастные группы</h1>
        <button className="btn btn-primary" onClick={handleCreate}>
          + Добавить возрастную группу
        </button>
      </div>

      {isEditing && (
        <div className="card">
          <h2>{currentAgeGroup.id ? 'Редактировать' : 'Добавить'} возрастную группу</h2>
          <form onSubmit={handleSubmit}>
            <div className="form-row">
              <div className="form-group">
                <label>Название</label>
                <input
                  className="form-control"
                  type="text"
                  name="name"
                  value={currentAgeGroup.name}
                  onChange={handleChange}
                  required
                  placeholder="Например: Младшая группа"
                />
              </div>
              <div className="form-group">
                <label>Возрастная категория (1-7)</label>
                <input
                  className="form-control"
                  type="number"
                  name="ageGroup"
                  value={currentAgeGroup.ageGroup}
                  onChange={handleChange}
                  required
                  min="1"
                  max="7"
                />
              </div>
            </div>
            <div className="form-group">
              <label>Цена</label>
              <input
                className="form-control"
                type="number"
                name="price"
                value={currentAgeGroup.price}
                onChange={handleChange}
                required
                placeholder="Введите цену"
              />
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
              <th>Название</th>
              <th>Возрастная категория</th>
              <th>Цена</th>
              <th>Действия</th>
            </tr>
          </thead>
          <tbody>
            {ageGroups.length === 0 ? (
              <tr>
                <td colSpan="5" className="empty-state">
                  <h3>Список пуст</h3>
                  <p>Добавьте первую возрастную группу</p>
                </td>
              </tr>
            ) : (
              ageGroups.map((group) => (
                <tr key={group.id}>
                  <td><span className="badge badge-primary">#{group.id}</span></td>
                  <td>{group.name}</td>
                  <td><span className="badge badge-info">{group.ageGroup}</span></td>
                  <td><strong>{group.price} сом</strong></td>
                  <td>
                    <div className="table-actions">
                      <button className="btn btn-secondary btn-sm" onClick={() => handleEdit(group)}>
                        Ред.
                      </button>
                      <button className="btn btn-danger btn-sm" onClick={() => handleDelete(group.id)}>
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

export default AgeGroups;
