import React, { useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { useFormik } from 'formik';
import '../styles/auth.css';
import registerImage from '../assets/signup.jpg';
import { register } from '../api/api';
import { getTenantIdFromSubdomain } from '../utils/getTenantId';

export default function RegisterPage() {
  const navigate = useNavigate();
  const tenantId = getTenantIdFromSubdomain();
  const [error, setError] = useState('');

  const formik = useFormik({
    initialValues: {
      fullName: '',
      username: '',
      email: '',
      password: '',
      country: '',
    },
   onSubmit: async (values) => {
      try {
        await register(
          values.username,
          values.email,
          values.password,
          values.fullName,
          values.country,
          tenantId
        );
        navigate('/login');
      } catch (err) {
        console.error('Registration error:', err);
        setError('Registration failed! Please try again.');
      }
    },
  });

  return (
    <div className="register-wrapper">
      <div className="register-container">
        <div className="register-left">
          <img src={registerImage} alt="Airplane" />
        </div>

        <div className="register-right">
          <button
            type="button"
            className="register-back-icon-button"
            onClick={() => navigate(-1)}
          >
            ←
          </button>

          <div className="register-form-wrapper">
            <h2>Register</h2>
            {error && <p className="register-error">{error}</p>}
            <form className="register-form" onSubmit={formik.handleSubmit}>
              <input
                className="register-input"
                type="text"
                name="fullName"
                placeholder="Full Name"
                onChange={formik.handleChange}
                value={formik.values.fullName}
                required
              />
              <input
                className="register-input"
                type="text"
                name="username"
                placeholder="Username"
                onChange={formik.handleChange}
                value={formik.values.username}
                required
              />
              <input
                className="register-input"
                type="email"
                name="email"
                placeholder="Email"
                onChange={formik.handleChange}
                value={formik.values.email}
                required
              />
              <input
                className="register-input"
                type="password"
                name="password"
                placeholder="Password"
                onChange={formik.handleChange}
                value={formik.values.password}
                required
              />
              <button className="register-submit-btn" type="submit">Sign Up</button>
            </form>
            <p className="register-footer">
              Already have an account? <Link to="/login">Log in</Link>
            </p>
          </div>
        </div>
      </div>
    </div>
  );
}
