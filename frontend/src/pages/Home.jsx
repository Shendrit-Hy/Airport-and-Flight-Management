import React, { useEffect, useState } from 'react';
import '../styles/Home.css';

const HomePage = () => {
  const [weather, setWeather] = useState({
    temp: '',
    condition: '',
    icon: '',
  });

  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');

  useEffect(() => {
    const fetchWeather = async () => {
      try {
        const apiKey = '0ca76fecb5214fc98d8124203251805'; // Replace with your actual WeatherAPI key
        const city = 'Prishtina';
        const response = await fetch(
          `https://api.weatherapi.com/v1/current.json?key=${apiKey}&q=${city}&aqi=no`
        );
        const data = await response.json();

        if (data.error) {
          throw new Error(data.error.message);
        }

        setWeather({
          temp: data.current.temp_c,
          condition: data.current.condition.text,
          icon: data.current.condition.icon.startsWith('//')
            ? 'https:' + data.current.condition.icon
            : data.current.condition.icon,
        });

        setLoading(false);
      } catch (err) {
        console.error('Weather fetch failed:', err);
        setError('Failed to load weather data.');
        setLoading(false);
      }
    };

    fetchWeather();
  }, []);

  return (
    <div className="home-page">
      <header className="navbar-section"></header>

      {/* Section 1: Welcome */}
      <section className="section welcome-section">
        <div className="overlay-text">
          <h1 className="home-title">Welcome to MBI RE</h1>
          <p className="home-text">
            Experience stress-free travel planning with MbiRe. From booking flights to managing 
            your journey, we bring everything together in one smooth, intuitive platform. 
            Whether you're flying for business or adventure, your journey starts here.
          </p>
        </div>
      </section>

      {/* Section 2: Flights */}
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
              {/* Flight data goes here */}
            </tbody>
          </table>
          <button
            className="view-flights-btn"
            onClick={() => (window.location.href = '/flights')}
          >
            View All Flights
          </button>
        </div>
      </section>

      {/* Section 3: Search */}
      <section className="section search-section">
        <form className="search-form" method="GET" action="/filteredflights">
          <p className="search-section-title">Search for flights</p>
          <input type="text" name="from" placeholder="From" />
          <input type="text" name="to" placeholder="To" />
          <input type="date" name="date" />
          <button type="submit">Search</button>
        </form>
        <div className="image-group">
          <img className="search-section-photo1" src="/public/edyta.jpg" alt="Plane 1" />
          <img className="search-section-photo2" src="/public/etreta.jpg" alt="Plane 3" />

        </div>
      </section>

      {/* Section 4: Placeholder */}
      {/* <section className="section placeholder-section">
        <div className="placeholder-box">DIZAJN</div>
      </section> */}

      {/* Section 5: Weather */}
      <section className="section weather-section">
        <h2 className="weather-title">Current Weather in Prishtina</h2>
        {loading ? (
          <p className="weather-info">Loading weather...</p>
        ) : error ? (
          <p className="weather-info">{error}</p>
        ) : (
          <>
            <p className="weather-info">{weather.condition}</p>
            <p className="weather-temp">{weather.temp}°C</p>
            {weather.icon && (
              <img
                src={weather.icon}
                alt="Weather icon"
                className="weather-icon"
              />
            )}
          </>
        )}
      </section>
    </div>
  );
};

export default HomePage;
