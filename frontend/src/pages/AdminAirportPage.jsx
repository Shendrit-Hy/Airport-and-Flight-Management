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
    <div className="admin-layout">
      <aside className="sidebar">
        <div className="logo">MBI RE</div>
        <nav>
          <a href="/admin/dashboard">DASHBOARD</a>
          <a href="/admin/search">SEARCH</a>
          <a href="/admin/airport" className="active">AIRPORT</a>
        </nav>
      </aside>

      <main className="main-content">
        <header className="admin-header">
          <h2>AIRPORT</h2>
          <div className="admin-title">ADMIN</div>
        </header>

        <form className="flight-add-form" onSubmit={handleAdd}>
          <div className="form-grid">
            <div className="input-group">
              <label htmlFor="name">Name</label>
              <input
                type="text"
                id="name"
                name="name"
                value={newAirport.name}
                onChange={handleChange}
                required
              />
            </div>
            <div className="input-group">
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
          <button type="submit" className="add-btn">ADD</button>
        </form>

        <div className="flights-table">
          <div className="table-header">
            <span>ID</span>
            <span>Name</span>
            <span>Location</span>
            <span>City</span>
            <span>Country</span>
            <span>Actions</span>
          </div>

          {airports.map((airport) => (
            <div className="table-row" key={airport.id}>
              <span>{airport.id}</span>
              <span>{airport.name}</span>
              <span>{airport.location}</span>
              <span>{airport.city}</span>
              <span>{airport.country}</span>
              <span>
                <button className="delete-btn" onClick={() => handleDelete(airport.id)}>🗑</button>
              </span>
            </div>
          ))}
        </div>
      </main>
    </div>
  );
}
