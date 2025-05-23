import React, { useEffect, useState } from "react";
import { getPayments, deletePayment } from "../api/paymentService";
import { getTenantIdFromSubdomain } from "../utils/getTenantId";
import "../styles/AdminBooking.css";
import "../styles/AdminAirportPage.css";

function AdminPayments() {
  const [payments, setPayments] = useState([]);
  const tenantId = getTenantIdFromSubdomain();
  const token = localStorage.getItem("token"); // assuming token is stored in localStorage

  useEffect(() => {
    fetchPayments();
  }, []);

  const fetchPayments = () => {
    getPayments(token, tenantId)
      .then((res) => {
        const data = Array.isArray(res.data) ? res.data : res.data.payments;
        console.log(res);
        setPayments(data);
      })
      .catch((err) => console.error("Error fetching payments:", err));
  };

  const handleDeletePayment = (reference) => {
    if (window.confirm("Are you sure you want to delete this payment?")) {
      deletePayment(reference, token, tenantId)
        .then(() => {
          setPayments((prev) => prev.filter((p) => p.reference !== reference));
        })
        .catch((err) => console.error("Error deleting payment:", err));
    }
  };

  return (
    <div className="adminpayments-container">
      <aside className="airport-sidebar">
        <div className="airport-logo">MBI RE</div>
        <nav className="airport-nav-group">
          {[
            'dashboard', 'airport', 'booking', 'faqs', 'flightspage',
            'maintenance', 'passangers', 'payments', 'staff', 'support',
            'announcements', 'city', 'languages', 'trending', 'policy', 'gate', 'terminal'
          ].map((item) => (
            <div className="airport-nav-row" key={item}>
              <a href={`/admin/${item}`}>{item.toUpperCase()}</a>
            </div>
          ))}
        </nav>
      </aside>

      <main className="adminpayments-content">
        <header className="adminbooking-header">
          <h2>PAYMENTS</h2>
          <div className="adminbooking-title">ADMIN</div>
        </header>

        <div className="adminpayments-table-container">
          <table className="adminpayments-table">
            <thead>
              <tr>
                <th>Method</th>
                <th>Amount</th>
                <th>Status</th>
                <th>Reference</th>
                <th>Actions</th>
              </tr>
            </thead>
            <tbody>
              {payments.length > 0 ? (
                payments.map((p, i) => (
                  <tr key={p.reference || i}>
                    <td>{p.method}</td>
                    <td>{p.amount}</td>
                    <td>{p.status}</td>
                    <td>{p.reference}</td>
                    <td>
                      <button onClick={() => handleDeletePayment(p.reference)}>
                        Delete
                      </button>
                    </td>
                  </tr>
                ))
              ) : (
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
