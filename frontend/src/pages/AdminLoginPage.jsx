import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { Formik, Form, Field, ErrorMessage } from 'formik';
import * as Yup from 'yup';
import '../styles/auth.css';
import loginImage from '../assets/login.webp';
import { login } from '../api/api';
import { getTenantIdFromSubdomain } from '../utils/getTenantId';

export default function AdminLogin() {
  const navigate = useNavigate();
  const tenantId = getTenantIdFromSubdomain();
  const [error, setError] = useState('');

  return (
    <div className="adminlogin-container">
      <div className="adminlogin-left">
        <img src={loginImage} alt="Plane" />
      </div>
      <div className="adminlogin-right">
        <div className="adminlogin-form-wrapper">
          <h2>Admin Login</h2>
          {error && <p className="adminlogin-error">{error}</p>}
          <Formik
            initialValues={{ username: '', password: '' }}
            validationSchema={Yup.object({
              username: Yup.string().required('Username is required'),
              password: Yup.string().required('Password is required'),
            })}
            onSubmit={async (values, { setSubmitting }) => {
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
              } finally {
                setSubmitting(false);
              }
            }}
          >
            <Form className="adminlogin-form">
              <Field
                className="adminlogin-input"
                type="text"
                name="username"
                placeholder="Username"
              />
              <ErrorMessage name="username" component="div" className="adminlogin-error" />

              <Field
                className="adminlogin-input"
                type="password"
                name="password"
                placeholder="Password"
              />
              <ErrorMessage name="password" component="div" className="adminlogin-error" />

              <button className="adminlogin-submit-btn" type="submit">Log In</button>
            </Form>
          </Formik>
        </div>
      </div>
    </div>
  );
}
