import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { useFormik } from 'formik';
import '../styles/auth.css';
import loginImage from '../assets/login.webp';
import { login } from '../api/api';
import { getTenantIdFromSubdomain } from '../utils/getTenantId';

export default function AdminLogin() {
  const navigate = useNavigate();
  const tenantId = getTenantIdFromSubdomain();
  const [error, setError] = useState('');

  const formik = useFormik({
    initialValues: {
      username: '',
      password: '',
    },
    onSubmit: async (values) => {
      try {
        const response = await login(values.username, values.password, tenantId);
        const token = response.data.token;
        const payload = JSON.parse(atob(token.split('.')[1]));

        if (payload.role === 'ADMIN') {
          localStorage.setItem('token', token);
          localStorage.setItem('tenantId', tenantId);
          navigate('/admin/dashboard');
        } else {
          setError('Access denied: You are not an admin.');
        }
      } catch (err) {
        console.error('Login error:', err);
        setError('Login failed! Invalid credentials or tenant.');
      }
    },
  });

  return (
    <div className="adminlogin-container">
      <div className="adminlogin-left">
        <img src={loginImage} alt="Plane" />
      </div>
      <div className="adminlogin-right">
        <div className="adminlogin-form-wrapper">
          <h2>Admin Login</h2>
          {error && <p className="adminlogin-error">{error}</p>}
          <form onSubmit={formik.handleSubmit} className="adminlogin-form">
            <input
              className="adminlogin-input"
              type="text"
              name="username"
              placeholder="Username"
              onChange={formik.handleChange}
              value={formik.values.username}
              required
            />
            <input
              className="adminlogin-input"
              type="password"
              name="password"
              placeholder="Password"
              onChange={formik.handleChange}
              value={formik.values.password}
              required
            />
            <button className="adminlogin-submit-btn" type="submit">Log In</button>
          </form>
        </div>
      </div>
    </div>
  );
}
