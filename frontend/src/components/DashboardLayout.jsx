import React from 'react';
import './DashboardLayout.css';
import { useNavigate } from 'react-router-dom';
import { useAuth } from '../context/AuthContext';
import "../styles/AdminAirportPage.css";

function DashboardLayout() {
  const navigate = useNavigate();
  const { user } = useAuth();

  return (
    <div className="dashboard-container">
    <aside className="airport-sidebar">
        <div className="airport-logo">MBI RE</div>
        <nav className="airport-nav-group">
          {[
            'dashboard', 'airport', 'booking', 'faqs', 'flightspage',
            'maintenance', 'passangers', 'payments', 'staff', 'support',
            'announcements', 'city', 'languages', 'trending', 'policy', 'gate','terminal'
          ].map((item) => (
            <div className="airport-nav-row" key={item}>
              <a href={`/admin/${item}`}>{item.toUpperCase()}</a>
            </div>
          ))}
        </nav>
      </aside>

      <div className="main">
        <header className="topbar">
          ADMIN
        </header>

        <div className="content">
          <div className="dashboard-cards">
            <div className="card card1" onClick={() => navigate('/admin/airport')}>AIRPORT</div>
            <div className="card card2" onClick={() => navigate('/admin/flightspage')}>FLIGHTS</div>
            <div className="card card3" onClick={() => navigate('/admin/maintenance')}>MAINTENANCE</div>
            <div className="card card4" onClick={() => navigate('/admin/staff')}>STAFF</div>
          </div>

        </div>
      </div>
    </div>
  );
}

export default DashboardLayout;
