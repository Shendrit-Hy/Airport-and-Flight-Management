import React, { useEffect, useState } from 'react';

import "../styles/AdminBooking.css";
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
      <aside className="adminpassangers-sidebar">
        <img src="/logo.png" alt="Logo" className="adminpassangers-logo" />
        <button className="adminpassangers-side-button">DASHBOARD</button>
        <button className="adminpassangers-side-button">SEARCH</button>
        <button className="adminpassangers-side-button">PASSANGERS</button>
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
