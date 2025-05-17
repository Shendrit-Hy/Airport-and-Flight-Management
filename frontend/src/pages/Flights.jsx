import React, { useEffect, useState } from "react";
import "../styles/Flights.css";
import { getFlights } from "../api/api";
import { getTenantIdFromSubdomain } from "../utils/getTenantId";

const Flights = () => {
  const [flights, setFlights] = useState([]);
  const [searchTerm, setSearchTerm] = useState("");

  useEffect(() => {
    const tenantId = getTenantIdFromSubdomain();
    getFlights(tenantId)
      .then((res) => {
        setFlights(res.data);
      })
      .catch((err) => {
        console.error("Failed to fetch flights:", err);
      });
  }, []);

  const filteredFlights = flights.filter((flight) => {
    const term = searchTerm.toLowerCase();
    return (
      flight.flightNumber?.toLowerCase().includes(term) ||
      flight.departureAirport?.toLowerCase().includes(term) ||
      flight.arrivalAirport?.toLowerCase().includes(term) ||
      flight.airline?.toLowerCase().includes(term)
    );
  });

  return (
    <div className="flights-page-container">
      <div className="flights-main-container">
        <h2 className="flights-title">FLIGHTS</h2>

        <div className="flights-search-bar">
          <input
            type="text"
            placeholder="SEARCH BY FLIGHT, CITY OR AIRLINE"
            value={searchTerm}
            onChange={(e) => setSearchTerm(e.target.value)}
            className="flights-search-input"
          />
          <button className="flights-search-button">
            <span>&#128269;</span>
          </button>
        </div>

        <div className="flights-table-wrapper">
          <table className="flights-table">
            <thead>
              <tr>
                <th>FLIGHT NO</th>
                <th>ORIGIN</th>
                <th>DESTINATION</th>
                <th>DEPARTURE TIME</th>
                <th>ARRIVAL TIME</th>
                <th>AIRLINE</th>
              </tr>
            </thead>
            <tbody>
              {filteredFlights.map((flight, index) => (
                <tr key={index}>
                  <td>{flight.flightNumber}</td>
                  <td>{flight.departureAirport}</td>
                  <td>{flight.arrivalAirport}</td>
                  <td>{flight.departureTime}</td>
                  <td>{flight.arrivalTime}</td>
                  <td>{flight.airline}</td>
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
