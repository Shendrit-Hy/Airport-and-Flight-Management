import React from 'react';
import { Link } from 'react-router-dom';
import '../styles/auth.css';
import signupImage from '../assets/signup.jpg';
import { useFormik } from 'formik';

export default function RegisterPage() {
  const formik = useFormik({
    initialValues: {
      fullName: '',
      username: '',
      email: '',
      password: '',
      country: '',
    },
    onSubmit: (values) => {
      console.log('Registering:', values);
    },
  });

  return (
    <div className="auth-container">
      <div className="auth-left">
        <div className="form-wrapper">
          <h2>Sign Up</h2>
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
