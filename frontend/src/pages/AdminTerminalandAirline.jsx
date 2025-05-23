import React, { useEffect, useState } from 'react';
import { Formik, Form, Field, ErrorMessage } from 'formik';
import * as Yup from 'yup';
import '../styles/AdminAirportPage.css';
import { getAllAirports } from '../api/airportService';
import { getAllTerminals, createTerminal } from '../api/terminalService';
import { getAllAirlines, createAirline, deleteAirline } from '../api/airlineService';
import { getTenantIdFromSubdomain } from '../utils/getTenantId';

export default function AdminTerminalandAirline() {
  const [airports, setAirports] = useState([]);
  const [terminals, setTerminals] = useState([]);
  const [airlines, setAirlines] = useState([]);
  const tenantId = getTenantIdFromSubdomain();
  const token = localStorage.getItem("token");

  useEffect(() => {
    loadData();
  }, []);

const loadData = async () => {
  try {
    const [airportRes, terminalRes, airlineRes] = await Promise.all([
      getAllAirports(tenantId, token),
      getAllTerminals(tenantId),
      getAllAirlines(tenantId)
    ]);

    console.log('Airport Response:', airportRes);
    console.log('Terminal Response:', terminalRes);
    console.log('Airline Response:', airlineRes);

    setAirports(Array.isArray(airportRes.data) ? airportRes.data : []);
    setTerminals(Array.isArray(terminalRes.data) ? terminalRes.data : []);
    setAirlines(Array.isArray(airlineRes.data) ? airlineRes.data : []);
  } catch (err) {
    console.error('Error loading data:', err);
    setAirports([]);
    setTerminals([]);
    setAirlines([]);
  }
};


  const handleAddTerminal = async (values, { resetForm }) => {
    try {
      await createTerminal(values, tenantId, token);
      resetForm();
      loadData();
    } catch (err) {
      console.error('Error creating terminal:', err);
    }
  };

  const handleAddAirline = async (values, { resetForm }) => {
    try {
      await createAirline({ name: values.airlinename }, tenantId, token);
      resetForm();
      loadData();
    } catch (err) {
      console.error('Error creating airline:', err);
    }
  };

  const handleDeleteAirline = async (id) => {
    try {
      await deleteAirline(id, tenantId, token);
      loadData();
    } catch (err) {
      console.error('Error deleting airline:', err);
    }
  };

  return (
    <div className="airport-layout">
      <aside className="airport-sidebar">
        <div className="airport-logo">MBI RE</div>
        <nav className="airport-nav-group">
          {[
            'dashboard', 'airport', 'booking', 'faqs', 'flightspage',
            'maintenance', 'passangers', 'payments', 'staff', 'support',
            'announcements', 'city', 'languages', 'trending', 'policy', 'gate', 'terminal'
          ].map((item) => (
            <div className="airport-nav-row" key={item}>
              <a href={`/admin/${item}`}>{item.toUpperCase()}</a>
            </div>
          ))}
        </nav>
      </aside>

      <main className="airport-main-content" style={{
        backgroundImage: "url('../../public/AdminTerminalImage.png')",
        backgroundRepeat: 'no-repeat',
        backgroundSize: 'cover',
        backgroundPosition: 'center',
        width: '100%',
        height: 'auto'
      }}>
        <header className="airport-header">
          <h2 style={{ color: "black" }}>TERMINAL AND AIRLINE</h2>
          <div className="airport-admin-title" style={{ color: "black" }}>ADMIN</div>
        </header>

        {/* Terminal Form */}
        <Formik
          initialValues={{ terminalname: '', airportID: '' }}
          validationSchema={Yup.object({
            terminalname: Yup.string().required('Required'),
            airportID: Yup.string().required('Required'),
          })}
          onSubmit={handleAddTerminal}
        >
          <Form className="admin-city-country-form">
            <div className="admin-city-country-row">
              <div className="admin-city-country-input-group">
                <Field
                  type="text"
                  name="terminalname"
                  placeholder="Terminal Name"
                  className="admin-city-country-input"
                />
                <ErrorMessage name="terminalname" component="div" className="admin-city-country-error" />
              </div>

              <div className="admin-city-country-input-group">
                <Field as="select" name="airportID" className="admin-city-country-input">
                  <option value="">Select Airport</option>
                  {airports.map((airport) => (
                    <option key={airport.id} value={airport.id}>
                      {airport.name}
                    </option>
                  ))}
                </Field>
                <ErrorMessage name="airportID" component="div" className="admin-city-country-error" />
              </div>

              <button type="submit" className="admin-city-country-add-btn">ADD TERMINAL</button>
            </div>
          </Form>
        </Formik>

        {/* Airline Form */}
        <Formik
          initialValues={{ airlinename: '' }}
          validationSchema={Yup.object({
            airlinename: Yup.string().required('Required'),
          })}
          onSubmit={handleAddAirline}
        >
          <Form className="admin-city-country-form">
            <div className="admin-city-country-row">
              <div className="admin-city-country-input-group">
                <Field
                  type="text"
                  name="airlinename"
                  placeholder="Airline Name"
                  className="admin-city-country-input"
                />
                <ErrorMessage name="airlinename" component="div" className="admin-city-country-error" />
              </div>
              <button type="submit" className="admin-city-country-add-btn">ADD AIRLINE</button>
            </div>
          </Form>
        </Formik>

        {/* Terminals Table */}
        <div className="airport-table" style={{ marginBottom: 20 }}>
          <div className="airport-table-header" style={{ display: 'flex', width: '100%' }}>
            <span style={{ width: '50%' }}>Terminal Name</span>
            <span style={{ width: '50%' }}>Airport</span>
          </div>
          {terminals.map((terminal) => (
            <div key={terminal.id} className="airport-table-row" style={{ display: 'flex', width: '100%' }}>
              <span style={{ width: '50%' }}>{terminal.terminalname}</span>
              <span style={{ width: '50%' }}>
                {airports.find(a => a.id === terminal.airportID)?.name || terminal.airportID}
              </span>
            </div>
          ))}
        </div>

        {/* Airlines Table */}
        <div className="airport-table">
          <div className="airport-table-header" style={{ display: 'flex', width: '100%' }}>
            <span style={{ width: '50%' }}>Airline Name</span>
            <span style={{ width: '50%' }}>Actions</span>
          </div>
          {airlines.map((airline) => (
            <div key={airline.id} className="airport-table-row" style={{ display: 'flex', width: '100%' }}>
              <span style={{ width: '50%' }}>{airline.name}</span>
              <span style={{ width: '50%' }}>
                <button className="airport-delete-btn" onClick={() => handleDeleteAirline(airline.id)}>🗑</button>
              </span>
            </div>
          ))}
        </div>
      </main>
    </div>
  );
}
