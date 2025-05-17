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
    <div className="login-wrapper">
      <div className="login-container">
        <div className="login-left">
          <img src={loginImage} alt="Plane" />
        </div>

        <div className="login-right">
          <button
            type="button"
            className="login-back-icon-button"
            onClick={() => navigate(-1)}
          >
            ←
          </button>

          <div className="login-form-wrapper">
            <h2>Log In</h2>
            {error && <p className="login-error">{error}</p>}
            <form className="login-form" onSubmit={formik.handleSubmit}>
              <input
                className="login-input"
                type="text"
                name="username"
                placeholder="Username"
                onChange={formik.handleChange}
                value={formik.values.username}
                required
              />
              <input
                className="login-input"
                type="password"
                name="password"
                placeholder="Password"
                onChange={formik.handleChange}
                value={formik.values.password}
                required
              />
              <button className="login-submit-btn" type="submit">Log In</button>
            </form>
            <p className="login-footer">
              Don’t have an account? <Link to="/signup">Sign up</Link>
            </p>
          </div>
        </div>
      </div>
    </div>
  );
}
