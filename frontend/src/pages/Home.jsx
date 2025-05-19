import React, { useEffect, useState } from 'react';
import { Formik, Form, Field, ErrorMessage } from 'formik';
import * as Yup from 'yup';
import '../styles/Home.css';
import { getAirports } from '../api/airportService';

const SearchSchema = Yup.object().shape({
  from: Yup.string().required('Departure airport is required'),
  to: Yup.string().required('Arrival airport is required'),
  startDate: Yup.date().required('Start date is required'),
  endDate: Yup.date()
    .required('End date is required')
    .min(Yup.ref('startDate'), 'End date cannot be before start date'),
  passengers: Yup.number()
    .required('Passenger number is required')
    .min(1, 'At least one passenger is required'),
});

const HomePage = () => {
  const [airports, setAirports] = useState([]);

  useEffect(() => {
    const fetchAirports = async () => {
      try {
        const response = await getAirports();
        setAirports(Array.isArray(response.data) ? response.data : []);
      } catch (error) {
        console.error('Failed to load airports:', error);
      }
    };
    fetchAirports();
  }, []);

  return (
    <div className="home-page">
      <header className="navbar-section">
        {/* Add Navbar here if needed */}
      </header>

      {/* Section 1 */}
      <section className="section welcome-section">
        <div className="overlay-text">
          <h1>Welcome to MBI RE</h1>
          <p>Explore the skies with premium experiences.</p>
        </div>
      </section>

      {/* Section 2 */}
      <section className="section search-section">
        <div className="image-group">
          <img src="/public/edyta.jpg" alt="Plane 1" />
          <img src="/public/etreta.jpg" alt="Plane 2" />
        </div>

        <Formik
          initialValues={{
            from: '',
            to: '',
            startDate: '',
            endDate: '',
            passengers: 1,
          }}
          validationSchema={SearchSchema}
          onSubmit={(values) => {
            const queryParams = new URLSearchParams(values).toString();
            window.location.href = `/filteredflights?${queryParams}`;
          }}
        >
          {() => (
            <Form className="search-form">
              <Field as="select" name="from">
                <option value="">Select Departure Airport</option>
                {airports.map((airport) => (
                  <option key={airport.id} value={airport.city}>
                    {airport.city}
                  </option>
                ))}
              </Field>
              <ErrorMessage name="from" component="div" className="error" />

              <Field as="select" name="to">
                <option value="">Select Arrival Airport</option>
                {airports.map((airport) => (
                  <option key={airport.id} value={airport.city}>
                    {airport.city}
                  </option>
                ))}
              </Field>
              <ErrorMessage name="to" component="div" className="error" />

              <Field type="date" name="startDate" />
              <ErrorMessage name="startDate" component="div" className="error" />

              <Field type="date" name="endDate" />
              <ErrorMessage name="endDate" component="div" className="error" />

              <Field type="number" name="passengers" min="1" placeholder="Passengers" />
              <ErrorMessage name="passengers" component="div" className="error" />

              <button type="submit">Search</button>
            </Form>
          )}
        </Formik>
      </section>

      {/* Section 3 */}
      <section className="section flights-section">
        <div className="plane-image-left">
          <img src="/public/ekaterta.jpg" alt="Jet" />
        </div>

        <div className="flights-table-right">
          <table className="flight-table">
            <thead>
              <tr>
                <th>FLIGHT NO</th>
                <th>ORIGIN</th>
                <th>DESTINATION</th>
                <th>DEPARTURE TIME</th>
                <th>ARRIVAL TIME</th>
                <th>ACTIVE</th>
              </tr>
            </thead>
            <tbody>
              {/* Flights will be dynamically inserted here */}
            </tbody>
          </table>
          <button className="view-flights-btn" onClick={() => window.location.href = "/flights"}>
            View All Flights
          </button>
        </div>
      </section>

      {/* Section 4 */}
      <section className="section placeholder-section">
        <div className="placeholder-box">DIZAJN</div>
      </section>

      {/* Section 5 */}
      <section className="section fullscreen-image-section">
        <img src="/public/epesta.jpg" alt="Banner" className="full-img" />
      </section>
    </div>
  );
};

export default HomePage;
