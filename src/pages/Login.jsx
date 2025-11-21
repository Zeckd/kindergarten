import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import authService from '../api/authService';

const Login = () => {
  const [isRegistering, setIsRegistering] = useState(false);
  const [formData, setFormData] = useState({ username: '', password: '', email: '' });
  const [error, setError] = useState(null);
  const navigate = useNavigate();

  const handleChange = (e) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError(null);
    try {
      if (isRegistering) {
        await authService.register(formData);
        alert('Регистрация успешна! Теперь войдите.');
        setIsRegistering(false);
      } else {
        const token = 'Basic ' + btoa(formData.username + ':' + formData.password);
        localStorage.setItem('token', token);
        
        try {
            await authService.login(); // Verify credentials
            navigate('/');
        } catch (loginError) {
            localStorage.removeItem('token');
            throw loginError;
        }
      }
    } catch (err) {
      setError('Ошибка: ' + (err.response?.data?.message || err.message));
    }
  };

  return (
    <div style={{ display: 'flex', justifyContent: 'center', alignItems: 'center', height: '100vh', backgroundColor: '#f0f2f5' }}>
      <div style={{ padding: '40px', background: 'white', borderRadius: '8px', boxShadow: '0 4px 12px rgba(0,0,0,0.1)', width: '300px' }}>
        <h2 style={{ textAlign: 'center', marginBottom: '20px' }}>{isRegistering ? 'Регистрация' : 'Вход'}</h2>
        {error && <div style={{ color: 'red', marginBottom: '10px' }}>{error}</div>}
        <form onSubmit={handleSubmit}>
          <div style={{ marginBottom: '15px' }}>
            <label style={{ display: 'block', marginBottom: '5px' }}>Имя пользователя</label>
            <input
              type="text"
              name="username"
              value={formData.username}
              onChange={handleChange}
              required
              style={{ width: '100%', padding: '8px', boxSizing: 'border-box' }}
            />
          </div>
          {isRegistering && (
             <div style={{ marginBottom: '15px' }}>
             <label style={{ display: 'block', marginBottom: '5px' }}>Email</label>
             <input
               type="email"
               name="email"
               value={formData.email}
               onChange={handleChange}
               required
               style={{ width: '100%', padding: '8px', boxSizing: 'border-box' }}
             />
           </div>
          )}
          <div style={{ marginBottom: '20px' }}>
            <label style={{ display: 'block', marginBottom: '5px' }}>Пароль</label>
            <input
              type="password"
              name="password"
              value={formData.password}
              onChange={handleChange}
              required
              style={{ width: '100%', padding: '8px', boxSizing: 'border-box' }}
            />
          </div>
          <button type="submit" style={{ width: '100%', padding: '10px', backgroundColor: '#1890ff', color: 'white', border: 'none', borderRadius: '4px', cursor: 'pointer' }}>
            {isRegistering ? 'Зарегистрироваться' : 'Войти'}
          </button>
        </form>
        <div style={{ marginTop: '15px', textAlign: 'center' }}>
          <span
            onClick={() => setIsRegistering(!isRegistering)}
            style={{ color: '#1890ff', cursor: 'pointer' }}
          >
            {isRegistering ? 'Уже есть аккаунт? Войти' : 'Нет аккаунта? Зарегистрироваться'}
          </span>
        </div>
      </div>
    </div>
  );
};

export default Login;
