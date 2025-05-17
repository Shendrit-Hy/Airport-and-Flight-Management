import React from 'react';
import "../styles/FilteredFlights.css";

const FlightCard = () => (
  <div className="filteredflights-card">
    <div className="filteredflights-row">
      <input type="text" placeholder="From" className="filteredflights-input" />
      <input type="text" placeholder="To" className="filteredflights-input" />
    </div>
    <div className="filteredflights-row">
      <input type="text" placeholder="Departure" className="filteredflights-input" />
      <input type="text" placeholder="Arrival" className="filteredflights-input" />
    </div>
    <div className="filteredflights-row">
      <input type="text" placeholder="Number of available seats" className="filteredflights-input filteredflights-seats" />
      <input type="text" placeholder="Price" className="filteredflights-input filteredflights-price" />
      <button className="filteredflights-buy-btn">Buy</button>
    </div>
  </div>
);

const FilteredFlights = () => {
  return (
    <div className="filteredflights-wrapper">
      <div className="filteredflights-header">
        <h2>Filtered Flights</h2>
        <button className="filteredflights-filter-btn">
          Filter By <span>▼</span>
        </button>
      </div>
      <FlightCard />
      <FlightCard />
    </div>
  );
};

export default FilteredFlights;
