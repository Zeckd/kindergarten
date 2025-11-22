import React, { useEffect, useState } from 'react';
import teacherService from '../api/teacherService';
import groupService from '../api/groupService';

const Teachers = () => {
  const [teachers, setTeachers] = useState([]);
  const [groupsList, setGroupsList] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [isEditing, setIsEditing] = useState(false);
  const [currentTeacher, setCurrentTeacher] = useState({
    firstName: '',
    lastName: '',
    patronymic: '',
    dateOfBirth: '',
    contactCreate: {
      phoneNumber: '',
      secondaryPhoneNumber: '',
      email: ''
    },
    groupId: ''
  });
  const [position, setPosition] = useState('TEACHER');

  useEffect(() => {
    fetchData();
  }, []);

  const fetchData = async () => {
    try {
      const [teachersRes, groupsRes] = await Promise.all([
        teacherService.getAll(0, 100),
        groupService.getAll(0, 100)
      ]);
      setTeachers(teachersRes.data);
      setGroupsList(groupsRes.data);
      setLoading(false);
    } catch (err) {
      setError('Ошибка при загрузке данных');
      setLoading(false);
    }
  };

  const handleDelete = async (id) => {
    if (window.confirm('Вы уверены, что хотите удалить этого учителя?')) {
      try {
        await teacherService.delete(id);
        fetchData();
      } catch (err) {
        alert('Ошибка при удалении');
      }
    }
  };

  const handleEdit = (teacher) => {
    setCurrentTeacher({
      id: teacher.id,
      firstName: teacher.firstName,
      lastName: teacher.lastName,
      patronymic: teacher.patronymic || '',
      dateOfBirth: teacher.dateOfBirth,
      contactCreate: {
        phoneNumber: teacher.contact?.phoneNumber || '',
        secondaryPhoneNumber: teacher.contact?.secondaryPhoneNumber || '',
        email: teacher.contact?.email || ''
      },
      groupId: teacher.group ? teacher.group.id : ''
    });
    setPosition(teacher.position);
    setIsEditing(true);
  };

  const handleCreate = () => {
    setCurrentTeacher({
      firstName: '',
      lastName: '',
      patronymic: '',
      dateOfBirth: '',
      contactCreate: {
        phoneNumber: '',
        secondaryPhoneNumber: '',
        email: ''
      },
      groupId: ''
    });
    setPosition('TEACHER');
    setIsEditing(true);
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    const payload = {
      firstName: currentTeacher.firstName,
      lastName: currentTeacher.lastName,
      patronymic: currentTeacher.patronymic,
      dateOfBirth: currentTeacher.dateOfBirth,
      contactCreate: currentTeacher.contactCreate,
      groupId: currentTeacher.groupId
    };

    try {
      if (currentTeacher.id) {
        await teacherService.update(currentTeacher.id, payload, position);
      } else {
        await teacherService.create(payload, position);
      }
      setIsEditing(false);
      fetchData();
    } catch (err) {
      alert('Ошибка при сохранении: ' + (err.response?.data?.message || err.message));
    }
  };

  const handleChange = (e) => {
    const { name, value } = e.target;
    if (name.startsWith('contactCreate.')) {
      const contactField = name.split('.')[1];
      setCurrentTeacher({
        ...currentTeacher,
        contactCreate: {
          ...currentTeacher.contactCreate,
          [contactField]: value
        }
      });
    } else {
      setCurrentTeacher({ ...currentTeacher, [name]: value });
    }
  };

  if (loading) return <div className="loading">Загрузка...</div>;
  if (error) return <div className="error">{error}</div>;

  return (
    <div>
      <div className="page-header">
        <h1>👨‍🏫 Учителя</h1>
        <button className="btn btn-primary" onClick={handleCreate}>
          + Добавить учителя
        </button>
      </div>

      {isEditing && (
        <div className="card">
          <h2>{currentTeacher.id ? 'Редактировать' : 'Добавить'} учителя</h2>
          <form onSubmit={handleSubmit}>
            <div className="form-row">
              <div className="form-group">
                <label>Имя</label>
                <input
                  className="form-control"
                  type="text"
                  name="firstName"
                  value={currentTeacher.firstName}
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
                  value={currentTeacher.lastName}
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
                  value={currentTeacher.patronymic}
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
                  value={currentTeacher.dateOfBirth}
                  onChange={handleChange}
                  required
                />
              </div>
            </div>
            <div className="form-row">
              <div className="form-group">
                <label>Телефон</label>
                <input
                  className="form-control"
                  type="text"
                  name="contactCreate.phoneNumber"
                  value={currentTeacher.contactCreate.phoneNumber}
                  onChange={handleChange}
                  required
                  placeholder="+996700123456"
                />
              </div>
              <div className="form-group">
                <label>Доп. телефон</label>
                <input
                  className="form-control"
                  type="text"
                  name="contactCreate.secondaryPhoneNumber"
                  value={currentTeacher.contactCreate.secondaryPhoneNumber}
                  onChange={handleChange}
                  placeholder="+996555123456"
                />
              </div>
            </div>
            <div className="form-row">
              <div className="form-group">
                <label>Email</label>
                <input
                  className="form-control"
                  type="email"
                  name="contactCreate.email"
                  value={currentTeacher.contactCreate.email}
                  onChange={handleChange}
                  required
                  placeholder="example@mail.com"
                />
              </div>
              <div className="form-group">
                <label>Должность</label>
                <select
                  className="form-control"
                  value={position}
                  onChange={(e) => setPosition(e.target.value)}
                  required
                >
                  <option value="TEACHER">Учитель</option>
                  <option value="ASSISTANT">Ассистент</option>
                </select>
              </div>
              <div className="form-group">
                <label>Группа</label>
                <select
                  className="form-control"
                  name="groupId"
                  value={currentTeacher.groupId}
                  onChange={handleChange}
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
              <th>Имя</th>
              <th>Фамилия</th>
              <th>Отчество</th>
              <th>Должность</th>
              <th>Телефон</th>
              <th>Email</th>
              <th>Действия</th>
            </tr>
          </thead>
          <tbody>
            {teachers.length === 0 ? (
              <tr>
                <td colSpan="8" className="empty-state">
                  <h3>Список пуст</h3>
                  <p>Добавьте первого учителя</p>
                </td>
              </tr>
            ) : (
              teachers.map((teacher) => (
                <tr key={teacher.id}>
                  <td><span className="badge badge-primary">#{teacher.id}</span></td>
                  <td>{teacher.firstName}</td>
                  <td>{teacher.lastName}</td>
                  <td>{teacher.patronymic || '—'}</td>
                  <td><span className="badge badge-success">{teacher.position}</span></td>
                  <td>{teacher.contact?.phoneNumber || '—'}</td>
                  <td>{teacher.contact?.email || '—'}</td>
                  <td>
                    <div className="table-actions">
                      <button className="btn btn-secondary btn-sm" onClick={() => handleEdit(teacher)}>
                        Ред.
                      </button>
                      <button className="btn btn-info btn-sm" onClick={async () => {
                        try {
                          const res = await teacherService.getGroup(teacher.id);
                          alert('Группа: ' + JSON.stringify(res.data, null, 2));
                        } catch (e) {
                          alert('Ошибка при получении группы');
                        }
                      }}>
                        Группа
                      </button>
                      <button className="btn btn-danger btn-sm" onClick={() => handleDelete(teacher.id)}>
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

export default Teachers;
