import React, { useEffect, useState } from 'react';
import '../styles/AdminStaffPage.css';
import {
  getStaffList,
  deleteStaffById,
  addStaff
} from '../api/staffService';

import { getTenantIdFromSubdomain } from '../utils/getTenantId';

export default function AdminStaffPage() {
  const [staffList, setStaffList] = useState([]);
    const [newStaff, setNewStaff] = useState({
      name: '',
      role: '',
      email: '',
      shiftStart: '',
      shiftEnd: ''
    });

    const token = localStorage.getItem('token');
    const tenantId = getTenantIdFromSubdomain();

    useEffect(() => {
      if (token && tenantId) {
        loadStaff();
      }
    }, []);

    const loadStaff = async () => {
      try {
        const data = await getStaffList(tenantId, token);
        setStaffList(data.data);
      } catch (err) {
        console.error('Failed to load staff:', err);
      }
    };

  const handleDelete = async (id) => {
    try {
      await deleteStaffById(id, tenantId, token);
      loadStaff();
    } catch (err) {
      console.error('Failed to delete staff:', err);
    }
  };

  const handleChange = (e) => {
    setNewStaff({ ...newStaff, [e.target.name]: e.target.value });
  };

  const handleAddStaff = async (e) => {
    e.preventDefault();
    try {
      await addStaff(newStaff, tenantId, token);
      setNewStaff({ name: '', role: '', email: '', shiftStart: '', shiftEnd: '' });
      loadStaff();
    } catch (err) {
      console.error('Failed to add staff:', err);
    }
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
              name="name"
              placeholder="Full Name"
              value={newStaff.name}
              onChange={handleChange}
              required
            />
            <input
              type="time"
              name="shiftStart"
              placeholder="Shift Start"
              value={newStaff.shiftStart}
              onChange={handleChange}
              required
            />
            <input
              type="time"
              name="shiftEnd"
              placeholder="Shift End"
              value={newStaff.shiftEnd}
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
            <span>Shift Start</span>
            <span>Shift End</span>
            <span>Actions</span>
          </div>

          {staffList.map((staff) => (
            <div className="adminstaff-table-row" key={staff.id}>
              <span>{staff.name}</span>
              <span>{staff.role}</span>
              <span>{staff.email}</span>
              <span>{staff.shiftStart}</span>
              <span>{staff.shiftEnd}</span>
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
