import React, { useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import '../styles/auth.css';
import signupImage from '../assets/signup.jpg';
import { useFormik } from 'formik';
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
    <div className="auth-container">
      <div className="auth-left">
        <div className="form-wrapper">
          <h2>Sign Up</h2>
          {error && <p style={{ color: 'red' }}>{error}</p>}
          <form onSubmit={formik.handleSubmit}>
            <input
              type="text"
              name="fullName"
              placeholder="Full Name"
              onChange={formik.handleChange}
              value={formik.values.fullName}
              required
            />
            <input
              type="text"
              name="username"
              placeholder="Username"
              onChange={formik.handleChange}
              value={formik.values.username}
              required
            />
            <input
              type="email"
              name="email"
              placeholder="Email Address"
              onChange={formik.handleChange}
              value={formik.values.email}
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
            <select
              name="country"
              onChange={formik.handleChange}
              value={formik.values.country}
              required
            >
              <option value="">Select a country--</option>
              <option value="Germany">Germany</option>
              <option value="Kosovo">Kosovo</option>
              <option value="Albania">Albania</option>
            </select>
            <button type="submit">Sign Up</button>
          </form>
          <p>
            Already have an account? <Link to="/login">Log in</Link>
          </p>
        </div>
      </div>
      <div className="auth-right">
        <img src={signupImage} alt="Plane" />
      </div>
    </div>
  );
}
