import React, { useEffect, useState } from "react";
import "../styles/AdminBooking.css";
import "../styles/AdminAirportPage.css";
import { getAllBookings, deleteBooking } from "../api/bookingService";
import { getTenantIdFromSubdomain } from "../utils/getTenantId";

function AdminBooking() {
  const [bookings, setBookings] = useState([]);

  const token = localStorage.getItem("token");
  const tenantId = getTenantIdFromSubdomain();

  useEffect(() => {
    loadBookings();
  }, []);

  const loadBookings = async () => {
    try {
      const response = await getAllBookings(tenantId, token);
      const data = Array.isArray(response.data)
        ? response.data
        : response.data.bookings;

      setBookings(data || []);
    } catch (error) {
      console.error("Error fetching bookings:", error);
    }
  };

  const handleDelete = async (id) => {
    try {
      await deleteBooking(id, tenantId, token);
      setBookings((prev) => prev.filter((booking) => booking.id !== id));
      console.log("Booking deleted successfully.");
    } catch (error) {
      console.error("Deletion failed:", error);
    }
  };

  return (
    <div className="adminbooking-container">
      <aside className="airport-sidebar">
        <div className="airport-logo">MBI RE</div>
        <nav className="airport-nav-group">
          {[
            'dashboard', 'airport', 'booking', 'faqs', 'flightspage',
            'maintenance', 'passangers', 'payments', 'staff', 'support',
            'announcements', 'city', 'languages', 'trending', 'policy', 'gate','terminal'
          ].map((item) => (
            <div className="airport-nav-row" key={item}>
              <a href={`/admin/${item}`}>{item.toUpperCase()}</a>
            </div>
          ))}
        </nav>
      </aside>

      <main className="adminbooking-content">
        <header className="adminbooking-header">
          <h2>BOOKING</h2>
          <div className="adminbooking-title">ADMIN</div>
        </header>

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
                        onClick={() => handleDelete(b.id)}
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
