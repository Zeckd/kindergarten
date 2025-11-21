import React, { useEffect, useState } from 'react';
import teacherService from '../api/teacherService';

const Teachers = () => {
  const [teachers, setTeachers] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [isEditing, setIsEditing] = useState(false);
  const [currentTeacher, setCurrentTeacher] = useState({ firstName: '', lastName: '', position: '' });

  useEffect(() => {
    fetchTeachers();
  }, []);

  const fetchTeachers = async () => {
    try {
      const response = await teacherService.getAll();
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
    setCurrentTeacher(teacher);
    setIsEditing(true);
  };

  const handleCreate = () => {
    setCurrentTeacher({ firstName: '', lastName: '', position: '' });
    setIsEditing(true);
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      if (currentTeacher.id) {
        await teacherService.update(currentTeacher);
      } else {
        await teacherService.create(currentTeacher);
      }
      setIsEditing(false);
      fetchTeachers();
    } catch (err) {
      alert('Ошибка при сохранении');
    }
  };

  const handleChange = (e) => {
    const { name, value } = e.target;
    setCurrentTeacher({ ...currentTeacher, [name]: value });
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
              <label>Должность: </label>
              <input
                type="text"
                name="position"
                value={currentTeacher.position}
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
            <th>Должность</th>
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
