import React, { useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { useFormik } from 'formik';
import '../styles/auth.css';
import loginImage from '../assets/login.webp';
import { login } from '../api/api';
import { getTenantIdFromSubdomain } from '../utils/getTenantId';

export default function LoginPage() {
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
        localStorage.setItem('token', token);
        localStorage.setItem('tenantId', tenantId);
        navigate('/profile');
      } catch (err) {
        console.error('Login error:', err);
        setError('Login failed! Please check your credentials.');
      }
    },
  });

  return (
    <div className="auth-container">
      <div className="auth-left">
        <img src={loginImage} alt="Plane" />
      </div>

          <div className="auth-right">
            <button
              type="button"
              className="back-icon-button"
              onClick={() => navigate(-1)}
            >
              ←
            </button>

        <div className="form-wrapper">
          <h2>Log In</h2>
          {error && <p style={{ color: 'red' }}>{error}</p>}
          <form onSubmit={formik.handleSubmit}>
            <input
              type="text"
              name="username"
              placeholder="Username"
              onChange={formik.handleChange}
              value={formik.values.username}
              required
            />
            <input
              type="password"
              name="password"
              placeholder="Password"
              onChange={formik.handleChange}
              value={formik.values.password}
              required
            />
            <button type="submit">Log In</button>
          </form>



          <p>
            Don’t have an account? <Link to="/signup">Sign up</Link>
          </p>
        </div>
      </div>
    </div>
  );
}
