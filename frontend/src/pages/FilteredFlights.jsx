import React, { useEffect, useState } from 'react';
import { useLocation, useNavigate } from 'react-router-dom';
import "../styles/FilteredFlights.css";
import { getTenantIdFromSubdomain } from '../utils/getTenantId';
import { useLanguage } from '../context/LanguageContext';
import { filterFlights } from '../api/flightService';

const useQuery = () => {
  return new URLSearchParams(useLocation().search);
};

const FlightCard = ({ flight }) => {
  const navigate = useNavigate();
  const { t } = useLanguage();

  const handleBuy = () => {
    navigate('/booking', { state: { flight } });
  };

  return (
    <div className="filteredflights-card">
      <div className="filteredflights-row">
        <span className="filteredflights-text"><strong>{t("From", "Prej")}:</strong> {flight.departureAirport}</span>
        <span className="filteredflights-text"><strong>{t("To", "Deri")}:</strong> {flight.arrivalAirport}</span>
      </div>
      <div className="filteredflights-row">
        <span className="filteredflights-text"><strong>{t("Departure", "Nisja")}:</strong> {flight.departureTime}</span>
        <span className="filteredflights-text"><strong>{t("Arrival", "Ardhja")}:</strong> {flight.arrivalTime}</span>
      </div>
      <div className="filteredflights-row">
        <span className="filteredflights-text"><strong>{t("Seats", "Ulëset")}:</strong> {flight.availableSeat}</span>
        <span className="filteredflights-text"><strong>{t("Price", "Çmimi")}:</strong> €{flight.price}</span>
        <button className="filteredflights-buy-btn" onClick={handleBuy}>{t("Buy", "Bli")}</button>
      </div>
    </div>
  );
};

const FilteredFlights = () => {
  const query = useQuery();
  const [flights, setFlights] = useState([]);
  const [loading, setLoading] = useState(true);
  const { t } = useLanguage();

  const from = query.get('from');
  const to = query.get('to');
  const startDate = query.get('startDate');
  const endDate = query.get('endDate');
  const passengers = query.get('passengers');
  const tenantId = getTenantIdFromSubdomain();

  useEffect(() => {
    const fetchFlights = async () => {
      try {
        const response = await filterFlights({ from, to, startDate, endDate, passengers }, tenantId);
        setFlights(response.data);
      } catch (error) {
        console.error('Failed to fetch flights:', error);
      } finally {
        setLoading(false);
      }
    };

    fetchFlights();
  }, [from, to, startDate, endDate, passengers, tenantId]);

  return (
    <div className="filteredflights-wrapper">
      <div className="filteredflights-header">
        <h2>{t("Filtered Flights", "Fluturimet e Filtruara")}</h2>
        <button className="filteredflights-filter-btn">
          {t("Filter By", "Filtro sipas")} <span>▼</span>
        </button>
      </div>

      {loading ? (
        <p>{t("Loading flights...", "Duke i ngarkuar fluturimet...")}</p>
      ) : flights.length === 0 ? (
        <p>{t("No flights found.", "Asnjë fluturim nuk u gjet.")}</p>
      ) : (
        flights.map(flight => (
          <FlightCard key={flight.id} flight={flight} />
        ))
      )}
    </div>
  );
};

export default FilteredFlights;
