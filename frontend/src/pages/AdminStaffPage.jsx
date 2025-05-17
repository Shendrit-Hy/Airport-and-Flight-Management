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
    <div className="adminstaff-layout">
      <aside className="adminstaff-sidebar">
        <div className="adminstaff-logo">MBI RE</div>
        <nav className="adminstaff-nav">
          <div className="adminstaff-nav-row">
            <a href="/admin/dashboard">DASHBOARD</a>
            <a href="/admin/flights">FLIGHTS</a>
          </div>
          <div className="adminstaff-nav-row">
            <a href="/admin/staff" className="active">STAFF</a>
          </div>
        </nav>
      </aside>

      <main className="adminstaff-main">
        <header className="adminstaff-header">
          <h2>STAFF</h2>
          <div className="adminstaff-title">ADMIN</div>
        </header>

        <form className="adminstaff-add-form" onSubmit={handleAddStaff}>
          <div className="adminstaff-form-left">
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
          <button type="submit" className="adminstaff-add-btn">ADD</button>
        </form>

        <div className="adminstaff-table">
          <div className="adminstaff-table-header">
            <span>Full Name</span>
            <span>Role</span>
            <span>Email</span>
            <span>Shift Time</span>
            <span>Actions</span>
          </div>

          {staffList.map((staff) => (
            <div className="adminstaff-table-row" key={staff.id}>
              <span>{staff.fullName}</span>
              <span>{staff.role}</span>
              <span>{staff.email}</span>
              <span>{staff.shiftTime}</span>
              <span>
                <button onClick={() => handleDelete(staff.id)} className="adminstaff-delete-btn">🗑</button>
              </span>
            </div>
          ))}
        </div>
      </main>
    </div>
  );
}
