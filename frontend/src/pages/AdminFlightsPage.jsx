import React, { useState, useEffect } from 'react';
import '../styles/AdminFlightsPage.css';
import { getFlights, createFlight, deleteFlight } from '../api/flightService';

export default function AdminFlightsPage() {
  const [flights, setFlights] = useState([]);
  const [newFlight, setNewFlight] = useState({
    flightNumber: '',
    departureAirport: '',
    arrivalAirport: '',
    departureTime: '',
    arrivalTime: '',
    flightDate: '',
    availableSeat: '',
    price: '',
    airline: ''
  });

  useEffect(() => {
    loadFlights();
  }, []);

  const loadFlights = async () => {
    const res = await getFlights();
    setFlights(res.data);
  };

  const handleChange = (e) => {
    setNewFlight({ ...newFlight, [e.target.name]: e.target.value });
  };

  const handleAddFlight = async (e) => {
    e.preventDefault();
    await createFlight(newFlight);
    setNewFlight({
      flightNumber: '',
      departureAirport: '',
      arrivalAirport: '',
      departureTime: '',
      arrivalTime: '',
      flightDate: '',
      availableSeat: '',
      price: '',
      airline: ''
    });
    loadFlights();
  };

  const handleDelete = async (id) => {
    await deleteFlight(id);
    loadFlights();
  };

  return (
    <div className="admin-layout">
      <aside className="sidebar">
        <div className="logo">MBI RE</div>
        <nav>
          <a href="/admin/dashboard">DASHBOARD</a>
          <a href="/admin/flights" className="active">FLIGHTS</a>
          <a href="/admin/search">SEARCH</a>
        </nav>
      </aside>

      <main className="main-content">
        <header className="admin-header">
          <h2>FLIGHTS</h2>
          <div className="admin-title">ADMIN</div>
        </header>

        <form className="flight-add-form" onSubmit={handleAddFlight}>
          <div className="form-grid">
            {[
              { name: 'flightNumber', label: 'Flight Number' },
              { name: 'departureAirport', label: 'Departure Airport' },
              { name: 'arrivalAirport', label: 'Arrival Airport' },
              { name: 'departureTime', label: 'Departure Time' },
              { name: 'arrivalTime', label: 'Arrival Time' },
              { name: 'flightDate', label: 'Flight Date' },
              { name: 'availableSeat', label: 'Available Seat' },
              { name: 'price', label: 'Price' },
              { name: 'airline', label: 'Airline' }
            ].map((field) => (
              <div className="input-group" key={field.name}>
                <label htmlFor={field.name}>{field.label}</label>
                <input
                  type="text"
                  id={field.name}
                  name={field.name}
                  value={newFlight[field.name]}
                  onChange={handleChange}
                  required
                />
              </div>
            ))}
          </div>
          <button type="submit" className="add-btn">ADD</button>
        </form>

        <div className="flights-table">
          <div className="table-header">
            <span>Flight Number</span>
            <span>Departure Airport</span>
            <span>Arrival Airport</span>
            <span>Departure Time</span>
            <span>Arrival Time</span>
            <span>Flight Date</span>
            <span>Available Seat</span>
            <span>Price</span>
            <span>Airline</span>
            <span>Actions</span>
          </div>

          {flights.map((f) => (
            <div className="table-row" key={f.id}>
              <span>{f.flightNumber}</span>
              <span>{f.departureAirport}</span>
              <span>{f.arrivalAirport}</span>
              <span>{f.departureTime}</span>
              <span>{f.arrivalTime}</span>
              <span>{f.flightDate}</span>
              <span>{f.availableSeat}</span>
              <span>{f.price}</span>
              <span>{f.airline}</span>
              <span>
                <button className="delete-btn" onClick={() => handleDelete(f.id)}>🗑</button>
              </span>
            </div>
          ))}
        </div>
      </main>
    </div>
  );
}
