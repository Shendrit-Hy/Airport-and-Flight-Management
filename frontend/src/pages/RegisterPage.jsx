import React, { useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { Formik, Form, Field, ErrorMessage } from 'formik';
import * as Yup from 'yup';
import '../styles/auth.css';
import registerImage from '../assets/signup.jpg';
import { register } from '../api/api';
import { getTenantIdFromSubdomain } from '../utils/getTenantId';

export default function RegisterPage() {
  const navigate = useNavigate();
  const tenantId = getTenantIdFromSubdomain();
  const [error, setError] = useState('');

  const validationSchema = Yup.object({
    fullName: Yup.string().required('Full name is required'),
    username: Yup.string().required('Username is required'),
    email: Yup.string().email('Invalid email').required('Email is required'),
    password: Yup.string().min(6, 'Minimum 6 characters').required('Password is required'),
    country: Yup.string().required('Country is required'),
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
            <Formik
              initialValues={{
                fullName: '',
                username: '',
                email: '',
                password: '',
                country: '',
              }}
              validationSchema={validationSchema}
              onSubmit={async (values, { setSubmitting }) => {
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
                } finally {
                  setSubmitting(false);
                }
              }}
            >
              <Form className="register-form">
                <Field
                  className="register-input"
                  type="text"
                  name="fullName"
                  placeholder="Full Name"
                />
                <ErrorMessage name="fullName" component="div" className="register-error" />

                <Field
                  className="register-input"
                  type="text"
                  name="username"
                  placeholder="Username"
                />
                <ErrorMessage name="username" component="div" className="register-error" />

                <Field
                  className="register-input"
                  type="email"
                  name="email"
                  placeholder="Email"
                />
                <ErrorMessage name="email" component="div" className="register-error" />

                <Field
                  className="register-input"
                  type="password"
                  name="password"
                  placeholder="Password"
                />
                <ErrorMessage name="password" component="div" className="register-error" />

                <Field
                  className="register-input"
                  type="text"
                  name="country"
                  placeholder="Country"
                />
                <ErrorMessage name="country" component="div" className="register-error" />

                <button className="register-submit-btn" type="submit">Sign Up</button>
              </Form>
            </Formik>
            <p className="register-footer">
              Already have an account? <Link to="/login">Log in</Link>
            </p>
          </div>
        </div>
      </div>
    </div>
  );
}
