import React, { useEffect, useState } from 'react';
import childService from '../api/childService';
import groupService from '../api/groupService';
import teacherService from '../api/teacherService';
import paymentService from '../api/paymentService';

const Dashboard = () => {
  const [counts, setCounts] = useState({
    children: 0,
    groups: 0,
    teachers: 0,
    payments: 0
  });

  useEffect(() => {
    const fetchCounts = async () => {
      try {
        const [childrenRes, groupsRes, teachersRes, paymentsRes] = await Promise.all([
          childService.getAll(0, 1000),
          groupService.getAll(0, 1000),
          teacherService.getAll(0, 1000),
          paymentService.getAll()
        ]);
        setCounts({
          children: childrenRes.data.length,
          groups: groupsRes.data.length,
          teachers: teachersRes.data.length,
          payments: paymentsRes.data.length
        });
      } catch (err) {
        console.error('Error fetching counts', err);
      }
    };
    fetchCounts();
  }, []);

  const stats = [
    { title: 'Дети', count: counts.children, color: '#6366f1', icon: '👶', link: '/children' },
    { title: 'Группы', count: counts.groups, color: '#f43f5e', icon: '🏫', link: '/groups' },
    { title: 'Учителя', count: counts.teachers, color: '#10b981', icon: '👩‍🏫', link: '/teachers' },
    { title: 'Платежи', count: counts.payments, color: '#f59e0b', icon: '💳', link: '/payments' },
  ];

  return (
    <div>
      <div className="page-header">
        <h1>🏠 Панель управления</h1>
      </div>
      
      <div className="card" style={{ background: 'linear-gradient(135deg, #667eea 0%, #764ba2 100%)', color: 'white', marginBottom: '2rem' }}>
        <h2 style={{ color: 'white' }}>Добро пожаловать!</h2>
        <p style={{ fontSize: '1rem', opacity: 0.95 }}>
          Вы находитесь в системе управления детским садом. Используйте меню слева для навигации.
        </p>
      </div>

      <div style={{ display: 'grid', gridTemplateColumns: 'repeat(auto-fit, minmax(280px, 1fr))', gap: '1.5rem', marginBottom: '2rem' }}>
        {stats.map((stat, index) => (
          <div key={index} className="card" style={{ 
            borderLeft: `4px solid ${stat.color}`,
            cursor: 'pointer',
            transition: 'all 0.2s ease'
          }}>
            <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'flex-start' }}>
              <div>
                <h3 style={{ color: 'var(--text-secondary)', fontSize: '0.875rem', fontWeight: '600', marginBottom: '0.5rem', textTransform: 'uppercase' }}>
                  {stat.title}
                </h3>
                <div style={{ fontSize: '2rem', fontWeight: '700', color: stat.color }}>
                  {stat.count}
                </div>
              </div>
              <div style={{ fontSize: '2.5rem', opacity: 0.2 }}>
                {stat.icon}
              </div>
            </div>
          </div>
        ))}
      </div>

      <div className="card">
        <h2>📊 Быстрый доступ</h2>
        <div style={{ display: 'grid', gridTemplateColumns: 'repeat(auto-fit, minmax(200px, 1fr))', gap: '1rem', marginTop: '1rem' }}>
          <a href="/children" style={{ 
            padding: '1rem',
            background: 'var(--bg-primary)',
            borderRadius: 'var(--radius-md)',
            textAlign: 'center',
            transition: 'all 0.2s ease',
            border: '1px solid #e5e7eb'
          }}>
            <div style={{ fontSize: '2rem', marginBottom: '0.5rem' }}>👶</div>
            <div style={{ fontWeight: '600' }}>Управление детьми</div>
          </a>
          <a href="/groups" style={{ 
            padding: '1rem',
            background: 'var(--bg-primary)',
            borderRadius: 'var(--radius-md)',
            textAlign: 'center',
            transition: 'all 0.2s ease',
            border: '1px solid #e5e7eb'
          }}>
            <div style={{ fontSize: '2rem', marginBottom: '0.5rem' }}>🏫</div>
            <div style={{ fontWeight: '600' }}>Группы</div>
          </a>
          <a href="/payments" style={{ 
            padding: '1rem',
            background: 'var(--bg-primary)',
            borderRadius: 'var(--radius-md)',
            textAlign: 'center',
            transition: 'all 0.2s ease',
            border: '1px solid #e5e7eb'
          }}>
            <div style={{ fontSize: '2rem', marginBottom: '0.5rem' }}>💳</div>
            <div style={{ fontWeight: '600' }}>Платежи</div>
          </a>
          <a href="/teachers" style={{ 
            padding: '1rem',
            background: 'var(--bg-primary)',
            borderRadius: 'var(--radius-md)',
            textAlign: 'center',
            transition: 'all 0.2s ease',
            border: '1px solid #e5e7eb'
          }}>
            <div style={{ fontSize: '2rem', marginBottom: '0.5rem' }}>👩‍🏫</div>
            <div style={{ fontWeight: '600' }}>Учителя</div>
          </a>
        </div>
      </div>
    </div>
  );
};

export default Dashboard;
