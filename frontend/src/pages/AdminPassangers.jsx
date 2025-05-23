import React, { useEffect, useState } from 'react';

import "../styles/AdminBooking.css";
import "../styles/AdminAirportPage.css"
import axios from '../utils/axiosInstance'; 
function AdminPassangers() {
  const [users, setUsers] = useState([]);


 useEffect(() => {
   axios.get('/api/passengers')
     .then(response => {
       setUsers(response.data);
     })
     .catch(error => {
       console.error('Error fetching passengers:', error);
     });
 }, []);

const handleDelete = (id) => {
  axios
    .delete(`/api/passengers/${id}`)
    .then(() => {
      const updated = users.filter(user => user.id !== id);
      setUsers(updated);
    })
    .catch(error => {
      console.error('Error deleting passenger:', error);
    });
};

  return (
    <div className="adminpassangers-container">

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

      <main className="adminpassangers-content">
        <header className="adminbooking-header">
          <h2>PASSANGERS</h2>
          <div className="adminbooking-title">ADMIN</div>
        </header>

        <div className="adminpassangers-table-container">
          <table className="adminpassangers-table">
            <thead>
              <tr>
                <th>Full Name</th>
                <th>Email</th>
                <th>Phone</th>
                <th>Age</th>
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
                    <td>{user.age}</td>
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
