import React, { useEffect, useState } from "react";
import "../styles/AdminBooking.css";
import axios from "axios";

function AdminBooking() {
  const [bookings, setBookings] = useState([]);

  useEffect(() => {
    axios.get("/api/bookings")
      .then((response) => {
        const data = Array.isArray(response.data)
          ? response.data
          : response.data.bookings;
        setBookings(Array.isArray(data) ? data : []);
      })
      .catch((error) => console.error("Error fetching bookings:", error));
  }, []);

  const handleDelete = (id) => {
    const updated = bookings.filter((b, i) => b.id ? b.id !== id : i !== id);
    setBookings(updated);
  };

  return (
    <div className="adminbooking-container">
      <aside className="adminbooking-sidebar">
        <img src="/logo.png" alt="Logo" className="adminbooking-logo" />
        <button className="adminbooking-side-button">DASHBOARD</button>
        <button className="adminbooking-side-button">SEARCH</button>
        <button className="adminbooking-side-button">BOOKINGS</button>
      </aside>
      <main className="adminbooking-content">
        <div className="adminbooking-ribbon">
          <span className="adminbooking-label">ADMIN</span>
        </div>
        <h2 className="adminbooking-title">BOOKINGS</h2>

        <div className="adminbooking-table-container">
          <table className="adminbooking-table">
            <thead>
              <tr>
                <th>Passenger Name</th>
                <th>Flight Number</th>
                <th>Seat Number</th>
                <th>Booking Time</th>
                <th>Status</th>
                <th>Price</th>
                <th>Action</th>
              </tr>
            </thead>
            <tbody>
              {Array.isArray(bookings) && bookings.length > 0 ? (
                bookings.map((b, i) => (
                  <tr key={b.id || i}>
                    <td>{b.passengerName}</td>
                    <td>{b.flightNumber}</td>
                    <td>{b.seatNumber}</td>
                    <td>{b.bookingTime}</td>
                    <td>{b.status}</td>
                    <td>{b.price}</td>
                    <td>
                      <button
                        className="adminbooking-delete-btn"
                        onClick={() => handleDelete(b.id || i)}
                      >
                        Delete
                      </button>
                    </td>
                  </tr>
                ))
              ) : (
                <tr>
                  <td colSpan="7" style={{ textAlign: "center" }}>
                    No bookings found.
                  </td>
                </tr>
              )}
            </tbody>
          </table>
        </div>
      </main>
    </div>
  );
}

export default AdminBooking;
