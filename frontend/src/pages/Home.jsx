import React, { useEffect, useState } from 'react';
import { Formik, Form, Field, ErrorMessage } from 'formik';
import * as Yup from 'yup';
import '../styles/Home.css';
import { getAirports } from '../api/airportService';
import axios from '../utils/axiosInstance';
import ImageSlider from './ImageSlider';

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
  const [language, setLanguage] = useState(localStorage.getItem('language') || 'en');
  const [price, setPrice] = useState(null);
  const [weather, setWeather] = useState(null);

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

  useEffect(() => {
    const fetchWeather = async () => {
      try {
        const apiKey = "YOUR_API_KEY"; // Replace with your actual API key
        const response = await fetch(
          `https://api.weatherapi.com/v1/current.json?key=${apiKey}&q=Prishtina`
        );
        const data = await response.json();
        if (data.error) {
          console.error('Weather API Error:', data.error.message);
          setWeather(null);
        } else {
          setWeather({
            temp: data.current.temp_c,
            description: data.current.condition.text,
            icon: data.current.condition.icon
          });
        }
      } catch (error) {
        console.error("Weather fetch failed", error);
        setWeather(null);
      }
    };

    fetchWeather();
  }, []);

  const t = (en, sq) => (language === 'sq' ? sq : en);

  const handlePriceCheck = async (values) => {
    try {
      const response = await axios.post("/api/parking/calculate", {
        ...values,
        tenantId: localStorage.getItem("tenantId") || "1"
      });
      setPrice(response.data.price);
    } catch (err) {
      console.error("Error calculating price", err);
      setPrice(null);
    }
  };

  return (
    <div className="home-page">
      <header className="navbar-section"></header>

      <section className="section welcome-section">
        <div className="overlay-text">
          <h1 className="home-title">{t('Welcome to MBI RE', 'Mirë se vini në MBI RE')}</h1>
          <p className="home-text">{t('Explore the skies with premium experiences.', 'Eksploro qiellin me eksperienca premium.')}</p>
        </div>
      </section>

      <section className="section search-section">
        <div className="image-group">
          <img src="/public/edyta.jpg" alt="Plane 1" />
          <img src="/public/etreta.jpg" alt="Plane 2" />
        </div>

        <Formik
          initialValues={{ from: '', to: '', startDate: '', endDate: '', passengers: 1 }}
          validationSchema={SearchSchema}
          onSubmit={(values) => {
            const queryParams = new URLSearchParams(values).toString();
            window.location.href = `/filteredflights?${queryParams}`;
          }}
        >
          {() => (
            <Form className="search-form">
              <Field as="select" name="from" className='search-form-field'>
                <option value="">{t('Select Departure Airport', 'Zgjedh Aeroportin e Nisjes')}</option>
                {airports.map((airport) => (
                  <option key={airport.id} value={airport.city}>{airport.city}</option>
                ))}
              </Field>
              <ErrorMessage name="from" component="div" className="error" />
              <Field as="select" name="to" className='search-form-field'>
                <option value="">{t('Select Arrival Airport', 'Zgjedh Aeroportin e Mberritjes')}</option>
                {airports.map((airport) => (
                  <option key={airport.id} value={airport.city}>{airport.city}</option>
                ))}
              </Field>
              <ErrorMessage name="to" component="div" className="error" />
              <Field type="date" name="startDate" />
              <ErrorMessage name="startDate" component="div" className="error" />
              <Field type="date" name="endDate" />
              <ErrorMessage name="endDate" component="div" className="error" />
              <Field type="number" name="passengers" min="1" placeholder={t('Passengers', 'Pasagjerë')} />
              <ErrorMessage name="passengers" component="div" className="error" />
              <button type="submit">{t('Search', 'Kërko')}</button>
            </Form>
          )}
        </Formik>
      </section>

      <section className="section flights-section">
        <div className="flights-table-right">
          <table className="flight-table">
            <thead>
              <tr>
                <th>{t('FLIGHT NO', 'NR. I FLUTURIMIT')}</th>
                <th>{t('ORIGIN', 'NISJA')}</th>
                <th>{t('DESTINATION', 'DESTINACIONI')}</th>
                <th>{t('DEPARTURE TIME', 'KOHA E NISJES')}</th>
                <th>{t('ARRIVAL TIME', 'KOHA E MBERITJES')}</th>
                <th>{t('ACTIVE', 'AKTIV')}</th>
              </tr>
            </thead>
            <tbody>{/* Flights will be dynamically inserted here */}</tbody>
          </table>
          <button className="view-flights-btn" onClick={() => window.location.href = "/flights"}>
            {t('View All Flights', 'Shiko të gjitha fluturimet')}
          </button>
        </div>
        
        <div className="plane-image-left">
          <img src="/public/ekaterta.jpg" alt="Jet" />
        </div>
      </section>

      <ImageSlider />

      <section className="section parking-price-section">
        <div className="search-form">
          <h2 style={{ color: 'white' }}>{t('Calculate Parking Price', 'Llogarit Çmimin e Parkingut')}</h2>
          <Formik
            initialValues={{ entryHour: '', entryMinute: '', exitHour: '', exitMinute: '' }}
            onSubmit={handlePriceCheck}
          >
            {() => (
              <Form className='search-form'>
                <Field type="number" name="entryHour" placeholder={t('Entry Hour (0-23)', 'Ora e Hyrjes (0-23)')} />
                <Field type="number" name="entryMinute" placeholder={t('Entry Minute', 'Minuta e Hyrjes')} />
                <Field type="number" name="exitHour" placeholder={t('Exit Hour (0-23)', 'Ora e Daljes (0-23)')} />
                <Field type="number" name="exitMinute" placeholder={t('Exit Minute', 'Minuta e Daljes')} />
                <button type="submit">{t('Calculate', 'Llogarit')}</button>
              </Form>
            )}
          </Formik>
          {price !== null && (
            <div style={{ color: 'white', marginTop: '1rem' }}>
              {t('Total price:', 'Çmimi total:')} <strong>{price}€</strong>
            </div>
          )}
        </div>
        <div className="parking-price-right-section"></div>
      </section>

      {/* 🌤️ Weather Section */}
      <section className="section weather-section">
        <h2 className="weather-title">Current Weather in Prishtina</h2>
        {weather ? (
          <>
            <p className="weather-info">{weather.description}</p>
            <p className="weather-temp">{weather.temp}°C</p>
            <img src={weather.icon} alt="Weather Icon" />
          </>
        ) : (
          <p className="weather-info">Failed To Load Weather Data.</p>
        )}
      </section>
    </div>
  );
};

export default HomePage;
