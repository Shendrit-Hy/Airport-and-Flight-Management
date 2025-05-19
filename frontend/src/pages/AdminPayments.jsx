import React, { useEffect, useState } from "react";
import axios from "axios";
import "../styles/AdminBooking.css"; // CSS e përbashkët për admin pages

function AdminPayments() {
  const [payments, setPayments] = useState([]);

  useEffect(() => {
    axios.get("/api/payments")
      .then((res) => {
        const data = Array.isArray(res.data) ? res.data : res.data.payments;
        setPayments(Array.isArray(data) ? data : []);
      })
      .catch((err) => console.error("Error fetching payments:", err));
  }, []);

  const handleDelete = (id) => {
    const updated = payments.filter((p, i) => p.id ? p.id !== id : i !== id);
    setPayments(updated);
  };

  return (
    <div className="adminpayments-container">
      <aside className="adminbooking-sidebar">
        <div className="airport-logo">MBI RE</div>
        <nav className="airport-nav-group">
          <div className="airport-nav-row">
            <a href="/admin/dashboard">DASHBOARD</a> 
          </div>
          <div className="airport-nav-row">
            <a href="/admin/search">SEARCH</a>
          </div>
          <div className="airport-nav-row">
            <a href="/admin/airport" className="active">STAFF</a>
          </div>
          <div className="airport-nav-row">
            <a href="/admin/dashboard">BOOKING</a> 
          </div>
          <div className="airport-nav-row">
            <a href="/admin/search">MAINTENANCE</a>
          </div>
          <div className="airport-nav-row">
            <a href="/admin/airport" className="active">AIRPORT</a>
          </div>
          <div className="airport-nav-row">
            <a href="/admin/dashboard">SUPPORT</a> 
          </div>
          <div className="airport-nav-row">
            <a href="/admin/search">PAYMENTS</a>
          </div>
          <div className="airport-nav-row">
            <a href="/admin/airport" className="active">PASSANGERS</a>
          </div>
        </nav>
      </aside>

      <main className="adminpayments-content">
        <div className="adminpayments-ribbon">
          <span className="adminpayments-label">ADMIN</span>
        </div>
        <h2 className="adminpayments-title">PAYMENTS</h2>

        <div className="adminpayments-table-container">
          <table className="adminpayments-table">
            <thead>
              <tr>
                <th>Card Number</th>
                <th>Amount</th>
                <th>Status</th>
                <th>Booking</th>
                <th>Action</th>
              </tr>
            </thead>
            <tbody>
              {Array.isArray(payments) && payments.map((p, i) => (
                <tr key={p.id || i}>
                  <td>{p.cardNumber}</td>
                  <td>{p.amount}</td>
                  <td>{p.status}</td>
                  <td>{p.bookingId}</td>
                  <td>
                    <button
                      className="adminpayments-delete-btn"
                      onClick={() => handleDelete(p.id || i)}
                    >
                      Delete
                    </button>
                  </td>
                </tr>
              ))}
              {payments.length === 0 && (
                <tr>
                  <td colSpan="5">No payments found.</td>
                </tr>
              )}
            </tbody>
          </table>
        </div>
      </main>
    </div>
  );
}

export default AdminPayments;
