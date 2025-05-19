import React, { useEffect, useState } from 'react';
import '../styles/AdminAirportPage.css';
import { getAirports, createAirport, deleteAirport } from '../api/airportService';

export default function AdminAirportPage() {
  const [airports, setAirports] = useState([]);
  const [newAirport, setNewAirport] = useState({
    name: '',
    location: '',
    city: '',
    country: ''
  });

  useEffect(() => {
    loadAirports();
  }, []);

  const loadAirports = async () => {
    try {
      const res = await getAirports();
      setAirports(Array.isArray(res.data) ? res.data : []);
    } catch (err) {
      console.error('Error loading airports:', err);
    }
  };

  const handleChange = (e) => {
    setNewAirport({ ...newAirport, [e.target.name]: e.target.value });
  };

  const handleAdd = async (e) => {
    e.preventDefault();
    try {
      await createAirport(newAirport);
      setNewAirport({
        name: '',
        location: '',
        city: '',
        country: ''
      });
      loadAirports();
    } catch (err) {
      console.error('Error creating airport:', err);
    }
  };

  const handleDelete = async (id) => {
    try {
      await deleteAirport(id);
      loadAirports();
    } catch (err) {
      console.error('Error deleting airport:', err);
    }
  };

  return (
    <div className="airport-layout">
      <aside className="airport-sidebar">
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

      <main className="airport-main-content">
        <header className="airport-header">
          <h2>AIRPORT</h2>
          <div className="airport-admin-title">ADMIN</div>
        </header>

        <form className="airport-add-form" onSubmit={handleAdd}>
          <div className="airport-form-grid">
            <div className="airport-input-group">
              <label htmlFor="airportId">Airport ID</label>
              <input
                type="text"
                id="name"
                name="name"
                value={newAirport.name}
                onChange={handleChange}
                required
              />
            </div>
            <div className="airport-input-group">
              <label htmlFor="location">Location</label>
              <input
                type="text"
                id="location"
                name="location"
                value={newAirport.location}
                onChange={handleChange}
                required
              />
            </div>
            <div className="input-group">
              <label htmlFor="city">City</label>
              <input
                type="text"
                id="city"
                name="city"
                value={newAirport.city}
                onChange={handleChange}
                required
              />
            </div>
            <div className="input-group">
              <label htmlFor="country">Country</label>
              <input
                type="text"
                id="country"
                name="country"
                value={newAirport.country}
                onChange={handleChange}
                required
              />
            </div>
          </div>
          <button type="submit" className="airport-add-btn">ADD</button>
        </form>

        <div className="airport-table">
          <div className="airport-table-header">
            <span>Airport Name</span>
            <span>Airport Location</span>
            <span>Actions</span>
          </div>

          {airports.map((airport) => (
            <div className="airport-table-row" key={airport.id}>
              <span>{airport.airportId}</span>
              <span>{airport.location}</span>
              <span>{airport.city}</span>
              <span>{airport.country}</span>
              <span>
                <button
                  className="airport-delete-btn"
                  onClick={() => handleDelete(airport.id)}
                >
                  🗑
                </button>
              </span>
            </div>
          ))}
        </div>
      </main>
    </div>
  );
}
