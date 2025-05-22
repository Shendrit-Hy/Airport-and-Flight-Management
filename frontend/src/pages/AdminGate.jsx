import React, { useEffect, useState } from 'react';
import { Formik, Form, Field, ErrorMessage } from 'formik';
import * as Yup from 'yup';
import '../styles/AdminAirportPage.css';
import { getAllAirports, createAirport, deleteAirport } from '../api/airportService';
import { getTenantIdFromSubdomain } from '../utils/getTenantId';

export default function AdminGate() {
  const [airports, setAirports] = useState([]);
  const tenantId = getTenantIdFromSubdomain();
  const token = localStorage.getItem("token");

  useEffect(() => {
    loadAirports();
  }, []);

  const loadAirports = async () => {
    try {
      const res = await getAllAirports(tenantId, token);
      setAirports(Array.isArray(res.data) ? res.data : []);
    } catch (err) {
      console.error('Error loading airports:', err);
    }
  };

  const handleAdd = async (values, { resetForm }) => {
    const airportData = {
      name: values.name,
      code: values.code,
      timezone: values.timezone,
      cityId: values.cityId,
      countryId: values.countryId,
    };

    try {
      await createAirport(airportData, tenantId, token);
      resetForm();
      loadAirports();
    } catch (err) {
      console.error('Error creating airport:', err);
    }
  };

  const handleDelete = async (id) => {
    try {
      await deleteAirport(id, tenantId, token);
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
          <h2>GATE</h2>
          <div className="airport-admin-title">ADMIN</div>
        </header>

        <Formik
          initialValues={{ gatenr: '', status: '', terminalid: '', terminalname: ''}}
          validationSchema={Yup.object({
            gatenr: Yup.string().required('Required'),
            status: Yup.string().required('Required'),
            terminalid: Yup.string().required('Required'),
            terminalname: Yup.string().required('Required'),
          })}
          onSubmit={handleAdd}
        >
          <Form className="airport-add-form">
            <div className="airport-form-grid">
              {['Gate Nr','status', 'terminal id', 'terminal name'].map((field) => (
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
          <div className="airport-table-header" style={{ width: '100%',display:'flex' }}>
            <span style={{ width: '20%' }}>Gate Number</span>
            <span style={{ width: '20%' }}>Status</span>
            <span style={{ width: '20%' }}>Terminal ID</span>
            <span style={{ width: '20%' }}>Terminal Name</span>
            <span style={{ width: '20%' }}>Actions</span>
          </div>

          {airports.map((airport) => (
            <div className="airport-table-row" key={airport.id}>
              <span style={{ width: '20%' }}>{airport.name}</span>
              <span style={{ width: '20%' }}>{airport.code}</span>
              <span style={{ width: '20%' }}>{airport.timezone}</span>
              <span style={{ width: '20%' }}>{airport.city.id}</span>
              <span>
                <button
                  style={{ width: '20%' }}
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
