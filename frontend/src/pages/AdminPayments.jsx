import React, { useEffect, useState } from 'react';
import "../styles/AdminBooking.css";
import axios from 'axios';

function AdminPayments() {
  const [payments, setPayments] = useState([
    {
      id: 1,
      method: 'Credit Card',
      amount: '$299.99',
      time: '2025-05-16 14:30',
      reference: 'TXN123456789'
    }
  ]);

  useEffect(() => {
    axios.get('/api/payments') // Replace with your real API
      .then(response => {
        const data = Array.isArray(response.data)
          ? response.data
          : response.data.payments;

        if (Array.isArray(data) && data.length > 0) {
          setPayments(data);
        }
      })
      .catch(error => {
        console.error('Error fetching payments:', error);
      });
  }, []);

  const handleDelete = (id) => {
    const updated = payments.filter((payment, index) =>
      payment.id ? payment.id !== id : index !== id
    );
    setPayments(updated);

    // Optionally: send DELETE request
    // axios.delete(`/api/payments/${id}`)
    //   .then(() => console.log('Deleted'))
    //   .catch(err => console.error('Delete failed', err));
  };

  return (
    <div className="admin-container">
      <div className="sidebar">
        <img src="/logo.png" alt="Logo" className="logo" />
        <button className="side-button">DASHBOARD</button>
        <button className="side-button">SEARCH</button>
      </div>

      <div className="content payments-container">
        <div className="admin-ribbon">
          <span className="admin-label">ADMIN</span>
        </div>

        <h2 className="bookings-title">PAYMENTS</h2>

        <div className="bookings-table-container">
          <table className="bookings-table">
            <thead>
              <tr>
                <th>Method</th>
                <th>Amount</th>
                <th>Payment time</th>
                <th>Reference</th>
                <th>Action</th>
              </tr>
            </thead>
            <tbody>
              {payments.map((payment, index) => (
                <tr key={payment.id || index}>
                  <td>{payment.method}</td>
                  <td>{payment.amount}</td>
                  <td>{payment.time}</td>
                  <td>{payment.reference}</td>
                  <td>
                    <button
                      className="delete-button"
                      onClick={() => handleDelete(payment.id || index)}
                    >
                      Delete
                    </button>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      </div>
    </div>
  );
}

export default AdminPayments;
