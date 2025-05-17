import React, { useEffect, useState } from 'react';
import '../styles/AdminAirportPage.css';
import { getAirports, createAirport, deleteAirport } from '../api/airportService';

export default function AdminAirportPage() {
  const [airports, setAirports] = useState([]);
  const [newAirport, setNewAirport] = useState({
    airportId: '',
    location: ''
  });

  useEffect(() => {
    loadAirports();
  }, []);

  const loadAirports = async () => {
    const res = await getAirports();
    setAirports(res.data);
  };

  const handleChange = (e) => {
    setNewAirport({ ...newAirport, [e.target.name]: e.target.value });
  };

  const handleAdd = async (e) => {
    e.preventDefault();
    await createAirport(newAirport);
    setNewAirport({ airportId: '', location: '' });
    loadAirports();
  };

  const handleDelete = async (id) => {
    await deleteAirport(id);
    loadAirports();
  };

  return (
    <div className="airport-layout">
      <aside className="airport-sidebar">
        <div className="airport-logo">MBI RE</div>
        <nav className="airport-nav-group">
          <div className="airport-nav-row">
            <a href="/admin/dashboard">DASHBOARD</a>
            <a href="/admin/search">SEARCH</a>
          </div>
          <div className="airport-nav-row">
            <a href="/admin/airport" className="active">AIRPORT</a>
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
                id="airportId"
                name="airportId"
                value={newAirport.airportId}
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
