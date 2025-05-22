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
    <div className="adminsupport-layout">
      <aside className="adminsupport-sidebar">
        <div className="adminsupport-logo">MBI RE</div>
        <nav className="adminsupport-nav">
          <div className="adminsupport-nav-row">
            <a href="/admin/dashboard">DASHBOARD</a>
            <a href="/admin/search">SEARCH</a>
          </div>
<<<<<<< Updated upstream
          <div className="adminsupport-nav-row">
            <a href="/admin/support" className="active">SUPPORT TICKETS</a>
=======
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
>>>>>>> Stashed changes
          </div>
        </nav>
      </aside>

      <main className="adminsupport-main-content">
        <header className="adminsupport-header">
          <h2>SUPPORT TICKETS</h2>
          <div className="adminsupport-title">ADMIN</div>
        </header>

        <form className="adminsupport-form" onSubmit={handleAdd}>
          <div className="adminsupport-input-group full-width">
            <label htmlFor="message">Message</label>
            <textarea
              id="message"
              name="message"
              value={newTicket.message}
              onChange={handleChange}
              required
            />
          </div>

          <div className="adminsupport-form-grid">
            {[
              { name: 'reportedBy', label: 'Reported By' },
              { name: 'priority', label: 'Priority' },
              { name: 'status', label: 'Status' },
              { name: 'description', label: 'Description' },
              { name: 'location', label: 'Location' },
              { name: 'issueType', label: 'Issue Type' }
            ].map((field) => (
              <div className="adminsupport-input-group" key={field.name}>
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

          <button type="submit" className="adminsupport-add-btn">ADD</button>
        </form>

        <div className="adminsupport-table">
          <div className="adminsupport-table-header">
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
            <div className="adminsupport-table-row" key={ticket.id}>
              <span>{ticket.airportId}</span>
              <span>{ticket.location}</span>
              <span>{ticket.issueType}</span>
              <span>{ticket.reportedBy}</span>
              <span>{ticket.priority}</span>
              <span>{ticket.status}</span>
              <span>{ticket.description}</span>
              <span>
                <button
                  className="adminsupport-delete-btn"
                  onClick={() => handleDelete(ticket.id)}
                >🗑</button>
              </span>
            </div>
          ))}
        </div>
      </main>
    </div>
  );
}
