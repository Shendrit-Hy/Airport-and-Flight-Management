import React from 'react';
import "../styles/FilteredFlights.css";

const FlightCard = () => (
  <div className="flight-card">
    <div className="row">
      <input type="text" placeholder="From" />
      <input type="text" placeholder="To" />
    </div>
    <div className="row">
      <input type="text" placeholder="Departure" />
      <input type="text" placeholder="Arrival" />
    </div>
    <div className="row">
      <input type="text" placeholder="Number of available seats" className="seats-input" />
      <input type="text" placeholder="Price" className="price-input" />
      <button className="buy-btn">Buy</button>
    </div>
  </div>
);

const FilteredFlights = () => {
  return (
    <div className="filtered-flights">
      <div className="header">
        <h2>Filtered Flights</h2>
        <button className="filter-btn">
          Filter By <span>▼</span>
        </button>
      </div>
      <FlightCard />
      <FlightCard />
    </div>
  );
};

export default FilteredFlights;
