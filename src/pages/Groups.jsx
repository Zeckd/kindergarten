import React, { useEffect, useState } from 'react';
import groupService from '../api/groupService';
import ageGroupService from '../api/ageGroupService';

const Groups = () => {
  const [groups, setGroups] = useState([]);
  const [ageGroups, setAgeGroups] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [isEditing, setIsEditing] = useState(false);
  const [currentGroup, setCurrentGroup] = useState({ name: '', ageGroupId: '' });

  useEffect(() => {
    fetchGroups();
    fetchAgeGroups();
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

  const fetchAgeGroups = async () => {
    try {
      const response = await ageGroupService.getAll();
      setAgeGroups(response.data);
    } catch (err) {
      console.error('Ошибка при загрузке возрастных групп', err);
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
    setCurrentGroup({ ...group, ageGroupId: group.ageGroup ? group.ageGroup.id : '' });
    setIsEditing(true);
  };

  const handleCreate = () => {
    setCurrentGroup({ name: '', ageGroupId: '' });
    setIsEditing(true);
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    const payload = {
      name: currentGroup.name,
      ageGroupId: currentGroup.ageGroupId
    };

    try {
      if (currentGroup.id) {
        await groupService.update({ ...payload, id: currentGroup.id });
      } else {
        await groupService.create(payload);
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

  if (loading) return <div className="loading">Загрузка...</div>;
  if (error) return <div className="error">{error}</div>;

  return (
    <div>
      <div className="page-header">
        <h1>👥 Группы</h1>
        <button className="btn btn-primary" onClick={handleCreate}>
          + Добавить группу
        </button>
      </div>

      {isEditing && (
        <div className="card">
          <h2>{currentGroup.id ? 'Редактировать' : 'Добавить'} группу</h2>
          <form onSubmit={handleSubmit}>
            <div className="form-group">
              <label>Название</label>
              <input
                className="form-control"
                type="text"
                name="name"
                value={currentGroup.name}
                onChange={handleChange}
                required
                placeholder="Введите название группы"
              />
            </div>
            <div className="form-group">
              <label>Возрастная группа</label>
              <select
                className="form-control"
                name="ageGroupId"
                value={currentGroup.ageGroupId}
                onChange={handleChange}
                required
              >
                <option value="">Выберите возрастную группу</option>
                {ageGroups.map((ag) => (
                  <option key={ag.id} value={ag.id}>
                    {ag.name}
                  </option>
                ))}
              </select>
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
              <th>Возрастная группа</th>
              <th>Персонал</th>
              <th>Дети</th>
              <th>Действия</th>
            </tr>
          </thead>
          <tbody>
            {groups.length === 0 ? (
              <tr>
                <td colSpan="6" className="empty-state">
                  <h3>Список пуст</h3>
                  <p>Добавьте первую группу</p>
                </td>
              </tr>
            ) : (
              groups.map((group) => (
                <tr key={group.id}>
                  <td><span className="badge badge-primary">#{group.id}</span></td>
                  <td>{group.name}</td>
                  <td><span className="badge badge-info">{group.ageGroup?.name || '—'}</span></td>
                  <td>
                    {group.teacher && (
                      <div style={{ marginBottom: '5px' }}>
                        <strong>Учитель:</strong><br/>
                        {group.teacher.firstName} {group.teacher.lastName}
                      </div>
                    )}
                    {group.assistant && (
                      <div>
                        <strong>Ассистент:</strong><br/>
                        {group.assistant.firstName} {group.assistant.lastName}
                      </div>
                    )}
                    {!group.teacher && !group.assistant && <span className="text-muted">—</span>}
                  </td>
                  <td>
                    {group.children && group.children.length > 0 ? (
                      <details>
                        <summary>Показать ({group.children.length})</summary>
                        <ul style={{ paddingLeft: '20px', margin: '5px 0' }}>
                          {group.children.map(child => (
                            <li key={child.id}>
                              {child.firstName} {child.lastName}
                            </li>
                          ))}
                        </ul>
                      </details>
                    ) : (
                      <span className="text-muted">Нет детей</span>
                    )}
                  </td>
                  <td>
                    <div className="table-actions">
                      <button className="btn btn-secondary btn-sm" onClick={() => handleEdit(group)}>
                        Ред.
                      </button>
                      <button className="btn btn-success btn-sm" onClick={() => {
                        const teacherId = prompt('Введите числовой ID Учителя/Ассистента');
                        if(teacherId && !isNaN(teacherId)) {
                          groupService.addMember({ id: group.id, teacherOrAssistantId: teacherId })
                            .then(() => {
                              alert('Добавлено');
                              fetchGroups();
                            })
                            .catch((e) => alert('Ошибка: ' + (e.response?.data?.message || e.message)));
                        } else if (teacherId) {
                          alert('ID должен быть числом');
                        }
                      }}>
                        + Учитель
                      </button>
                      <button className="btn btn-success btn-sm" onClick={() => {
                        const childId = prompt('Введите числовой ID Ребенка');
                        if(childId && !isNaN(childId)) {
                          groupService.addMember({ id: group.id, childId: childId })
                            .then(() => {
                              alert('Добавлено');
                              fetchGroups();
                            })
                            .catch((e) => alert('Ошибка: ' + (e.response?.data?.message || e.message)));
                        } else if (childId) {
                          alert('ID должен быть числом');
                        }
                      }}>
                        + Ребенок
                      </button>
                      <button className="btn btn-warning btn-sm" onClick={() => {
                        if(confirm('Удалить учителя?')) {
                          groupService.removeTeacher(group.id)
                            .then(() => {
                              alert('Удалено');
                              fetchGroups();
                            })
                            .catch(() => alert('Ошибка'));
                        }
                      }}>
                        − Учитель
                      </button>
                      <button className="btn btn-warning btn-sm" onClick={() => {
                        const childId = prompt('Введите числовой ID Ребенка для удаления');
                        if(childId && !isNaN(childId) && confirm('Удалить ребенка?')) {
                          groupService.removeChild(group.id, childId)
                            .then(() => {
                              alert('Удалено');
                              fetchGroups();
                            })
                            .catch((e) => alert('Ошибка: ' + (e.response?.data?.message || e.message)));
                        } else if (childId) {
                          alert('ID должен быть числом');
                        }
                      }}>
                        − Ребенок
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

export default Groups;
