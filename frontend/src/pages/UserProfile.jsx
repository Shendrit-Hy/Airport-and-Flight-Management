import React, { useEffect, useState } from 'react';
import '../styles/userProfile.css';

export default function UserProfile() {
  const [user, setUser] = useState({
    username: 'john_doe',
    fullName: 'John Doe',
    email: 'john@example.com',
    role: 'User'
  });

  const [flights, setFlights] = useState([
    {
      id: 1,
      flightNumber: 'MB101',
      departureAirport: 'Prishtina',
      arrivalAirport: 'Zurich',
      departureTime: '07:30',
      arrivalTime: '09:15',
      price: 150,
      flightStatus: 'SCHEDULED',
      checked: 'No'
    },
    {
      id: 2,
      flightNumber: 'MB202',
      departureAirport: 'Tirana',
      arrivalAirport: 'Berlin',
      departureTime: '10:00',
      arrivalTime: '12:45',
      price: 200,
      flightStatus: 'CANCELLED',
      checked: 'No'
    }
  ]);

  const handleCancel = (flightId) => {
    setFlights(prev =>
      prev.map(f =>
        f.id === flightId ? { ...f, flightStatus: 'CANCELLED' } : f
      )
    );
  };

  const handleCheckIn = (flightId) => {
    setFlights(prev =>
      prev.map(f =>
        f.id === flightId ? { ...f, checked: 'Yes' } : f
      )
    );
  };

  return (
    <div className="userprofile-page">
      <div className="userprofile-main">
        <header className="userprofile-topbar">
          <span>{user.role}</span>
        </header>

        <div className="userprofile-container">
          <div className="userprofile-left">
            <button className="userprofile-btn">Username: {user.username}</button>
            <button className="userprofile-btn userprofile-update">Update Your Username</button>
            <button className="userprofile-btn">Full Name: {user.fullName}</button>
            <button className="userprofile-btn">Email: {user.email}</button>
          </div>

          <div className="userprofile-right">
            <div className="userprofile-flight-box">
              <p>NUMRI I<br />FLUTURIMEVE</p>
              <h3>{flights.length}</h3>
            </div>
          </div>
        </div>

        <div className="userprofile-table">
          <table>
            <thead>
              <tr>
                <th>Flight No</th>
                <th>Origin</th>
                <th>Destination</th>
                <th>Departure</th>
                <th>Arrival</th>
                <th>Price (€)</th>
                <th>Status</th>
                <th>Checked</th>
                <th>Actions</th>
              </tr>
            </thead>
            <tbody>
              {flights.map((flight) => (
                <tr key={flight.id}>
                  <td>{flight.flightNumber}</td>
                  <td>{flight.departureAirport}</td>
                  <td>{flight.arrivalAirport}</td>
                  <td>{flight.departureTime}</td>
                  <td>{flight.arrivalTime}</td>
                  <td>{flight.price}</td>
                  <td>{flight.flightStatus}</td>
                  <td>{flight.checked}</td>
                  <td>
                    {flight.flightStatus === 'SCHEDULED' && (
                      <>
                        <button onClick={() => handleCancel(flight.id)}>Cancel</button>
                        <button onClick={() => handleCheckIn(flight.id)}>Check In</button>
                      </>
                    )}
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
