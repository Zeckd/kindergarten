import React, { useEffect, useState } from 'react';
import parentService from '../api/parentService';
import childService from '../api/childService';

const Parents = () => {
  const [parents, setParents] = useState([]);
  const [childrenList, setChildrenList] = useState([]);
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
    },
    childrenId: []
  });
  const [role, setRole] = useState('FATHER');

  useEffect(() => {
    fetchData();
  }, []);

  const fetchData = async () => {
    try {
      const [parentsRes, childrenRes] = await Promise.all([
        parentService.getAll(0, 100),
        childService.getAll(0, 100)
      ]);
      setParents(parentsRes.data);
      setChildrenList(childrenRes.data);
      setLoading(false);
    } catch (err) {
      setError('Ошибка при загрузке данных');
      setLoading(false);
    }
  };

  const handleDelete = async (id) => {
    if (window.confirm('Вы уверены, что хотите удалить этого родителя?')) {
      try {
        await parentService.delete(id);
        fetchData();
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
      },
      childrenId: parent.children ? parent.children.map(c => c.id) : []
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
      },
      childrenId: []
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
      contactCreate: currentParent.contactCreate,
      childrenId: currentParent.childrenId
    };

    try {
      if (currentParent.id) {
        await parentService.update(currentParent.id, payload, role);
      } else {
        await parentService.create(payload, role);
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

  const handleChildChange = (e) => {
    const selectedOptions = Array.from(e.target.selectedOptions, option => Number(option.value));
    setCurrentParent({ ...currentParent, childrenId: selectedOptions });
  };

  if (loading) return <div className="loading">Загрузка...</div>;
  if (error) return <div className="error">{error}</div>;

  return (
    <div>
      <div className="page-header">
        <h1>👨‍👩‍👧 Родители</h1>
        <button className="btn btn-primary" onClick={handleCreate}>
          + Добавить родителя
        </button>
      </div>

      {isEditing && (
        <div className="card">
          <h2>{currentParent.id ? 'Редактировать' : 'Добавить'} родителя</h2>
          <form onSubmit={handleSubmit}>
            <div className="form-row">
              <div className="form-group">
                <label>Имя</label>
                <input
                  className="form-control"
                  type="text"
                  name="firstName"
                  value={currentParent.firstName}
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
                  value={currentParent.lastName}
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
                  value={currentParent.patronymic}
                  onChange={handleChange}
                  placeholder="Введите отчество"
                />
              </div>
              <div className="form-group">
                <label>Роль</label>
                <select
                  className="form-control"
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
            </div>
            <div className="form-row">
              <div className="form-group">
                <label>Телефон</label>
                <input
                  className="form-control"
                  type="text"
                  name="contactCreate.phoneNumber"
                  value={currentParent.contactCreate.phoneNumber}
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
                  value={currentParent.contactCreate.secondaryPhoneNumber}
                  onChange={handleChange}
                  placeholder="+996555123456"
                />
              </div>
            </div>
            <div className="form-group">
              <label>Email</label>
              <input
                className="form-control"
                type="email"
                name="contactCreate.email"
                value={currentParent.contactCreate.email}
                onChange={handleChange}
                required
                placeholder="example@mail.com"
              />
            </div>
            <div className="form-group">
              <label>Дети (Ctrl+Click для выбора нескольких)</label>
              <select
                className="form-control"
                multiple
                value={currentParent.childrenId}
                onChange={handleChildChange}
                style={{ height: '100px' }}
              >
                {childrenList.map(c => (
                  <option key={c.id} value={c.id}>
                    {c.firstName} {c.lastName}
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
              <th>Имя</th>
              <th>Фамилия</th>
              <th>Отчество</th>
              <th>Роль</th>
              <th>Телефон</th>
              <th>Email</th>
              <th>Действия</th>
            </tr>
          </thead>
          <tbody>
            {parents.length === 0 ? (
              <tr>
                <td colSpan="8" className="empty-state">
                  <h3>Список пуст</h3>
                  <p>Добавьте первого родителя</p>
                </td>
              </tr>
            ) : (
              parents.map((parent) => (
                <tr key={parent.id}>
                  <td><span className="badge badge-primary">#{parent.id}</span></td>
                  <td>{parent.firstName}</td>
                  <td>{parent.lastName}</td>
                  <td>{parent.patronymic || '—'}</td>
                  <td><span className="badge badge-info">{parent.role}</span></td>
                  <td>{parent.contact?.phoneNumber || '—'}</td>
                  <td>{parent.contact?.email || '—'}</td>
                  <td>
                    <div className="table-actions">
                      <button className="btn btn-secondary btn-sm" onClick={() => handleEdit(parent)}>
                        Ред.
                      </button>
                      <button className="btn btn-info btn-sm" onClick={async () => {
                        try {
                          const res = await parentService.getChildren(parent.id);
                          alert('Дети: ' + JSON.stringify(res.data, null, 2));
                        } catch (e) {
                          alert('Ошибка при получении детей');
                        }
                      }}>
                        Дети
                      </button>
                      <button className="btn btn-danger btn-sm" onClick={() => handleDelete(parent.id)}>
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

export default Parents;
