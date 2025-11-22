import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import Layout from './components/Layout';
import Dashboard from './pages/Dashboard';
import Teachers from './pages/Teachers';
import Parents from './pages/Parents';
import Children from './pages/Children';
import Groups from './pages/Groups';
import Payments from './pages/Payments';
import AgeGroups from './pages/AgeGroups';
import History from './pages/History';
import Users from './pages/Users';
import Login from './pages/Login';
import './App.css';

function App() {
  return (
    <Router>
      <Routes>
        <Route path="/login" element={<Login />} />
        <Route path="/" element={<Layout />}>
          <Route index element={<Dashboard />} />
          <Route path="teachers" element={<Teachers />} />
          <Route path="parents" element={<Parents />} />
          <Route path="children" element={<Children />} />
          <Route path="groups" element={<Groups />} />
          <Route path="payments" element={<Payments />} />
          <Route path="age-groups" element={<AgeGroups />} />
          <Route path="history" element={<History />} />
          <Route path="users" element={<Users />} />
        </Route>
      </Routes>
    </Router>
  );
}

export default App;