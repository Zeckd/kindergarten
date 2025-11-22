import React from 'react';
import { NavLink, Outlet } from 'react-router-dom';
import './Layout.css';

const Layout = () => {
  return (
    <div className="layout">
      <aside className="sidebar">
        <h2>Kindergarten</h2>
        <nav>
          <ul>
            <li><NavLink to="/" end>Главная</NavLink></li>
            <li><NavLink to="/teachers">Учителя</NavLink></li>
            <li><NavLink to="/parents">Родители</NavLink></li>
            <li><NavLink to="/children">Дети</NavLink></li>
            <li><NavLink to="/groups">Группы</NavLink></li>
            <li><NavLink to="/payments">Платежи</NavLink></li>
            <li><NavLink to="/age-groups">Возрастные группы</NavLink></li>
            <li><NavLink to="/history">История групп</NavLink></li>
            <li><NavLink to="/users">Пользователи</NavLink></li>
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
