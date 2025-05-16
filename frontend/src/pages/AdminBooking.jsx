import React, { useEffect, useState } from 'react';
import "../styles/AdminBooking.css";
import axios from 'axios';

function AdminBooking() {
  const [bookings, setBookings] = useState([
    {
      id: 1,
      passengerName: 'John Doe',
      flightNumber: 'AB1234',
      seatNumber: '12A',
      bookingTime: '2025-05-16 10:00',
      status: 'Confirmed',
      price: '$199.99'
    }
  ]);

  useEffect(() => {
    axios.get('/api/bookings') // Replace with your real API
      .then(response => {
        const data = Array.isArray(response.data)
          ? response.data
          : response.data.bookings;

        if (Array.isArray(data) && data.length > 0) {
          setBookings(data);
        }
      })
      .catch(error => {
        console.error('Error fetching bookings:', error);
        // Keeps sample row on error
      });
  }, []);

  const handleDelete = (id) => {
    const updatedBookings = bookings.filter((booking, index) => {
      return booking.id ? booking.id !== id : index !== id;
    });
    setBookings(updatedBookings);

    // Optionally: send DELETE request
    // axios.delete(`/api/bookings/${id}`)
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

      <div className="content">
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
                  <td>{booking.bookingTime}</td>
                  <td>{booking.status}</td>
                  <td>{booking.price}</td>
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
            </tbody>
          </table>
        </div>
      </div>
    </div>
  );
}

export default AdminBooking;