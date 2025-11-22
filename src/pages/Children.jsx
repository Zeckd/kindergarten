import React, { useEffect, useState } from 'react';
import childService from '../api/childService';
import parentService from '../api/parentService';
import groupService from '../api/groupService';

const Children = () => {
  const [children, setChildren] = useState([]);
  const [parentsList, setParentsList] = useState([]);
  const [groupsList, setGroupsList] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [isEditing, setIsEditing] = useState(false);
  const [currentChild, setCurrentChild] = useState({
    firstName: '',
    lastName: '',
    patronymic: '',
    dateOfBirth: '',
    parentsId: [],
    group: ''
  });

  useEffect(() => {
    fetchData();
  }, []);

  const fetchData = async () => {
    try {
      const [childrenRes, parentsRes, groupsRes] = await Promise.all([
        childService.getAll(0, 100),
        parentService.getAll(0, 100),
        groupService.getAll(0, 100)
      ]);
      setChildren(childrenRes.data);
      setParentsList(parentsRes.data);
      setGroupsList(groupsRes.data);
      setLoading(false);
    } catch (err) {
      setError('Ошибка при загрузке данных');
      setLoading(false);
    }
  };

  const handleDelete = async (id) => {
    if (window.confirm('Вы уверены, что хотите удалить этого ребенка?')) {
      try {
        await childService.delete(id);
        fetchData();
      } catch (err) {
        alert('Ошибка при удалении');
      }
    }
  };

  const handleEdit = (child) => {
    setCurrentChild({
      id: child.id,
      firstName: child.firstName,
      lastName: child.lastName,
      patronymic: child.patronymic || '',
      dateOfBirth: child.dateOfBirth,
      parentsId: child.parents ? child.parents.map(p => p.id) : [],
      group: child.group ? child.group.id : ''
    });
    setIsEditing(true);
  };

  const handleCreate = () => {
    setCurrentChild({
      firstName: '',
      lastName: '',
      patronymic: '',
      dateOfBirth: '',
      parentsId: [],
      group: ''
    });
    setIsEditing(true);
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    const payload = {
      firstName: currentChild.firstName,
      lastName: currentChild.lastName,
      patronymic: currentChild.patronymic,
      dateOfBirth: currentChild.dateOfBirth,
      parentsId: currentChild.parentsId,
      group: currentChild.group
    };

    try {
      if (currentChild.id) {
        await childService.update(currentChild.id, payload);
      } else {
        await childService.create(payload);
      }
      setIsEditing(false);
      fetchData();
    } catch (err) {
      alert('Ошибка при сохранении: ' + (err.response?.data?.message || err.message));
    }
  };

  const handleChange = (e) => {
    const { name, value } = e.target;
    setCurrentChild({ ...currentChild, [name]: value });
  };

  const handleParentChange = (e) => {
    const selectedOptions = Array.from(e.target.selectedOptions, option => Number(option.value));
    setCurrentChild({ ...currentChild, parentsId: selectedOptions });
  };

  if (loading) return <div className="loading">Загрузка...</div>;
  if (error) return <div className="error">{error}</div>;

  return (
    <div>
      <div className="page-header">
        <h1>👶 Дети</h1>
        <div style={{ display: 'flex', gap: '10px' }}>
            <button className="btn btn-primary" onClick={handleCreate}>
            + Добавить ребенка
            </button>
            <button className="btn btn-secondary" onClick={() => {
                const groupId = prompt('ID Группы');
                if(groupId) {
                    childService.getByGroup(groupId).then(res => setChildren(res.data)).catch(() => alert('Ошибка'));
                }
            }}>
            По группе
            </button>
            <button className="btn btn-secondary" onClick={() => {
                const parentId = prompt('ID Родителя');
                if(parentId) {
                    childService.getByParent(parentId).then(res => setChildren(res.data)).catch(() => alert('Ошибка'));
                }
            }}>
            По родителю
            </button>
            <button className="btn btn-secondary" onClick={fetchData}>
            Сброс
            </button>
        </div>
      </div>

      {isEditing && (
        <div className="card">
          <h2>{currentChild.id ? 'Редактировать' : 'Добавить'} ребенка</h2>
          <form onSubmit={handleSubmit}>
            <div className="form-row">
              <div className="form-group">
                <label>Имя</label>
                <input
                  className="form-control"
                  type="text"
                  name="firstName"
                  value={currentChild.firstName}
                  onChange={handleChange}
                  required
                  placeholder="Введите имя"
                />
              </div>
              <div className="form-group">
                <label>Фамилия</label>
                <input
                  className="form-control"
                  type="text"
                  name="lastName"
                  value={currentChild.lastName}
                  onChange={handleChange}
                  required
                  placeholder="Введите фамилию"
                />
              </div>
            </div>
            <div className="form-row">
              <div className="form-group">
                <label>Отчество</label>
                <input
                  className="form-control"
                  type="text"
                  name="patronymic"
                  value={currentChild.patronymic}
                  onChange={handleChange}
                  placeholder="Введите отчество"
                />
              </div>
              <div className="form-group">
                <label>Дата рождения</label>
                <input
                  className="form-control"
                  type="date"
                  name="dateOfBirth"
                  value={currentChild.dateOfBirth}
                  onChange={handleChange}
                  required
                />
              </div>
            </div>
            <div className="form-row">
              <div className="form-group">
                <label>Группа</label>
                <select
                  className="form-control"
                  name="group"
                  value={currentChild.group}
                  onChange={handleChange}
                >
                  <option value="">Выберите группу</option>
                  {groupsList.map(g => (
                    <option key={g.id} value={g.id}>{g.name}</option>
                  ))}
                </select>
              </div>
              <div className="form-group">
                <label>Родители (Ctrl+Click для выбора нескольких)</label>
                <select
                  className="form-control"
                  multiple
                  value={currentChild.parentsId}
                  onChange={handleParentChange}
                  style={{ height: '100px' }}
                >
                  {parentsList.map(p => (
                    <option key={p.id} value={p.id}>
                      {p.firstName} {p.lastName} ({p.role})
                    </option>
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
              <th>Имя</th>
              <th>Фамилия</th>
              <th>Отчество</th>
              <th>Дата рождения</th>
              <th>Действия</th>
            </tr>
          </thead>
          <tbody>
            {children.length === 0 ? (
              <tr>
                <td colSpan="6" className="empty-state">
                  <h3>Список пуст</h3>
                  <p>Добавьте первого ребенка</p>
                </td>
              </tr>
            ) : (
              children.map((child) => (
                <tr key={child.id}>
                  <td><span className="badge badge-primary">#{child.id}</span></td>
                  <td>{child.firstName}</td>
                  <td>{child.lastName}</td>
                  <td>{child.patronymic || '—'}</td>
                  <td>{new Date(child.dateOfBirth).toLocaleDateString('ru-RU')}</td>
                  <td>
                    <div className="table-actions">
                      <button className="btn btn-secondary btn-sm" onClick={() => handleEdit(child)}>
                        Ред.
                      </button>
                      <button className="btn btn-danger btn-sm" onClick={() => handleDelete(child.id)}>
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

export default Children;
