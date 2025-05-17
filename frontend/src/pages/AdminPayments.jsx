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
      <aside className="adminpayments-sidebar">
        <img src="/logo.png" alt="Logo" className="adminpayments-logo" />
        <button className="adminpayments-side-button">DASHBOARD</button>
        <button className="adminpayments-side-button">SEARCH</button>
        <button className="adminpayments-side-button">PAYMENTS</button>
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
