import React, { useState, useEffect } from 'react';
import '../styles/AdminFlightsPage.css';
import { getAllFlights, createFlight, deleteFlight } from '../api/flightService';
import { getTenantIdFromSubdomain } from '../utils/getTenantId';
import "../styles/AdminAirportPage.css"


export default function AdminFlightsPage() {
  const [flights, setFlights] = useState([]);
  const [terminals, setTerminals] = useState([]);
  const [gates, setGates] = useState([]);

  const [newFlight, setNewFlight] = useState({
    flightNumber: '',
    departureAirport: '',
    arrivalAirport: '',
    departureTime: '',
    arrivalTime: '',
    flightDate: '',
    availableSeat: '',
    price: '',
    airline: '',
    terminalId: '',
    gateId: ''
  });

  const tenantId = getTenantIdFromSubdomain();
  const token = localStorage.getItem("token");

  useEffect(() => {
    loadFlights();
    loadTerminals();
    loadGates();
  }, []);

  const loadFlights = async () => {
    try {
      const res = await getAllFlights(tenantId, token);
      setFlights(res.data);
    } catch (err) {
      console.error("Failed to load flights", err);
    }
  };

  const loadTerminals = async () => {
    try {
      const res = await fetch(`/api/terminals`, {
        headers: {
          "X-Tenant-ID": tenantId,
          Authorization: `Bearer ${token}`
        }
      });
      const data = await res.json();
      setTerminals(data);
    } catch (err) {
      console.error("Failed to load terminals", err);
    }
  };

  const loadGates = async () => {
    try {
      const res = await fetch(`/api/gates`, {
        headers: {
          "X-Tenant-ID": tenantId,
          Authorization: `Bearer ${token}`
        }
      });
      const data = await res.json();
      setGates(data);
    } catch (err) {
      console.error("Failed to load gates", err);
    }
  };

  const handleChange = (e) => {
    setNewFlight({ ...newFlight, [e.target.name]: e.target.value });
  };

  const handleAddFlight = async (e) => {
    e.preventDefault();
    try {
      await createFlight(newFlight, tenantId, token);
      console.log("Flight created!");
      loadFlights();
      setNewFlight({
        flightNumber: '',
        departureAirport: '',
        arrivalAirport: '',
        departureTime: '',
        arrivalTime: '',
        flightDate: '',
        availableSeat: '',
        price: '',
        airline: '',
        terminalId: '',
        gateId: ''
      });
    } catch (error) {
      console.error("Failed to create flight", error);
      if (error.response?.status === 401) {
        alert("You must be logged in as admin to create a flight.");
      }
    }
  };

  const handleDelete = async (id) => {
    try {
      await deleteFlight(id, tenantId, token);
      loadFlights();
    } catch (err) {
      console.error("Failed to delete flight", err);
    }
  };

  return (
    <div className="adminflights-layout">
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

      <main className="adminflights-main-content">
        <header className="adminflights-header">
          <h2>FLIGHTS</h2>
          <div className="adminflights-title">ADMIN</div>
        </header>

        <form className="adminflights-add-form" onSubmit={handleAddFlight}>
          <div className="adminflights-form-grid">
            {[
              { name: 'flightNumber', label: 'Flight Number' },
              { name: 'departureAirport', label: 'Departure Airport' },
              { name: 'arrivalAirport', label: 'Arrival Airport' },
              { name: 'departureTime', label: 'Departure Time' },
              { name: 'arrivalTime', label: 'Arrival Time' },
              { name: 'flightDate', label: 'Flight Date' },
              { name: 'availableSeat', label: 'Available Seat' },
              { name: 'price', label: 'Price' },
              { name: 'airline', label: 'Airline' },
            ].map((field) => (
              <div className="adminflights-input-group" key={field.name}>
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

            {/* Terminal Selection */}
            <div className="adminflights-input-group">
              <label htmlFor="terminalId">Terminal</label>
              <select name="terminalId" value={newFlight.terminalId} onChange={handleChange} required>
                <option value="">Select Terminal</option>
                {terminals.map((t) => (
                  <option key={t.id} value={t.id}>{t.name}</option>
                ))}
              </select>
            </div>

            {/* Gate Selection */}
            <div className="adminflights-input-group">
              <label htmlFor="gateId">Gate</label>
              <select name="gateId" value={newFlight.gateId} onChange={handleChange} required>
                <option value="">Select Gate</option>
                {gates.map((g) => (
                  <option key={g.id} value={g.id}>{g.name}</option>
                ))}
              </select>
            </div>
          </div>

          <button type="submit" className="adminflights-add-btn">ADD</button>
        </form>

        <div className="adminflights-table">
          <div className="adminflights-table-header">
            <span>Flight Number</span>
            <span>Departure Airport</span>
            <span>Arrival Airport</span>
            <span>Departure Time</span>
            <span>Arrival Time</span>
            <span>Flight Date</span>
            <span>Available Seat</span>
            <span>Price</span>
            <span>Airline</span>
            <span>Status</span>
            <span>Actions</span>
          </div>

          {flights.map((f) => (
            <div className="adminflights-table-row" key={f.id}>
              <span>{f.flightNumber}</span>
              <span>{f.departureAirport}</span>
              <span>{f.arrivalAirport}</span>
              <span>{f.departureTime}</span>
              <span>{f.arrivalTime}</span>
              <span>{f.flightDate}</span>
              <span>{f.availableSeat}</span>
              <span>{f.price}</span>
              <span>{f.airline}</span>
              <span>{f.flightStatus}</span>
              <span>
                <button className="adminflights-delete-btn" onClick={() => handleDelete(f.id)}>🗑</button>
              </span>
            </div>
          ))}
        </div>
      </main>
    </div>
  );
}
