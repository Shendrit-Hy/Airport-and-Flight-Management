import React, { useEffect, useState } from 'react';
import { useLocation } from 'react-router-dom';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';
import "../styles/FilteredFlights.css";
import { getTenantIdFromSubdomain } from '../utils/getTenantId';

const useQuery = () => {
  return new URLSearchParams(useLocation().search);
};

const FlightCard = ({ flight }) => {
  const navigate = useNavigate();

  const handleBuy = () => {
    navigate('/booking', { state: { flight } });
  };

  return (
    <div className="filteredflights-card">
      <div className="filteredflights-row">
        <span className="filteredflights-text"><strong>From:</strong> {flight.departureAirport}</span>
        <span className="filteredflights-text"><strong>To:</strong> {flight.arrivalAirport}</span>
      </div>
      <div className="filteredflights-row">
        <span className="filteredflights-text"><strong>Departure:</strong> {flight.departureTime}</span>
        <span className="filteredflights-text"><strong>Arrival:</strong> {flight.arrivalTime}</span>
      </div>
      <div className="filteredflights-row">
        <span className="filteredflights-text"><strong>Seats:</strong> {flight.availableSeat}</span>
        <span className="filteredflights-text"><strong>Price:</strong> €{flight.price}</span>
        <button className="filteredflights-buy-btn" onClick={handleBuy}>Buy</button>
      </div>
    </div>
  );
};

const FilteredFlights = () => {
  const query = useQuery();
  const [flights, setFlights] = useState([]);
  const [loading, setLoading] = useState(true);

  const from = query.get('from');
  const to = query.get('to');
  const startDate = query.get('startDate');
  const endDate = query.get('endDate');
  const passengers = query.get('passengers');

  useEffect(() => {
    const fetchFlights = async () => {
      try {
        const response = await axios.get('http://localhost:8080/api/flights/filter', {
          params: {
            from,
            to,
            startDate,
            endDate,
            passengers
          },
          headers: {
            'X-Tenant-ID': getTenantIdFromSubdomain()
          }
        });
        setFlights(response.data);
        console.log(response.data.id)
      } catch (error) {
        console.error('Failed to fetch flights:', error);
      } finally {
        setLoading(false);
      }
    };

    fetchFlights();
  }, [from, to, startDate, endDate, passengers]);

  return (
    <div className="filteredflights-wrapper">
      <div className="filteredflights-header">
        <h2>Filtered Flights</h2>
        <button className="filteredflights-filter-btn">
          Filter By <span>▼</span>
        </button>
      </div>

      {loading ? (
        <p>Loading flights...</p>
      ) : flights.length === 0 ? (
        <p>No flights found.</p>
      ) : (
        flights.map(flight => (
          <FlightCard key={flight.id} flight={flight} />
        ))
      )}
    </div>
  );
};

export default FilteredFlights;
