import React, { useState, useEffect } from 'react';
import '../styles/AdminFlightsPage.css';
import { getFlights, createFlight } from '../api/flightService';

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
    try {
      const res = await getFlights();
      console.log('Flights:', res.data);
      setFlights(res.data);
    } catch (err) {
      console.error('Failed to load flights', err);
    }
  };

  const handleChange = (e) => {
    setNewFlight({ ...newFlight, [e.target.name]: e.target.value });
  };

  const handleAddFlight = async (e) => {
    e.preventDefault();
    try {
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
    } catch (err) {
      console.error('Failed to add flight', err);
    }
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

        {/* Form for adding flight */}
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
            <span className="vlera">Flight Number</span>
            <span className="vlera">Departure Airport</span>
            <span className="vlera">Arrival Airport</span>
            <span className="vlera">Departure Time</span>
            <span className="vlera">Arrival Time</span>
            <span className="vlera">Flight Date</span>
            <span className="vlera">Available Seat</span>
            <span className="vlera">Price</span>
            <span className="vlera">Airline</span>
          </div>

          {flights.length === 0 ? (
            <p style={{ marginTop: '20px' }}>No flights found.</p>
          ) : (
            flights.map((f) => (
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
              </div>
            ))
          )}
        </div>
      </main>
    </div>
  );
}
