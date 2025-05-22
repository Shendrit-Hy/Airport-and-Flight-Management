import React, { useEffect, useState } from 'react';
import '../styles/AdminSupportPage.css';
import { getSupportRequests, deleteSupportRequest } from '../api/supportService';
import { getTenantIdFromSubdomain } from '../utils/getTenantId'; // adjust path if needed
import "../styles/AdminAirportPage.css"


export default function AdminSupportPage() {
  const [tickets, setTickets] = useState([]);

  const token = localStorage.getItem('token');
  const tenantId = getTenantIdFromSubdomain();

  useEffect(() => {
    loadTickets();
  }, []);

  const loadTickets = async () => {
    try {
      const res = await getSupportRequests(token, tenantId);
      console.log(res)
      setTickets(res.data);
    } catch (error) {
      console.error('Error loading support tickets:', error);
    }
  };

  const handleDelete = async (id) => {
    try {
      await deleteSupportRequest(id, token, tenantId);
      loadTickets();
    } catch (error) {
      console.error('Error deleting support ticket:', error);
    }
  };

  return (
    <div className="adminsupport-layout">
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

          </div>
        </nav>
      </aside>

      <main className="adminsupport-main-content">
        <header className="adminsupport-header">
          <h2>SUPPORT TICKETS</h2>
          <div className="adminsupport-title">ADMIN</div>
        </header>

        <div className="adminsupport-table">
          <div className="adminsupport-table-header">
            <span>Ticket</span>
            <span>Flight</span>
            <span>Issue Type</span>
            <span>Subject</span>
            <span>Reported By</span>
            <span>Status</span>
            <span>Description</span>
            <span>Actions</span>
          </div>

          {tickets.map((ticket) => (
            <div className="adminsupport-table-row" key={ticket.id}>
              <span>{ticket.ticketId}</span>
              <span>{ticket.flight}</span>
              <span>{ticket.type}</span>
              <span>{ticket.subject}</span>
              <span>{ticket.email}</span>
              <span>{ticket.status}</span>
              <span>{ticket.message}</span>
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