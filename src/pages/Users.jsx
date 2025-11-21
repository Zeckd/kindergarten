import React, { useEffect, useState } from 'react';
import authService from '../api/authService';

const Users = () => {
  const [users, setUsers] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

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

  if (loading) return <div>Загрузка...</div>;
  if (error) return <div>{error}</div>;

  return (
    <div>
      <h1>Пользователи (Админ)</h1>
      <table border="1" cellPadding="10" style={{ width: '100%', borderCollapse: 'collapse' }}>
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
              <td>{user.id}</td>
              <td>{user.username}</td>
              <td>{user.email}</td>
              <td>{user.role}</td>
              <td>
                <button onClick={() => handleSetRole(user.id, 'ADMIN')}>Сделать Админом</button>
                <button onClick={() => handleSetRole(user.id, 'USER')} style={{ marginLeft: '5px' }}>Сделать Юзером</button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
};

export default Users;
