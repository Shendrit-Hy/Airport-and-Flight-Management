import React from 'react';
import '../styles/Home.css';

const HomePage = () => {
  return (
    <div className="home-page">

      <header className="navbar-section">
   }
      </header>

      {/* Seksioni 1 */}
      <section className="section welcome-section">
        <div className="overlay-text">
          <h1>Welcome to MBI RE</h1>
          <p>Explore the skies with premium experiences.</p>
        </div>
      </section>

      {/* Seksioni 2 */}
     <section className="section search-section">
       <div className="image-group">
         <img src="/public/plane-bg.jpg" alt="Plane 1" />
         <img src="/public/plane-bg.jpg" alt="Plane 2" />
       </div>
       <form className="search-form" method="GET" action="/filteredflights">
         <input type="text" name="from" placeholder="From" />
         <input type="text" name="to" placeholder="To" />
         <input type="date" name="date" />
         <button type="submit">Search</button>
       </form>
     </section>

    <section className="section flights-section">
      <div className="plane-image-left">
        <img src="/public/plane-bg.jpg" alt="Jet" />
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
            {/* 3 */}
          </tbody>
        </table>
        <button className="view-flights-btn" onClick={() => window.location.href = "/flights"}>
          View All Flights
        </button>
      </div>
    </section>

    {/* Seksioni 4*/}
    <section className="section placeholder-section">
      <div className="placeholder-box">DIZAJN</div>
    </section>

    {/* Seksioni 5*/}
    <section className="section fullscreen-image-section">
      <img src="/public/plane-bg.jpg" alt="Banner" className="full-img" />
    </section>
    </div>
  );
};

export default HomePage;
