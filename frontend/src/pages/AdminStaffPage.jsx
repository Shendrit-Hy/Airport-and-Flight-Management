import React, { useEffect, useState } from 'react';
import '../styles/AdminStaffPage.css';
import {
  getStaffList,
  deleteStaffById,
  addStaff
} from '../api/staffService';

export default function AdminStaffPage() {
  const [staffList, setStaffList] = useState([]);
  const [newStaff, setNewStaff] = useState({
    fullName: '',
    role: '',
    email: '',
    shiftTime: ''
  });

  useEffect(() => {
    loadStaff();
  }, []);

  const loadStaff = async () => {
    const data = await getStaffList();
    setStaffList(data);
  };

  const handleDelete = async (id) => {
    await deleteStaffById(id);
    loadStaff();
  };

  const handleChange = (e) => {
    setNewStaff({ ...newStaff, [e.target.name]: e.target.value });
  };

  const handleAddStaff = async (e) => {
    e.preventDefault();
    await addStaff(newStaff);
    setNewStaff({ fullName: '', role: '', email: '', shiftTime: '' });
    loadStaff();
  };

  return (
    <div className="admin-layout">
      <aside className="sidebar">
        <div className="logo">MBI RE</div>
        <nav>
          <a href="/admin/dashboard">DASHBOARD</a>
          <a href="/admin/flights">FLIGHTS</a>

        </nav>
      </aside>

      <main className="main-content">
        <header className="admin-header">
          <h2>STAFF</h2>
          <div className="admin-title">ADMIN</div>
        </header>

        {/* Forma për shtim të stafit */}
        <form className="staff-add-form" onSubmit={handleAddStaff}>
          <div className="form-left">
            <input
              type="text"
              name="fullName"
              placeholder="Full Name"
              value={newStaff.fullName}
              onChange={handleChange}
              required
            />
            <input
              type="text"
              name="shiftTime"
              placeholder="Shift Time"
              value={newStaff.shiftTime}
              onChange={handleChange}
              required
            />
            <input
              type="text"
              name="role"
              placeholder="Role"
              value={newStaff.role}
              onChange={handleChange}
              required
            />
            <input
              type="email"
              name="email"
              placeholder="Email"
              value={newStaff.email}
              onChange={handleChange}
              required
            />
          </div>
          <button type="submit" className="add-btn">ADD</button>
        </form>

        <div className="staff-table">
          <div className="table-header">
            <span>Full Name</span>
            <span>Role</span>
            <span>Email</span>
            <span>Shift Time</span>
            <span>Actions</span>
          </div>

          {staffList.map((staff) => (
            <div className="table-row" key={staff.id}>
              <span>{staff.fullName}</span>
              <span>{staff.role}</span>
              <span>{staff.email}</span>
              <span>{staff.shiftTime}</span>
              <span>
                <button onClick={() => handleDelete(staff.id)} className="delete-btn">🗑</button>
              </span>
            </div>
          ))}
        </div>
      </main>
    </div>
  );
}
