import React, { useEffect, useState } from 'react';
import { Formik, Form, Field, ErrorMessage } from 'formik';
import * as Yup from 'yup';
import '../styles/AdminAirportPage.css';
import {
  getAllCities,
  createCity,
  deleteCity
} from '../api/cityService';
import {
  getAllCountries,
  createCountry,
  deleteCountry
} from '../api/countryService';
import { getTenantIdFromSubdomain } from '../utils/getTenantId';

export default function AdminCity() {
  const [cities, setCities] = useState([]);
  const [countries, setCountries] = useState([]);

  // Replace these with real values from auth/context
  const token = localStorage.getItem("token");
  const tenantId = getTenantIdFromSubdomain();

  useEffect(() => {
    loadCities();
    loadCountries();
  }, []);

  const loadCities = async () => {
    try {
      const res = await getAllCities(tenantId, token);
      setCities(Array.isArray(res.data) ? res.data : []);
    } catch (err) {
      console.error('Error loading cities:', err);
    }
  };

const loadCountries = async () => {
  try {
    const res = await getAllCountries(tenantId, token);
    console.log('Countries response:', res);

    const data = Array.isArray(res?.data) ? res.data : [];
    setCountries(data);
  } catch (err) {
    console.error('Error loading countries:', err);
  }
};


  const handleAddCity = async (values, { resetForm }) => {
    try {
      await createCity(tenantId, values, token);
      resetForm();
      loadCities();
    } catch (err) {
      console.error('Error creating city:', err);
    }
  };

const handleDeleteCity = async (id) => {
  try {
    await deleteCity(tenantId, id, token);
    loadCities();
  } catch (err) {
    if (err.response?.status === 409) {
      alert('This city is connected to other records and cannot be deleted.');
    } else {
      console.error('Error deleting city:', err);
      alert('An error occurred while trying to delete the city.');
    }
  }
};

  const handleAddCountry = async (values, { resetForm }) => {
    try {
      await createCountry(tenantId, values, token);
      resetForm();
      loadCountries();
    } catch (err) {
      console.error('Error creating country:', err);
    }
  };

  const handleDeleteCountry = async (id) => {
    try {
      await deleteCountry(tenantId, id, token);
      loadCountries();
    } catch (err) {
      console.error('Error deleting country:', err);
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
            'announcements', 'city', 'languages', 'trending', 'policy', 'gate','terminal'
          ].map((item) => (
            <div className="airport-nav-row" key={item}>
              <a href={`/admin/${item}`}>{item.toUpperCase()}</a>
            </div>
          ))}
        </nav>
      </aside>

      <main className="airport-main-content" style={{ backgroundImage: "url('../../public/AdminCityImage.jpg')", 
            backgroundRepeat: 'no-repeat',
            backgroundSize: 'cover',
            backgroundPosition: 'center',
            width: '100%',
            height: 'auto' }}>
        <header className="airport-header">
          <h2>CITY & COUNTRY ADMIN</h2>
          <div className="airport-admin-title">ADMIN</div>
        </header>

        {/* City Form */}
        <Formik
          initialValues={{ name: '', countryId: '' }}
          validationSchema={Yup.object({
            name: Yup.string().required('Required'),
            countryId: Yup.string().required('Required'),
          })}
          onSubmit={handleAddCity}
        >
          <Form className="admin-city-country-form">
            <div className="admin-city-country-row">
              <div className="admin-city-country-input-group">
                <Field
                  type="text"
                  name="name"
                  placeholder="City Name"
                  className="admin-city-country-input"
                />
                <ErrorMessage name="name" component="div" className="admin-city-country-error" />
              </div>
              <div className="admin-city-country-input-group">
                <Field
                  as="select"
                  name="countryId"
                  className="admin-city-country-input"
                >
                  <option value="">Select Country</option>
                {countries.map((c) => (
                  <option key={c.id} value={c.id}>{c.name}</option>
                ))}
                </Field>
                <ErrorMessage name="countryId" component="div" className="admin-city-country-error" />
              </div>
              <button type="submit" className="admin-city-country-add-btn">ADD CITY</button>
            </div>
          </Form>
        </Formik>

        {/* City Table */}
        <div className="airport-table" style={{ marginTop: 30 }}>
          <div className="airport-table-header" style={{ display: 'flex', width: '100%' }}>
            <span style={{ width: '40%' }}>City Name</span>
            <span style={{ width: '30%' }}>Country ID</span>
            <span style={{ width: '30%' }}>Actions</span>
          </div>

          {cities.map((city) => (
            <div className="airport-table-row" key={city.id} style={{ display: 'flex', width: '100%' }}>
              <span style={{ width: '40%' }}>{city.name}</span>
              <span style={{ width: '30%' }}>{city.countryId}</span>
              <span style={{ width: '30%' }}>
                <button className="airport-delete-btn" onClick={() => handleDeleteCity(city.id)}>🗑</button>
              </span>
            </div>
          ))}
        </div>

        {/* Country Form */}
        <Formik
          initialValues={{ name: '', code: '' }}
          validationSchema={Yup.object({
            name: Yup.string().required('Required'),
            code: Yup.string().required('Required'),
          })}
          onSubmit={handleAddCountry}
        >
          <Form className="admin-city-country-form" style={{ marginTop: 50 }}>
            <div className="admin-city-country-row">
              <div className="admin-city-country-input-group">
                <Field
                  type="text"
                  name="name"
                  placeholder="Country Name"
                  className="admin-city-country-input"
                />
                <ErrorMessage name="name" component="div" className="admin-city-country-error" />
              </div>
              <div className="admin-city-country-input-group">
                <Field
                  type="text"
                  name="code"
                  placeholder="Country Code"
                  className="admin-city-country-input"
                />
                <ErrorMessage name="code" component="div" className="admin-city-country-error" />
              </div>
              <button type="submit" className="admin-city-country-add-btn">ADD COUNTRY</button>
            </div>
          </Form>
        </Formik>

        {/* Country Table */}
        <div className="airport-table" style={{ marginTop: 30 }}>
          <div className="airport-table-header" style={{ display: 'flex', width: '100%' }}>
            <span style={{ width: '40%' }}>Country Name</span>
            <span style={{ width: '30%' }}>Code</span>
            <span style={{ width: '30%' }}>Actions</span>
          </div>

          {countries.map((country) => (
            <div className="airport-table-row" key={country.id} style={{ display: 'flex', width: '100%' }}>
              <span style={{ width: '40%' }}>{country.name}</span>
              <span style={{ width: '30%' }}>{country.code}</span>
              <span style={{ width: '30%' }}>
                <button className="airport-delete-btn" onClick={() => handleDeleteCountry(country.id)}>🗑</button>
              </span>
            </div>
          ))}
        </div>
      </main>
    </div>
  );
}
