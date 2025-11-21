import React from 'react';
import { Link, Outlet } from 'react-router-dom';
import './Layout.css'; // We'll create this css file

const Layout = () => {
  return (
    <div className="layout">
      <aside className="sidebar">
        <h2>Kindergarden</h2>
        <nav>
          <ul>
            <li><Link to="/">Главная</Link></li>
            <li><Link to="/teachers">Учителя</Link></li>
            <li><Link to="/parents">Родители</Link></li>
            <li><Link to="/children">Дети</Link></li>
            <li><Link to="/groups">Группы</Link></li>
            <li><Link to="/payments">Платежи</Link></li>
            <li><Link to="/age-groups">Возрастные группы</Link></li>
            <li><Link to="/history">История групп</Link></li>
            <li><Link to="/users">Пользователи</Link></li>
          </ul>
        </nav>
      </aside>
      <main className="content">
        <header className="top-bar">
          <div className="user-info">Admin</div>
        </header>
        <div className="page-content">
          <Outlet />
        </div>
      </main>
    </div>
  );
};

export default Layout;
