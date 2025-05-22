import React, { useEffect, useState } from 'react';
import { Formik, Form, Field, ErrorMessage } from 'formik';
import * as Yup from 'yup';
import '../styles/AdminAirportPage.css';
import { getAirports, createAirport, deleteAirport } from '../api/airportService';

export default function AdminCity() {
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
          <h2>CITY AND COUNTRY</h2>
          <div className="airport-admin-title">ADMIN</div>
        </header>


        <Formik
          initialValues={{ countryname: '', code: ''}}
          validationSchema={Yup.object({
            countryname: Yup.string().required('Required'),
            code: Yup.string().required('Required'),
            
          })}
          onSubmit={handleAdd}
        >
          <Form className="admin-city-country-form">
            <div className="admin-city-country-row">
                {['country name', 'code'].map((field) => (
                <div className="admin-city-country-input-group" key={field}>
                    <Field
                    type="text"
                    id={field}
                    name={field}
                    placeholder={field.charAt(0).toUpperCase() + field.slice(1)}
                    className="admin-city-country-input"
                    />
                    <ErrorMessage
                    name={field}
                    component="div"
                    className="admin-city-country-error"
                    />
                </div>
    ))}
    <button type="submit" className="admin-city-country-add-btn">ADD</button>
  </div>
          </Form>

        </Formik>
        <Formik
          initialValues={{ cityname: '', cityid: ''}}
          validationSchema={Yup.object({
            cityname: Yup.string().required('Required'),
            cityid: Yup.string().required('Required'),
            
          })}
          onSubmit={handleAdd}
        >
          <Form className="admin-city-country-form">
            <div className="admin-city-country-row">
                {['city name', 'city id'].map((field) => (
                <div className="admin-city-country-input-group" key={field}>
                    <Field
                    type="text"
                    id={field}
                    name={field}
                    placeholder={field.charAt(0).toUpperCase() + field.slice(1)}
                    className="admin-city-country-input"
                    />
                    <ErrorMessage
                    name={field}
                    component="div"
                    className="admin-city-country-error"
                    />
                </div>
    ))}
    <button type="submit" className="admin-city-country-add-btn">ADD</button>
  </div>
          </Form>

        </Formik>

        <div className="airport-table" style={{marginBottom:20}}>
            <div
                className="airport-table-header"
                style={{ display: 'flex', width: '100%' }}
            >
                <span style={{ width: '33%' }}>Country Name</span>
                <span style={{ width: '33%' }}>Country Code</span>
                <span style={{ width: '33%' }}>Actions</span>
            </div>

            {airports.map((airport) => (
                <div
                className="airport-table-row"
                key={airport.id}
                style={{ display: 'flex', width: '100%' }}
                >
                <span style={{ width: '33%' }}>{airport.name}</span>
                <span style={{ width: '33%' }}>{airport.code}</span>
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
        <div className="airport-table">
            <div
                className="airport-table-header"
                style={{ display: 'flex', width: '100%' }}
            >
                <span style={{ width: '33%' }}>City Name</span>
                <span style={{ width: '33%' }}>City Name</span>
                <span style={{ width: '33%' }}>Actions</span>
            </div>

            {airports.map((airport) => (
                <div
                className="airport-table-row"
                key={airport.id}
                style={{ display: 'flex', width: '100%' }}
                >
                <span style={{ width: '33%' }}>{airport.name}</span>
                <span style={{ width: '33%' }}>{airport.code}</span>
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
