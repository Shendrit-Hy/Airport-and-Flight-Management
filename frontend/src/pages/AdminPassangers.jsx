import React, { useEffect, useState } from 'react';
import "../styles/AdminBooking.css"; // reuse same CSS
import axios from 'axios';

function AdminPassangers() {
  const [users, setUsers] = useState([
    {
      id: 1,
      fullName: 'John Doe',
      email: 'john@example.com',
      phone: '+123456789',
      nationality: 'USA'
    }
  ]);

  useEffect(() => {
    axios.get('/api/users') // Replace with your real API
      .then(response => {
        const data = Array.isArray(response.data)
          ? response.data
          : response.data.users;

        if (Array.isArray(data) && data.length > 0) {
          setUsers(data);
        }
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

    // Optionally send DELETE request
    // axios.delete(`/api/users/${id}`)
    //   .then(() => console.log('Deleted'))
    //   .catch(err => console.error('Delete failed', err));
  };

  return (
    <div className="admin-container">
      <div className="sidebar">
        <img src="/logo.png" alt="Logo" className="logo" />
        <button className="side-button">DASHBOARD</button>
        <button className="side-button">SEARCH</button>
      </div>

      <div className="content users-container">
        <div className="admin-ribbon">
          <span className="admin-label">ADMIN</span>
        </div>

        <h2 className="bookings-title">PASSANGERS</h2>

        <div className="bookings-table-container">
          <table className="bookings-table">
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
              {users.map((user, index) => (
                <tr key={user.id || index}>
                  <td>{user.fullName}</td>
                  <td>{user.email}</td>
                  <td>{user.phone}</td>
                  <td>{user.nationality}</td>
                  <td>
                    <button
                      className="delete-button"
                      onClick={() => handleDelete(user.id || index)}
                    >
                      Delete
                    </button>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      </div>
    </div>
  );
}

export default AdminPassangers;
