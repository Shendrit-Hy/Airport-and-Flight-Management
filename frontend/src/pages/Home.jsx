import React, { useEffect, useState } from 'react';
import { Formik, Form, Field, ErrorMessage } from 'formik';
import * as Yup from 'yup';
import '../styles/Home.css';
import { getAllAirports } from '../api/airportService';
import axios from '../utils/axiosInstance';
import ImageSlider from './ImageSlider';
import "slick-carousel/slick/slick.css";
import "slick-carousel/slick/slick-theme.css";
import Slider from "react-slick";
import { getTenantIdFromSubdomain } from '../utils/getTenantId';


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
  const [forecast, setForecast] = useState([]);
  const [currentWeather, setCurrentWeather] = useState(null);
  const [hourlyForecast, setHourlyForecast] = useState([]);

  const tenantId = getTenantIdFromSubdomain();
  const token = localStorage.getItem("token");

useEffect(() => {
  const fetchAirports = async () => {
    try {
      const tenantId = localStorage.getItem('tenantId'); // or however you store it
      const token = localStorage.getItem('token'); // or use context/auth service

      if (!tenantId || !token) {
        console.error('Missing tenant ID or token');
        return;
      }

      const response = await getAllAirports(tenantId, token);
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
        const response = await axios.get("http://localhost:8080/api/weather/current");
        const data = response.data;
        if (!data.error) {
          setCurrentWeather(data);
        }
      } catch (error) {
        console.error("Weather fetch failed", error);
      }
    };
    fetchWeather();
  }, []);

  useEffect(() => {
    const fetchForecast = async () => {
      try {
        const apiKey = 'c8e04e837b134803a7c132638252305';
        const lat = '42.665440';
        const lon = '21.165319';
        const response = await fetch(`https://api.openweathermap.org/data/2.5/forecast?lat=${lat}&lon=${lon}&appid=${apiKey}&units=metric`);
        const data = await response.json();
        if (data.list) {
          const dailyData = data.list.filter(item => item.dt_txt.includes('12:00:00')).slice(0, 5);
            console.log(" Weekly Forecast Data:", dailyData);
          setForecast(dailyData);

          const next24Hours = data.list.slice(0, 9); // 3-hour intervals = 24 hrs
          setHourlyForecast(next24Hours);
        }
      } catch (err) {
        console.error('Error fetching forecast:', err);
      }
    };
    fetchForecast();
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
<Field as="select" name="from" className="search-form-field">
  <option value="">{t('Select Departure Airport', 'Zgjedh Aeroportin e Nisjes')}</option>
  {airports.map((airport) => (
    <option key={airport.id} value={airport.code}>
      {airport.name} ({airport.code})
    </option>
  ))}
</Field>

              <ErrorMessage name="from" component="div" className="error" />
<Field as="select" name="to" className="search-form-field">
  <option value="">{t('Select Arrival Airport', 'Zgjedh Aeroportin e Mberritjes')}</option>
  {airports.map((airport) => (
    <option key={airport.id} value={airport.code}>
      {airport.name} ({airport.code})
    </option>
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

      {currentWeather && (
        <section className="section weather-section">
          <h2 className="weather-title">{t('Current Weather', 'Moti Aktual')}</h2>
          <div className="weather-container-dark">
            <div className="weather-card current-weather-card-dark">
              <p className="weather-location"><strong>{currentWeather.location.name.toUpperCase()}</strong></p>
              <img className="weather-icon-large" src={currentWeather.current.condition.icon} alt="weather icon" />
              <p className="weather-temp-large">{currentWeather.current.temp_c}°C</p>
              <p className="weather-text-light">{currentWeather.current.condition.text}</p>
            </div>
          </div>
        </section>
      )}

      {hourlyForecast.length > 0 && (
        <section className="section weather-section">
          <h2 className="weather-title">{t('Next 24 Hours', '24 Orët e Ardhshme')}</h2>
          <div className="hourly-forecast-container">
            {hourlyForecast.map((hour, index) => (
              <div className="hourly-forecast-card" key={index}>
                <p className="hour">
                  {new Date(hour.dt_txt).toLocaleTimeString([], {
                    hour: '2-digit',
                    minute: '2-digit',
                  })}
                </p>
                <img
                  src={`https://openweathermap.org/img/wn/${hour.weather[0].icon}@2x.png`}
                  alt="weather icon"
                />
                <p className="temp">{Math.round(hour.main.temp)}°C</p>
              </div>
            ))}
          </div>
        </section>
      )}

      {forecast.length > 0 && (
  <section className="section weather-section">
    <h2 className="weather-title">{t('Weekly Forecast', 'Parashikimi Javor')}</h2>
    <Slider
      dots={true}
      infinite={true}
      speed={500}
      slidesToShow={3}
      slidesToScroll={1}
      responsive={[
        {
          breakpoint: 768,
          settings: {
            slidesToShow: 2,
          },
        },
        {
          breakpoint: 480,
          settings: {
            slidesToShow: 1,
          },
        },
      ]}
    >
      {forecast.map((day, index) => (
        <div className="weekly-slide" key={index}>
          <p><strong>{new Date(day.dt_txt).toLocaleDateString('en-US', { weekday: 'long' })}</strong></p>
          <img
            src={`https://openweathermap.org/img/wn/${day.weather[0].icon}@2x.png`}
            alt="weather icon"
            style={{ width: '60px', height: '60px' }}
          />
          <p style={{ fontSize: '1.2rem' }}>{Math.round(day.main.temp)}°C</p>
          <p>{day.weather[0].description}</p>
        </div>
      ))}
    </Slider>
  </section>
)}

    </div>
  );
};

export default HomePage;
