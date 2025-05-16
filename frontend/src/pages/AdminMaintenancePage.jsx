import React, { useEffect, useState } from 'react';
import '../styles/MaintenancePage.css';
import {
  getMaintenances,
  createMaintenance,
  deleteMaintenance
} from '../api/maintenanceService';

export default function AdminMaintenancePage() {
  const [maintenances, setMaintenances] = useState([]);
  const [newItem, setNewItem] = useState({
    airportId: '',
    location: '',
    issueType: '',
    reportedBy: '',
    priority: '',
    status: '',
    description: ''
  });

  useEffect(() => {
    loadData();
  }, []);

  const loadData = async () => {
    const res = await getMaintenances();
    setMaintenances(res.data);
  };

  const handleChange = (e) => {
    setNewItem({ ...newItem, [e.target.name]: e.target.value });
  };

  const handleAdd = async (e) => {
    e.preventDefault();
    await createMaintenance(newItem);
    setNewItem({
      airportId: '',
      location: '',
      issueType: '',
      reportedBy: '',
      priority: '',
      status: '',
      description: ''
    });
    loadData();
  };

  const handleDelete = async (id) => {
    await deleteMaintenance(id);
    loadData();
  };

  return (
    <div className="admin-layout">
      <aside className="sidebar">
        <div className="logo">MBI RE</div>
        <nav>
          <a href="/admin/dashboard">DASHBOARD</a>
          <a href="/admin/search">SEARCH</a>
          <a href="/admin/maintenance" className="active">MAINTENANCE</a>
        </nav>
      </aside>

      <main className="main-content">
        <header className="admin-header">
          <h2>MAINTENANCE</h2>
          <div className="admin-title">ADMIN</div>
        </header>

        <form className="flight-add-form" onSubmit={handleAdd}>
          <div className="form-grid">
            {[
              { name: 'airportId', label: 'Airport ID' },
              { name: 'location', label: 'Location' },
              { name: 'issueType', label: 'Issue Type' },
              { name: 'reportedBy', label: 'Reported By' },
              { name: 'priority', label: 'Priority' },
              { name: 'status', label: 'Status' },
              { name: 'description', label: 'Description' }
            ].map((field) => (
              <div className="input-group" key={field.name}>
                <label htmlFor={field.name}>{field.label}</label>
                <input
                  type="text"
                  id={field.name}
                  name={field.name}
                  value={newItem[field.name]}
                  onChange={handleChange}
                  required
                />
              </div>
            ))}
          </div>
          <button type="submit" className="add-btn">ADD</button>
        </form>

        <div className="flights-table">
          <div className="table-header">
            <span>Airport ID</span>
            <span>Location</span>
            <span>Issue Type</span>
            <span>Reported By</span>
            <span>Priority</span>
            <span>Status</span>
            <span>Description</span>
            <span>Actions</span>
          </div>

          {maintenances.map((item) => (
            <div className="table-row" key={item.id}>
              <span>{item.airportId}</span>
              <span>{item.location}</span>
              <span>{item.issueType}</span>
              <span>{item.reportedBy}</span>
              <span>{item.priority}</span>
              <span>{item.status}</span>
              <span>{item.description}</span>
              <span>
                <button className="delete-btn" onClick={() => handleDelete(item.id)}>🗑</button>
              </span>
            </div>
          ))}
        </div>
      </main>
    </div>
  );
}
