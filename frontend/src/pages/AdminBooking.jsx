import React, { useEffect, useState } from 'react';
import "../styles/AdminBooking.css";
import axios from 'axios';

function AdminBooking() {
  const [bookings, setBookings] = useState([]);

  useEffect(() => {
    const token = localStorage.getItem("token"); // Ose merre nga AuthContext
    const tenantId = "airline1"; // ose import nga getTenantId()

    console.log("📡 Duke dërguar request për bookings me token:", token);

    axios.get('http://localhost:8080/api/bookings', {
      headers: {
        'Authorization': `Bearer ${token}`,
        'X-Tenant-ID': tenantId,
        'Content-Type': 'application/json'
      }
    })
      .then(response => {
        const data = Array.isArray(response.data)
          ? response.data
          : response.data.bookings;

        console.log("📦 Marrë nga backend:", data);
        setBookings(data || []);
      })
      .catch(error => {
        console.error(' Error fetching bookings:', error);
      });
  }, []);

  const handleDelete = async (id) => {
  const token = localStorage.getItem("token");
  const tenantId = "airline1";

  try {
    await axios.delete(`http://localhost:8080/api/bookings/${id}`, {
      headers: {
        'Authorization': `Bearer ${token}`,
        'X-Tenant-ID': tenantId
      }
    });

    // Fshi nga state vetëm nëse backend-i konfirmon fshirjen
    const updatedBookings = bookings.filter(booking => booking.id !== id);
    setBookings(updatedBookings);

    console.log("Booking u fshi me sukses.");
  } catch (error) {
    console.error("Fshirja dështoi:", error);
  }
};


  return (
    <div className="admin-container">
      <div className="sidebar">
        <img src="/logo.png" alt="Logo" className="logo" />
        <button className="side-button">DASHBOARD</button>
        <button className="side-button">SEARCH</button>
      </div>

      <div className="content bookings-container">
        <div className="admin-ribbon">
          <span className="admin-label">ADMIN</span>
        </div>

        <h2 className="bookings-title">BOOKINGS</h2>

        <div className="bookings-table-container">
          <table className="bookings-table">
            <thead>
              <tr>
                <th>Passenger name</th>
                <th>Flight number</th>
                <th>Seat number</th>
                <th>Booking time</th>
                <th>Status</th>
                <th>Price</th>
                <th>Action</th>
              </tr>
            </thead>
            <tbody>
              {bookings.map((booking, index) => (
                <tr key={booking.id || index}>
                  <td>{booking.passengerName}</td>
                  <td>{booking.flightNumber}</td>
                  <td>{booking.seatNumber}</td>
                  <td>{booking.bookingTime?.slice(0, 16).replace("T", " ")}</td>
                  <td>{booking.status}</td>
                  <td>{booking.price || "N/A"}</td>
                  <td>
                    <button
                      className="delete-button"
                      onClick={() => handleDelete(booking.id || index)}
                    >
                      Delete
                    </button>
                  </td>
                </tr>
              ))}
              {bookings.length === 0 && (
                <tr>
                  <td colSpan="7" style={{ textAlign: "center" }}>
                    No bookings found.
                  </td>
                </tr>
              )}
            </tbody>
          </table>
        </div>
      </div>
    </div>
  );
}

export default AdminBooking;
