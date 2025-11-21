import React, { useEffect, useState } from 'react';
import teacherService from '../api/teacherService';

const Teachers = () => {
  const [teachers, setTeachers] = useState([]);
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
    }
  });
  const [position, setPosition] = useState('TEACHER');

  useEffect(() => {
    fetchTeachers();
  }, []);

  const fetchTeachers = async () => {
    try {
      const response = await teacherService.getAll(0, 100); // Fetching first 100 for now
      setTeachers(response.data);
      setLoading(false);
    } catch (err) {
      setError('Ошибка при загрузке учителей');
      setLoading(false);
    }
  };

  const handleDelete = async (id) => {
    if (window.confirm('Вы уверены, что хотите удалить этого учителя?')) {
      try {
        await teacherService.delete(id);
        fetchTeachers();
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
      }
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
      }
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
      contactCreate: currentTeacher.contactCreate
    };

    try {
      if (currentTeacher.id) {
        await teacherService.update(currentTeacher.id, payload, position);
      } else {
        await teacherService.create(payload, position);
      }
      setIsEditing(false);
      fetchTeachers();
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

  if (loading) return <div>Загрузка...</div>;
  if (error) return <div>{error}</div>;

  return (
    <div>
      <h1>Учителя</h1>
      <button onClick={handleCreate} style={{ marginBottom: '20px', padding: '10px', cursor: 'pointer' }}>
        Добавить учителя
      </button>

      {isEditing && (
        <div className="form-container" style={{ marginBottom: '20px', padding: '20px', border: '1px solid #ccc' }}>
          <h2>{currentTeacher.id ? 'Редактировать' : 'Создать'} учителя</h2>
          <form onSubmit={handleSubmit}>
            <div style={{ marginBottom: '10px' }}>
              <label>Имя: </label>
              <input
                type="text"
                name="firstName"
                value={currentTeacher.firstName}
                onChange={handleChange}
                required
              />
            </div>
            <div style={{ marginBottom: '10px' }}>
              <label>Фамилия: </label>
              <input
                type="text"
                name="lastName"
                value={currentTeacher.lastName}
                onChange={handleChange}
                required
              />
            </div>
            <div style={{ marginBottom: '10px' }}>
              <label>Отчество: </label>
              <input
                type="text"
                name="patronymic"
                value={currentTeacher.patronymic}
                onChange={handleChange}
              />
            </div>
            <div style={{ marginBottom: '10px' }}>
              <label>Дата рождения: </label>
              <input
                type="date"
                name="dateOfBirth"
                value={currentTeacher.dateOfBirth}
                onChange={handleChange}
                required
              />
            </div>
            <div style={{ marginBottom: '10px' }}>
              <label>Телефон (+996...): </label>
              <input
                type="text"
                name="contactCreate.phoneNumber"
                value={currentTeacher.contactCreate.phoneNumber}
                onChange={handleChange}
                required
                placeholder="+996700123456"
              />
            </div>
            <div style={{ marginBottom: '10px' }}>
              <label>Доп. телефон: </label>
              <input
                type="text"
                name="contactCreate.secondaryPhoneNumber"
                value={currentTeacher.contactCreate.secondaryPhoneNumber}
                onChange={handleChange}
                placeholder="+996555123456"
              />
            </div>
            <div style={{ marginBottom: '10px' }}>
              <label>Email: </label>
              <input
                type="email"
                name="contactCreate.email"
                value={currentTeacher.contactCreate.email}
                onChange={handleChange}
                required
              />
            </div>
            <div style={{ marginBottom: '10px' }}>
              <label>Должность: </label>
              <select
                value={position}
                onChange={(e) => setPosition(e.target.value)}
                required
              >
                <option value="TEACHER">Учитель</option>
                <option value="ASSISTANT">Ассистент</option>
              </select>
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
            <th>Должность</th>
            <th>Телефон</th>
            <th>Действия</th>
          </tr>
        </thead>
        <tbody>
          {teachers.map((teacher) => (
            <tr key={teacher.id}>
              <td>{teacher.id}</td>
              <td>{teacher.firstName}</td>
              <td>{teacher.lastName}</td>
              <td>{teacher.position}</td>
              <td>{teacher.contact?.phoneNumber}</td>
              <td>
                <button onClick={() => handleEdit(teacher)}>Редактировать</button>
                <button onClick={() => handleDelete(teacher.id)} style={{ marginLeft: '10px', color: 'red' }}>
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

export default Teachers;
