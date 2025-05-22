import React, { useEffect, useState } from 'react';
import { Formik, Form, Field, ErrorMessage } from 'formik';
import * as Yup from 'yup';
import '../styles/AdminFlightsPage.css';
import { getAllFlights, createFlight, deleteFlight } from '../api/flightService';
import { getTenantIdFromSubdomain } from '../utils/getTenantId';
import "../styles/AdminAirportPage.css";

export default function AdminFlightsPage() {
  const [flights, setFlights] = useState([]);
  const [terminals, setTerminals] = useState([]);
  const [gates, setGates] = useState([]);

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

  const handleAddFlight = async (values, { resetForm }) => {
    try {
      await createFlight(values, tenantId, token);
      console.log("Flight created!");
      loadFlights();
      resetForm();
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
          {["dashboard", "airport", "booking", "faqs", "flightspage", "maintenance", "passangers", "payments", "staff", "support", "announcements"].map((item) => (
            <div className="airport-nav-row" key={item}>
              <a href={`/admin/${item}`}>{item.toUpperCase()}</a>
            </div>
          ))}
        </nav>
      </aside>

      <main className="adminflights-main-content">
        <header className="adminflights-header">
          <h2>FLIGHTS</h2>
          <div className="adminflights-title">ADMIN</div>
        </header>

        <Formik
          initialValues={{
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
          }}
          validationSchema={Yup.object({
            flightNumber: Yup.string().required('Required'),
            departureAirport: Yup.string().required('Required'),
            arrivalAirport: Yup.string().required('Required'),
            departureTime: Yup.string().required('Required'),
            arrivalTime: Yup.string().required('Required'),
            flightDate: Yup.string().required('Required'),
            availableSeat: Yup.number().required('Required'),
            price: Yup.number().required('Required'),
            airline: Yup.string().required('Required'),
            terminalId: Yup.string().required('Required'),
            gateId: Yup.string().required('Required')
          })}
          onSubmit={handleAddFlight}
        >
          <Form className="adminflights-add-form">
            <div className="adminflights-form-grid">
              {["flightNumber", "departureAirport", "arrivalAirport", "departureTime", "arrivalTime", "flightDate", "availableSeat", "price", "airline"].map((field) => (
                <div className="adminflights-input-group" key={field}>
                  <Field
                    type="text"
                    name={field}
                    id={field}
                    placeholder={field.replace(/([A-Z])/g, ' $1')}
                    className="adminflights-input"
                    style={{ backgroundColor: 'rgb(53,53,53)', color: 'white' }}
                  />
                  <ErrorMessage name={field} component="div" className="adminflights-error" />
                </div>
              ))}

              <div className="adminflights-input-group">
                <label htmlFor="terminalId">Terminal</label>
                <Field
                  as="select"
                  name="terminalId"
                  className="adminflights-input"
                  style={{ backgroundColor: 'rgb(53,53,53)', color: 'white' }}
                  required
                >
                  <option value="">Select Terminal</option>
                  {terminals.map((t) => (
                    <option key={t.id} value={t.id}>{t.name}</option>
                  ))}
                </Field>
                <ErrorMessage name="terminalId" component="div" className="adminflights-error" />
              </div>

              <div className="adminflights-input-group">
                <label htmlFor="gateId">Gate</label>
                <Field
                  as="select"
                  name="gateId"
                  className="adminflights-input"
                  style={{ backgroundColor: 'rgb(53,53,53)', color: 'white' }}
                  required
                >
                  <option value="">Select Gate</option>
                  {gates.map((g) => (
                    <option key={g.id} value={g.id}>{g.name}</option>
                  ))}
                </Field>
                <ErrorMessage name="gateId" component="div" className="adminflights-error" />
              </div>
            </div>

            <button type="submit" className="adminflights-add-btn">ADD</button>
          </Form>
        </Formik>

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
