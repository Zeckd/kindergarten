import React, { useEffect, useState } from 'react';
import parentService from '../api/parentService';

const Parents = () => {
  const [parents, setParents] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [isEditing, setIsEditing] = useState(false);
  const [currentParent, setCurrentParent] = useState({
    firstName: '',
    lastName: '',
    patronymic: '',
    contactCreate: {
      phoneNumber: '',
      secondaryPhoneNumber: '',
      email: ''
    }
  });
  const [role, setRole] = useState('FATHER');

  useEffect(() => {
    fetchParents();
  }, []);

  const fetchParents = async () => {
    try {
      const response = await parentService.getAll(0, 100);
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
    setCurrentParent({
      id: parent.id,
      firstName: parent.firstName,
      lastName: parent.lastName,
      patronymic: parent.patronymic || '',
      contactCreate: {
        phoneNumber: parent.contact?.phoneNumber || '',
        secondaryPhoneNumber: parent.contact?.secondaryPhoneNumber || '',
        email: parent.contact?.email || ''
      }
    });
    setRole(parent.role);
    setIsEditing(true);
  };

  const handleCreate = () => {
    setCurrentParent({
      firstName: '',
      lastName: '',
      patronymic: '',
      contactCreate: {
        phoneNumber: '',
        secondaryPhoneNumber: '',
        email: ''
      }
    });
    setRole('FATHER');
    setIsEditing(true);
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    const payload = {
      firstName: currentParent.firstName,
      lastName: currentParent.lastName,
      patronymic: currentParent.patronymic,
      contactCreate: currentParent.contactCreate
    };

    try {
      if (currentParent.id) {
        await parentService.update(currentParent.id, payload, role);
      } else {
        await parentService.create(payload, role);
      }
      setIsEditing(false);
      fetchParents();
    } catch (err) {
      alert('Ошибка при сохранении: ' + (err.response?.data?.message || err.message));
    }
  };

  const handleChange = (e) => {
    const { name, value } = e.target;
    if (name.startsWith('contactCreate.')) {
      const contactField = name.split('.')[1];
      setCurrentParent({
        ...currentParent,
        contactCreate: {
          ...currentParent.contactCreate,
          [contactField]: value
        }
      });
    } else {
      setCurrentParent({ ...currentParent, [name]: value });
    }
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
              <label>Отчество: </label>
              <input
                type="text"
                name="patronymic"
                value={currentParent.patronymic}
                onChange={handleChange}
              />
            </div>
            <div style={{ marginBottom: '10px' }}>
              <label>Телефон (+996...): </label>
              <input
                type="text"
                name="contactCreate.phoneNumber"
                value={currentParent.contactCreate.phoneNumber}
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
                value={currentParent.contactCreate.secondaryPhoneNumber}
                onChange={handleChange}
                placeholder="+996555123456"
              />
            </div>
            <div style={{ marginBottom: '10px' }}>
              <label>Email: </label>
              <input
                type="email"
                name="contactCreate.email"
                value={currentParent.contactCreate.email}
                onChange={handleChange}
                required
              />
            </div>
            <div style={{ marginBottom: '10px' }}>
              <label>Роль: </label>
              <select
                value={role}
                onChange={(e) => setRole(e.target.value)}
                required
              >
                <option value="FATHER">Отец</option>
                <option value="MOTHER">Мать</option>
                <option value="BROTHER">Брат</option>
                <option value="SISTER">Сестра</option>
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
            <th>Роль</th>
            <th>Телефон</th>
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
              <td>{parent.contact?.phoneNumber}</td>
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
