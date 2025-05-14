import React from 'react';
import { Link } from 'react-router-dom';
import '../styles/auth.css';
import loginImage from '../assets/login.webp';

export default function LoginPage() {
  return (
    <div className="auth-container">
      <div className="auth-left">
        <img src={loginImage} alt="Plane" />
      </div>
      <div className="auth-right">
        <div className="form-wrapper">
          <h2>Log In</h2>
          <form>
            <input type="text" placeholder="Username" required />
            <input type="password" placeholder="Password" required />
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
