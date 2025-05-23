import React, { useEffect, useState } from 'react';
import '../styles/userProfile.css';
import { useLanguage } from '../context/LanguageContext';
import axios from '../utils/axiosInstance';

export default function UserProfile() {
  const { t } = useLanguage();

  const [user, setUser] = useState({
    username: '',
    fullName: '',
    email: '',
    role: ''
  });

  const [flights, setFlights] = useState([]);

  useEffect(() => {
    const token = localStorage.getItem("token");
    const tenantId = localStorage.getItem("tenantId") || "default";

    axios.get('/api/auth/profile', {
      headers: {
        Authorization: `Bearer ${token}`,
        "X-Tenant-ID": tenantId
      }
    }).then(res => {
      setUser(res.data);
    }).catch(err => {
      console.error("Gabim gjatë marrjes së profilit:", err);
    });

    axios.get('/api/flights', {
      headers: {
        "X-Tenant-ID": tenantId
      }
    }).then(res => {
      setFlights(res.data);
    }).catch(err => {
      console.error("Gabim gjatë marrjes së fluturimeve:", err);
    });
  }, []);

  const handleCancel = (flightId) => {
    setFlights(prev =>
      prev.map(f =>
        f.id === flightId ? { ...f, flightStatus: 'CANCELLED' } : f
      )
    );
  };

  const handleCheckIn = (flightId) => {
    setFlights(prev =>
      prev.map(f =>
        f.id === flightId ? { ...f, checked: 'Yes' } : f
      )
    );
  };

  return (
    <div className="userprofile-page">
      <div className="userprofile-main">
        <header className="userprofile-topbar">
          <span>{t(user.role, user.role)}</span>
        </header>

        <div className="userprofile-container">
          <div className="userprofile-left">
            <button className="userprofile-btn">{t("Username", "Emri i përdoruesit")}: {user.username}</button>
            <button className="userprofile-btn">{t("Full Name", "Emri i Plotë")}: {user.fullname}</button>
            <button className="userprofile-btn">{t("Email", "Email")}: {user.email}</button>
          </div>

          <div className="userprofile-right">
            <div className="userprofile-flight-box">
              <p>{t("NUMBER OF", "NUMRI I")}<br />{t("FLIGHTS", "FLUTURIMEVE")}</p>
              <h3>{flights.length}</h3>
            </div>
          </div>
        </div>

        <div className="userprofile-table">
          <table>
            <thead>
              <tr>
                <th>{t("Flight No", "Nr. Fluturimit")}</th>
                <th>{t("Origin", "Prejardhja")}</th>
                <th>{t("Destination", "Destinacioni")}</th>
                <th>{t("Departure", "Nisja")}</th>
                <th>{t("Arrival", "Ardhja")}</th>
                <th>{t("Price (€)", "Çmimi (€)")}</th>
                <th>{t("Status", "Statusi")}</th>
                <th>{t("Checked", "I regjistruar")}</th>
                <th>{t("Actions", "Veprime")}</th>
              </tr>
            </thead>
            <tbody>
              {flights.map((flight) => (
                <tr key={flight.id}>
                  <td>{flight.flightNumber}</td>
                  <td>{flight.departureAirport}</td>
                  <td>{flight.arrivalAirport}</td>
                  <td>{flight.departureTime}</td>
                  <td>{flight.arrivalTime}</td>
                  <td>{flight.price}</td>
                  <td>{flight.flightStatus}</td>
                  <td>{flight.checked}</td>
                  <td>
                    {flight.flightStatus === 'SCHEDULED' && (
                      <>
                        <button onClick={() => handleCancel(flight.id)}>{t("Cancel", "Anulo")}</button>
                        <button onClick={() => handleCheckIn(flight.id)}>{t("Check In", "Regjistrohu")}</button>
                      </>
                    )}
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      </div>
    </div>
  );
}
