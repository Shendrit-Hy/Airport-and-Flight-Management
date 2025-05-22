import React, { useEffect, useState } from 'react';
import axios from 'axios';
import "../styles/AdminBooking.css";

function AdminPassangers() {
  const [users, setUsers] = useState([]);

  useEffect(() => {
    axios.get('/api/users')
      .then(response => {
        const data = Array.isArray(response.data) ? response.data : response.data.users;
        setUsers(Array.isArray(data) ? data : []);
      })
      .catch(error => {
        console.error('Error fetching users:', error);
      });
  }, []);

  const handleDelete = (id) => {
    const updated = users.filter((user, index) =>
      user.id ? user.id !== id : index !== id
    );
    setUsers(updated);
  };

  return (
    <div className="adminpassangers-container">
<<<<<<< Updated upstream
      <aside className="adminpassangers-sidebar">
        <img src="/logo.png" alt="Logo" className="adminpassangers-logo" />
        <button className="adminpassangers-side-button">DASHBOARD</button>
        <button className="adminpassangers-side-button">SEARCH</button>
        <button className="adminpassangers-side-button">PASSANGERS</button>
=======
      <aside className="airport-sidebar">
        <div className="airport-logo">MBI RE</div>
        <nav className="airport-nav-group">
          <div className="airport-nav-row">
            <a href="/admin/dashboard">DASHBOARD</a>
          </div>
          <div className="airport-nav-row">
            <a href="/admin/airport">AIRPORT</a>
          </div>
          <div className="airport-nav-row">
            <a href="/admin/booking">BOOKING</a>
          </div>
          <div className="airport-nav-row">
            <a href="/admin/faqs">FAQS</a>
          </div>
          <div className="airport-nav-row">
            <a href="/admin/flightspage">FLIGHTS</a>
          </div>
          <div className="airport-nav-row">
            <a href="/admin/maintenance">MAINTENANCE</a>
          </div>
          <div className="airport-nav-row">
            <a href="/admin/passangers">PASSANGERS</a>
          </div>
          <div className="airport-nav-row">
            <a href="/admin/payments">PAYMENTS</a>
          </div>
          <div className="airport-nav-row">
            <a href="/admin/staff">STAFF</a>
          </div>
          <div className="airport-nav-row">
            <a href="/admin/support">SUPPORT</a>
          </div>
          <div className="airport-nav-row">
            <a href="/admin/announcements">ANNOUNCEMENTS</a>
          </div>
        </nav>
>>>>>>> Stashed changes
      </aside>

      <main className="adminpassangers-content">
        <div className="adminpassangers-ribbon">
          <span className="adminpassangers-label">ADMIN</span>
        </div>

        <h2 className="adminpassangers-title">PASSANGERS</h2>

        <div className="adminpassangers-table-container">
          <table className="adminpassangers-table">
            <thead>
              <tr>
                <th>Full Name</th>
                <th>Email</th>
                <th>Phone</th>
                <th>Nationality</th>
                <th>Action</th>
              </tr>
            </thead>
            <tbody>
              {Array.isArray(users) && users.length > 0 ? (
                users.map((user, index) => (
                  <tr key={user.id || index}>
                    <td>{user.fullName}</td>
                    <td>{user.email}</td>
                    <td>{user.phone}</td>
                    <td>{user.nationality}</td>
                    <td>
                      <button
                        className="adminpassangers-delete-btn"
                        onClick={() => handleDelete(user.id || index)}
                      >
                        Delete
                      </button>
                    </td>
                  </tr>
                ))
              ) : (
                <tr>
                  <td colSpan="5" style={{ textAlign: "center" }}>
                    No passengers found.
                  </td>
                </tr>
              )}
            </tbody>
          </table>
        </div>
      </main>
    </div>
  );
}

export default AdminPassangers;
