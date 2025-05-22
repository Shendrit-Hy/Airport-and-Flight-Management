import React, { useEffect, useState } from 'react';
import { Formik, Form, Field, ErrorMessage } from 'formik';
import * as Yup from 'yup';
import '../styles/AdminAirportPage.css';
import { getAllAirports, createAirport, deleteAirport } from '../api/airportService';

export default function AdminTrendingPlace() {
  const [airports, setAirports] = useState([]);

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

  const handleAdd = async (values, { resetForm }) => {
    try {
      await createAirport(values);
      resetForm();
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
          {['dashboard', 'airport', 'booking', 'faqs', 'flightspage', 'maintenance', 'passangers', 'payments', 'staff', 'support', 'announcements'].map((item) => (
            <div className="airport-nav-row" key={item}>
              <a href={`/admin/${item}`}>{item.toUpperCase()}</a>
            </div>
          ))}
        </nav>
      </aside>

      <main className="airport-main-content">
        <header className="airport-header">
          <h2>TRENDING PLACE</h2>
          <div className="airport-admin-title">ADMIN</div>
        </header>


        <Formik
          initialValues={{ name: '', description: '', season: '', imageurl: ''}}
          validationSchema={Yup.object({
            name: Yup.string().required('Required'),
            description: Yup.string().required('Required'),
            season: Yup.string().required('Required'),
            imageurl: Yup.string().required('Required'),
          })}
          onSubmit={handleAdd}
        >
          <Form className="airport-add-form">
            <div className="airport-form-grid">
              {['name', 'description', 'season', 'image url'].map((field) => (
                <div className="airport-input-group" key={field}>
                  <Field
                    type="text"
                    id={field}
                    name={field}
                    placeholder={field.charAt(0).toUpperCase() + field.slice(1)}
                    className="airport-input"
                  />
                  <ErrorMessage name={field} component="div" className="airport-error" />
                </div>
              ))}
            </div>
            <button type="submit" className="airport-add-btn">ADD</button>
          </Form>
        </Formik>

        <div className="airport-table">
          <div className="airport-table-header" style={{ display: 'flex', width: '100%' }}>
            <span style={{ width: '20%' }}>Name</span>
            <span style={{ width: '20%' }}>Description</span>
            <span style={{ width: '20%' }}>Season</span>
            <span style={{ width: '20%' }}>Image Url</span>
            <span style={{ width: '20%' }}>Actions</span>
          </div>

          {airports.map((airport) => (
            <div className="airport-table-row" key={airport.id}>
              <span style={{ width: '20%' }}>{airport.name}</span>
              <span style={{ width: '20%' }}>{airport.code}</span>
              <span style={{ width: '20%' }}>{airport.name}</span>
              <span style={{ width: '20%' }}>{airport.code}</span>
              <span style={{ width: '20%' }}>
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
