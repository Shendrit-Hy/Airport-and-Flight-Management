import React, { useState } from "react";
import "../styles/Flights.css";

const Flights = ({ data }) => {
  const [searchTerm, setSearchTerm] = useState("");

  // No filtering; show all flights directly
  const displayedFlights = data || []; // fallback to empty array if undefined

  return (
    <div className="page-container">
      <div className="flights-container">
        <h2>FLIGHTS</h2>
        <div className="search-bar">
          <input
            type="text"
            placeholder="SEARCH"
            value={searchTerm}
            onChange={(e) => setSearchTerm(e.target.value)}
          />
          <button>
            <span>&#128269;</span>
          </button>
        </div>

        <div className="table-wrapper">
          <table>
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
              {displayedFlights.map((flight, index) => (
                <tr key={index}>
                  <td>{flight.flightNumber}</td>
                  <td>{flight.origin}</td>
                  <td>{flight.destination}</td>
                  <td>{flight.departureTime}</td>
                  <td>{flight.arrivalTime}</td>
                  <td>{flight.active ? "Yes" : "No"}</td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      </div>
    </div>
  );
};

export default Flights;
