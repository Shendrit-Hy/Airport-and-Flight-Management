import React from 'react';
import { Link } from 'react-router-dom';
import '../styles/auth.css';
import signupImage from '../assets/signup.jpg';

export default function RegisterPage() {
  return (
    <div className="auth-container">
      <div className="auth-left">
        <div className="form-wrapper">
          <h2>Sign Up</h2>
          <form>
            <input type="text" placeholder="Full Name" required />
            <input type="text" placeholder="Username" required />
            <input type="email" placeholder="Email Address" required />
            <input type="password" placeholder="Password" required />
            <select required>
              <option>Select a country--</option>
              <option>Germany</option>
              <option>Kosovo</option>
              <option>Albania</option>
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
