import React, { useEffect, useState } from 'react';
import '../styles/AdminMaintenancePage.css';
import "../styles/AdminAirportPage.css"

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

    const payload = {
      airportCode: newItem.airportId, // backend pret "airportCode"
      location: newItem.location,
      issueType: newItem.issueType,
      reportedBy: newItem.reportedBy,
      priority: newItem.priority,
      status: newItem.status,
      description: newItem.description
    };

    await createMaintenance(payload);

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
    <div className="adminmaintenance-layout">
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
            <a href="/admin/faqs">FAQ'S</a>
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
      </aside>

      <main className="adminmaintenance-main-content">
        <header className="adminmaintenance-header">
          <h2>MAINTENANCE</h2>
          <div className="adminmaintenance-title">ADMIN</div>
        </header>

        <form className="adminmaintenance-form" onSubmit={handleAdd}>
          <div className="adminmaintenance-form-grid">
            {[
              { name: 'airportId', label: 'Airport ID' },
              { name: 'location', label: 'Location' },
              { name: 'issueType', label: 'Issue Type' },
              { name: 'reportedBy', label: 'Reported By' },
              { name: 'priority', label: 'Priority' },
              { name: 'status', label: 'Status' },
              { name: 'description', label: 'Description' }
            ].map((field) => (
              <div className="adminmaintenance-input-group" key={field.name}>
                {/* <label htmlFor={field.name}>{field.label}</label> */}
                <input
                  type="text"
                  id={field.name}
                  name={field.name}
                  value={newItem[field.name]}
                  onChange={handleChange}
                  required
                  placeholder={field.label}
                  style={{ backgroundColor: 'rgb(53,53,53)' }}                 />
              </div>
            ))}
          </div>
          <button type="submit" className="adminmaintenance-add-btn">ADD</button>
        </form>

        <div className="adminmaintenance-table">
          <div className="adminmaintenance-table-header">
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
            <div className="adminmaintenance-table-row" key={item.id}>
              <span>{item.airportCode}</span> {/* saktësisht sipas emrit nga backend */}
              <span>{item.location}</span>
              <span>{item.issueType}</span>
              <span>{item.reportedBy}</span>
              <span>{item.priority}</span>
              <span>{item.status}</span>
              <span>{item.description}</span>
              <span>
                <button className="adminmaintenance-delete-btn" onClick={() => handleDelete(item.id)}>🗑</button>
              </span>
            </div>
          ))}
        </div>
      </main>
    </div>
  );
}
