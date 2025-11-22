import React, { useEffect, useState } from 'react';
import authService from '../api/authService';

const Users = () => {
  const [users, setUsers] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [isCreating, setIsCreating] = useState(false);
  const [newUser, setNewUser] = useState({
    username: '',
    password: '',
    email: '',
    name: ''
  });

  useEffect(() => {
    fetchUsers();
  }, []);

  const fetchUsers = async () => {
    try {
      const response = await authService.getAllUsers();
      setUsers(response.data);
      setLoading(false);
    } catch (err) {
      setError('Ошибка при загрузке пользователей (нужны права админа)');
      setLoading(false);
    }
  };

  const handleSetRole = async (userId, role) => {
    try {
      await authService.setRole(userId, role);
      alert('Роль обновлена');
      fetchUsers();
    } catch (err) {
      alert('Ошибка при обновлении роли');
    }
  };

  const handleCreate = () => {
    setNewUser({
      username: '',
      password: '',
      email: '',
      name: ''
    });
    setIsCreating(true);
  };

  const handleChange = (e) => {
    const { name, value } = e.target;
    setNewUser({ ...newUser, [name]: value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      await authService.register(newUser);
      setIsCreating(false);
      fetchUsers();
      alert('Пользователь создан');
    } catch (err) {
      alert('Ошибка при создании: ' + (err.response?.data?.message || err.message));
    }
  };

  if (loading) return <div className="loading">Загрузка...</div>;
  if (error) return <div className="error">{error}</div>;

  return (
    <div>
      <div className="page-header">
        <h1>👥 Пользователи</h1>
        <button className="btn btn-primary" onClick={handleCreate}>
          + Добавить пользователя
        </button>
      </div>

      {isCreating && (
        <div className="card">
          <h2>Новый пользователь</h2>
          <form onSubmit={handleSubmit}>
            <div className="form-row">
              <div className="form-group">
                <label>Username</label>
                <input
                  className="form-control"
                  type="text"
                  name="username"
                  value={newUser.username}
                  onChange={handleChange}
                  required
                  placeholder="Login"
                />
              </div>
              <div className="form-group">
                <label>Password</label>
                <input
                  className="form-control"
                  type="password"
                  name="password"
                  value={newUser.password}
                  onChange={handleChange}
                  required
                  placeholder="Password"
                />
              </div>
            </div>
            <div className="form-row">
              <div className="form-group">
                <label>Email</label>
                <input
                  className="form-control"
                  type="email"
                  name="email"
                  value={newUser.email}
                  onChange={handleChange}
                  required
                  placeholder="Email"
                />
              </div>
              <div className="form-group">
                <label>Имя</label>
                <input
                  className="form-control"
                  type="text"
                  name="name"
                  value={newUser.name}
                  onChange={handleChange}
                  required
                  placeholder="Имя"
                />
              </div>
            </div>
            <div className="table-actions">
              <button type="submit" className="btn btn-success">Создать</button>
              <button type="button" className="btn btn-secondary" onClick={() => setIsCreating(false)}>
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
              <th>Username</th>
              <th>Email</th>
              <th>Роль</th>
              <th>Действия</th>
            </tr>
          </thead>
          <tbody>
            {users.map((user) => (
              <tr key={user.id}>
                <td><span className="badge badge-primary">#{user.id}</span></td>
                <td>{user.username}</td>
                <td>{user.email}</td>
                <td><span className={`badge ${user.role === 'ADMIN' ? 'badge-danger' : 'badge-success'}`}>{user.role}</span></td>
                <td>
                  <div className="table-actions">
                    {user.role !== 'ADMIN' && (
                      <button className="btn btn-warning btn-sm" onClick={() => handleSetRole(user.id, 'ADMIN')}>
                        Сделать Админом
                      </button>
                    )}
                    {user.role !== 'USER' && (
                      <button className="btn btn-info btn-sm" onClick={() => handleSetRole(user.id, 'USER')}>
                        Сделать Юзером
                      </button>
                    )}
                  </div>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </div>
  );
};

export default Users;
