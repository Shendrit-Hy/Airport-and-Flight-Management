import React from 'react';
import { useAuth } from '../context/AuthContext';
import '../styles/userProfile.css';

export default function UserProfile() {
  const { user } = useAuth();

  return (
    <div className="userprofile-page">
      <aside className="userprofile-sidebar">
        <div className="userprofile-logo">LOGO</div>
        <div className="userprofile-sidebar-item">Profile</div>
      </aside>

      <div className="userprofile-main">
        <header className="userprofile-topbar">
          <span>User</span>
        </header>

        <div className="userprofile-container">
          <div className="userprofile-left">
            <button className="userprofile-btn">USERNAME</button>
            <button className="userprofile-btn userprofile-update">UPDATE YOUR USERNAME</button>
            <button className="userprofile-btn">FULL NAME</button>
            <button className="userprofile-btn">EMAIL</button>
          </div>

          <div className="userprofile-right">
            <div className="userprofile-flight-box">
              <p>NUMRI I<br />FLUTURIMEVE</p>
              <h3>7</h3>
            </div>
          </div>
        </div>

        <div className="userprofile-table">
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
