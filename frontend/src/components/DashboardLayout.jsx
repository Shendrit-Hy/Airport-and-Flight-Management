import React from 'react';
import './DashboardLayout.css';
import { useNavigate } from 'react-router-dom';
import { useAuth } from '../context/AuthContext';

function DashboardLayout() {
  const navigate = useNavigate();
  const { user } = useAuth();

  return (
    <div className="dashboard-container">
      <aside className="sidebar">
        <div className="logo">MBI RE</div>
        <button className="menu-btn">DASHBOARD</button>
        <nav className="menu">
          <a href="#">FLIGHTS</a>
          <a href="#">TICKETS</a>
          <a href="#">ROUTES</a>
          <a href="#">USERS</a>
          <a href="#">REPORTS</a>
          <a href="#">SETTINGS</a>
        </nav>
      </aside>

      <div className="main">
        <header className="topbar">
          ADMIN – Welcome, {user?.username || 'Guest'} {}
        </header>

        <div className="content">
          <div className="dashboard-cards">
            <div className="card card1" onClick={() => navigate('/flights')}>FLIGHT OVERVIEW</div>
            <div className="card card2" onClick={() => navigate('/tickets')}>TICKETS SOLD</div>
            <div className="card card3" onClick={() => navigate('/routes')}>ROUTE MANAGEMENT</div>
            <div className="card card4" onClick={() => navigate('/support')}>SUPPORT TICKETS</div>
          </div>

          <div className="recent-flights">
            <h2>RECENT FLIGHTS</h2>
            <table>
              <thead>
                <tr>
                  <th>FLIGHT NO</th>
                  <th>ORIGIN</th>
                  <th>DESTINATION</th>
                  <th>DEPARTURE TIME</th>
                  <th>ARRIVAL TIME</th>
                </tr>
              </thead>
              <tbody>
                <tr><td>MB101</td><td>Nairobi</td><td>Dubai</td><td>10:00</td><td>16:00</td></tr>
                <tr><td>MB102</td><td>London</td><td>Accra</td><td>09:30</td><td>13:45</td></tr>
                <tr><td>MB103</td><td>Paris</td><td>Nairobi</td><td>11:15</td><td>17:00</td></tr>
              </tbody>
            </table>
          </div>
        </div>
      </div>
    </div>
  );
}

export default DashboardLayout;
