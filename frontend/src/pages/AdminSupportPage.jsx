import React, { useEffect, useState } from 'react';
import '../styles/AdminSupportPage.css';
// import {
//   getSupportTickets,
//   createSupportTicket,
//   deleteSupportTicket
// } from '../api/supportService';

export default function AdminSupportPage() {
  const [tickets, setTickets] = useState([]);
  const [newTicket, setNewTicket] = useState({
    message: '',
    reportedBy: '',
    priority: '',
    status: '',
    description: '',
    location: '',
    issueType: ''
  });

  useEffect(() => {
    loadTickets();
  }, []);

  const loadTickets = async () => {
    const res = await getSupportTickets();
    setTickets(res.data);
  };

  const handleChange = (e) => {
    setNewTicket({ ...newTicket, [e.target.name]: e.target.value });
  };

  const handleAdd = async (e) => {
    e.preventDefault();
    await createSupportTicket(newTicket);
    setNewTicket({
      message: '',
      reportedBy: '',
      priority: '',
      status: '',
      description: '',
      location: '',
      issueType: ''
    });
    loadTickets();
  };

  const handleDelete = async (id) => {
    await deleteSupportTicket(id);
    loadTickets();
  };

  return (
    <div className="admin-layout">
      <aside className="sidebar">
        <div className="logo">MBI RE</div>
        <nav>
          <a href="/admin/dashboard">DASHBOARD</a>
          <a href="/admin/search">SEARCH</a>
          <a href="/admin/support" className="active">SUPPORT TICKETS</a>
        </nav>
      </aside>

      <main className="main-content">
        <header className="admin-header">
          <h2>SUPPORT TICKETS</h2>
          <div className="admin-title">ADMIN</div>
        </header>

        {/* Form */}
        <form className="flight-add-form" onSubmit={handleAdd}>
          <div className="input-group full-width">
            <label htmlFor="message">Message</label>
            <textarea
              id="message"
              name="message"
              value={newTicket.message}
              onChange={handleChange}
              required
            />
          </div>

          <div className="form-grid">
            {[
              { name: 'reportedBy', label: 'Reported By' },
              { name: 'priority', label: 'Priority' },
              { name: 'status', label: 'Status' },
              { name: 'description', label: 'Description' },
              { name: 'location', label: 'Location' },
              { name: 'issueType', label: 'Issue Type' }
            ].map((field) => (
              <div className="input-group" key={field.name}>
                <label htmlFor={field.name}>{field.label}</label>
                <input
                  type="text"
                  id={field.name}
                  name={field.name}
                  value={newTicket[field.name]}
                  onChange={handleChange}
                  required
                />
              </div>
            ))}
          </div>

          <button type="submit" className="add-btn">ADD</button>
        </form>

        {/* Table */}
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

          {tickets.map((ticket) => (
            <div className="table-row" key={ticket.id}>
              <span>{ticket.airportId}</span>
              <span>{ticket.location}</span>
              <span>{ticket.issueType}</span>
              <span>{ticket.reportedBy}</span>
              <span>{ticket.priority}</span>
              <span>{ticket.status}</span>
              <span>{ticket.description}</span>
              <span>
                <button className="delete-btn" onClick={() => handleDelete(ticket.id)}>🗑</button>
              </span>
            </div>
          ))}
        </div>
      </main>
    </div>
  );
}
