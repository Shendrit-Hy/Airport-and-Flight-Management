import React, { useEffect, useState } from "react";
import "../styles/AdminBooking.css";
import axios from "axios";

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
    <div className="adminbooking-container">
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
