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
  const [language, setLanguage] = useState(localStorage.getItem('language') || 'en');
<<<<<<< Updated upstream
=======
  const [price, setPrice] = useState(null);
  const [forecast, setForecast] = useState([]);
>>>>>>> Stashed changes

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

<<<<<<< Updated upstream
  const t = (en, sq) => (language === 'sq' ? sq : en);

=======
  useEffect(() => {
    const fetchWeather = async () => {
      try {
        const apiKey = "YOUR_API_KEY";
        const response = await fetch(
          `https://api.weatherapi.com/v1/current.json?key=${apiKey}&q=Prishtina`
        );
        const data = await response.json();
        if (data.error) {
          console.error('Weather API Error:', data.error.message);
        }
      } catch (error) {
        console.error("Weather fetch failed", error);
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

  useEffect(() => {
    const fetchForecast = async () => {
      try {
        const apiKey = 'YOUR_API_KEY';
        const lat = '42.665440';
        const lon = '21.165319';
        const response = await fetch(`https://api.openweathermap.org/data/2.5/forecast?lat=${lat}&lon=${lon}&appid=${apiKey}&units=metric`);
        const data = await response.json();
        if (data.list) {
          const dailyData = data.list.filter(item => item.dt_txt.includes('12:00:00')).slice(0, 5);
          setForecast(dailyData);
        }
      } catch (err) {
        console.error('Error fetching forecast:', err);
      }
    };
    fetchForecast();
  }, []);

>>>>>>> Stashed changes
  return (
    <div className="home-page">
      <header className="navbar-section">
        {/* Add Navbar here if needed */}
      </header>

      {/* Section 1 */}
      <section className="section welcome-section">
        <div className="overlay-text">
          <h1>{t('Welcome to MBI RE', 'Mirë se vini në MBI RE')}</h1>
          <p>{t('Explore the skies with premium experiences.', 'Eksploro qiellin me eksperienca premium.')}</p>
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
                <option value="">{t('Select Departure Airport', 'Zgjedh Aeroportin e Nisjes')}</option>
                {airports.map((airport) => (
                  <option key={airport.id} value={airport.city}>
                    {airport.city}
                  </option>
                ))}
              </Field>
              <ErrorMessage name="from" component="div" className="error" />

              <Field as="select" name="to">
                <option value="">{t('Select Arrival Airport', 'Zgjedh Aeroportin e Mberritjes')}</option>
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

              <Field type="number" name="passengers" min="1" placeholder={t('Passengers', 'Pasagjerë')} />
              <ErrorMessage name="passengers" component="div" className="error" />

              <button type="submit">{t('Search', 'Kërko')}</button>
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
                <th>{t('FLIGHT NO', 'NR. I FLUTURIMIT')}</th>
                <th>{t('ORIGIN', 'NISJA')}</th>
                <th>{t('DESTINATION', 'DESTINACIONI')}</th>
                <th>{t('DEPARTURE TIME', 'KOHA E NISJES')}</th>
                <th>{t('ARRIVAL TIME', 'KOHA E MBERITJES')}</th>
                <th>{t('ACTIVE', 'AKTIV')}</th>
              </tr>
            </thead>
<<<<<<< Updated upstream
            <tbody>
              {/* Flights will be dynamically inserted here */}
            </tbody>
=======
            <tbody></tbody>
>>>>>>> Stashed changes
          </table>
          <button className="view-flights-btn" onClick={() => window.location.href = "/flights"}>
            {t('View All Flights', 'Shiko të gjitha fluturimet')}
          </button>
        </div>
      </section>

      {/* Section 4 */}
      <section className="section placeholder-section">
        <div className="placeholder-box">DIZAJN</div>
      </section>

<<<<<<< Updated upstream
      {/* Section 5 */}
      <section className="section fullscreen-image-section">
        <img src="/public/epesta.jpg" alt="Banner" className="full-img" />
=======
      <section className="section weather-section">
        <h2 className="weather-title">{t('5-Day Forecast', 'Parashikimi 5-Ditor')}</h2>
        <div className="weather-cards">
          {forecast.map((day, index) => (
            <div className="weather-card" key={index}>
              <p><strong>{new Date(day.dt_txt).toLocaleDateString()}</strong></p>
              <img src={`https://openweathermap.org/img/wn/${day.weather[0].icon}@2x.png`} alt="weather" />
              <p>{Math.round(day.main.temp)}°C</p>
              <p>{day.weather[0].main}</p>
            </div>
          ))}
        </div>
>>>>>>> Stashed changes
      </section>
    </div>
  );
};

export default HomePage;
