import React from 'react';
import { useAuth } from '../context/AuthContext';
import '../styles/userProfile.css';

export default function UserProfile() {
  const { user } = useAuth();

  return (
    <div className="profile-page">
      <aside className="sidebar">
        <div className="logo">LOGO</div>
        <div className="sidebar-item">Profile</div>
      </aside>

      <div className="main-content">
        <header className="topbar">
          <span>User</span>
        </header>

        <div className="profile-container">
          <div className="left-section">
            <button className="profile-btn">USERNAME</button>
            <button className="update-btn">UPDATE YOUR USERNAME</button>
            <button className="profile-btn">FULL NAME</button>
            <button className="profile-btn">EMAIL</button>
          </div>

          <div className="right-section">
            <div className="flight-box">
              <p>NUMRI I<br />FLUTURIMEVE</p>
              <h3>7</h3> {/* këtë mund ta bësh dinamik më vonë */}
            </div>
          </div>
        </div>

        <div className="flight-table">
          <table>
            <thead>
              <tr>
                <th>Flight No</th>
                <th>Origin</th>
                <th>Destination</th>
                <th>Departure</th>
                <th>Arrival</th>
              </tr>
            </thead>
            <tbody>
              <tr>
                <td>MB101</td><td>Prishtina</td><td>Zurich</td><td>07:30</td><td>09:15</td>
              </tr>
              <tr>
                <td>MB202</td><td>Tirana</td><td>Berlin</td><td>10:00</td><td>12:45</td>
              </tr>
            </tbody>
          </table>
        </div>
      </div>
    </div>
  );
}
