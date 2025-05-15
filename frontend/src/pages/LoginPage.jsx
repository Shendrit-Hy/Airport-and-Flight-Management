import React from 'react';
import { Link } from 'react-router-dom';
import '../styles/auth.css';
import loginImage from '../assets/login.webp';
import { useFormik } from 'formik';

export default function LoginPage() {
  const formik = useFormik({
    initialValues: {
      username: '',
      password: '',
    },
    onSubmit: (values) => {
      console.log('Logging in with:', values);
      // navigate("/dashboard") – mund të shtosh më vonë
    },
  });

  return (
    <div className="auth-container">
      <div className="auth-left">
        <img src={loginImage} alt="Plane" />
      </div>
      <div className="auth-right">
        <div className="form-wrapper">
          <h2>Log In</h2>
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
