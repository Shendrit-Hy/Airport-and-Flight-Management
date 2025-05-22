import React, { useEffect, useState } from "react";
import axios from "axios";
import {getTenantIdFromSubdomain} from "../utils/getTenantId"; // assume this returns the current tenantId
import "../styles/AdminBooking.css"; // shared CSS for admin pages
import "../styles/AdminAirportPage.css"


function AdminPayments() {
  const [payments, setPayments] = useState([]);
  const [form, setForm] = useState({
    method: "",
    amount: "",
    status: "",
    reference: "",
  });

  const tenantId = getTenantIdFromSubdomain();

  useEffect(() => {
    fetchPayments();
  }, []);

const fetchPayments = () => {
  console.log("TenantId sent to backend:", tenantId); // For debugging
  axios
    .get("http://localhost:8080/api/payments", {
      headers: { "X-Tenant-ID": tenantId },
    })
    .then((res) => {
      const data = Array.isArray(res.data) ? res.data : res.data.payments;
      setPayments(data);
    })
    .catch((err) => console.error("Error fetching payments:", err));
};

const handleDeletePayment = (reference) => {
  if (window.confirm("Are you sure you want to delete this payment?")) {
    axios
      .delete(`http://localhost:8080/api/payments/${reference}`, {
        headers: { "X-Tenant-ID": tenantId },
      })
      .then(() => {
         setPayments((prev) => (Array.isArray(prev) ? [...prev, res.data] : [res.data]));
      })
      .catch((err) => console.error("Error deleting payment:", err));
  }
};



  const handleInputChange = (e) => {
    setForm({ ...form, [e.target.name]: e.target.value });
  };

  const handleAddPayment = () => {
    if (!form.method || !form.amount || !form.status || !form.reference) {
      alert("Please fill in all fields.");
      return;
    }

    axios
      .post(
        "http://localhost:8080/api/payments",
        { ...form },
        { headers: { "X-Tenant-ID": tenantId } }
      )
      .then((res) => {
        setPayments((prev) => (Array.isArray(prev) ? [...prev, res.data] : [res.data]));
        setForm({ method: "", amount: "", status: "", reference: "" });
      })
      .catch((err) => console.error("Error saving payment:", err));
  };

  return (
    <div className="adminpayments-container">

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
      <main className="adminpayments-content">
        <header className="adminbooking-header">
          <h2>PAYMENTS</h2>
          <div className="adminbooking-title">ADMIN</div>
        </header>

        {/* Add Payment Form */}
        <div className="adminpayments-form">
          <input
            name="method"
            placeholder="Method (e.g., CREDIT_CARD)"
            value={form.method}
            onChange={handleInputChange}
          />
          <input
            name="amount"
            type="number"
            placeholder="Amount"
            value={form.amount}
            onChange={handleInputChange}
          />
          <input
            name="status"
            placeholder="Status (e.g., PAID)"
            value={form.status}
            onChange={handleInputChange}
          />
          <input
            name="reference"
            placeholder="Reference (Booking ID)"
            value={form.reference}
            onChange={handleInputChange}
          />
          <button className="adminpayments-add-btn" onClick={handleAddPayment}>
            Add Payment
          </button>
        </div>


        <div className="adminpayments-table-container">
          <table className="adminpayments-table">
            <thead>
              <tr>
                <th>Method</th>
                <th>Amount</th>
                <th>Status</th>
                <th>Reference</th>
              </tr>
            </thead>
            <tbody>
              {Array.isArray(payments) && payments.length > 0 ? (
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
