import React, { useState, useContext } from 'react';
import { Formik, Form, Field, ErrorMessage } from 'formik';
import * as Yup from 'yup';
import { AuthContext } from '../context/AuthContext';
import '../styles/CheckInPage.css';

const CheckInPage = () => {
  const { user } = useContext(AuthContext);
  const [ticket, setTicket] = useState(null);
  const [error, setError] = useState('');

  const handleSearch = async (values) => {
    try {
      const res = await fetch(`/api/bookings/code/${values.code}`, {
        headers: {
          'Content-Type': 'application/json',
          'X-Tenant-ID': user?.tenantId,
          Authorization: `Bearer ${localStorage.getItem('token')}`
        }
      });

      const contentType = res.headers.get('content-type');
      if (!res.ok || !contentType.includes('application/json')) {
        throw new Error('Kodi nuk u gjet ose ka ndodhur një gabim.');
      }

      const data = await res.json();
      setTicket(data);
      setError('');
    } catch (err) {
      setError(err.message);
      setTicket(null);
    }
  };

  const handleCheckIn = async () => {
    try {
      await fetch(`/api/bookings/${ticket.id}/checkin`, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
          'X-Tenant-ID': user?.tenantId,
          Authorization: `Bearer ${localStorage.getItem('token')}`
        }
      });

      alert('Check-in u krye me sukses!');
      window.location.href = '/profile';
    } catch (err) {
      alert('Gabim gjatë check-in.');
    }
  };

  return (
    <div className="checkinpage-container">
      <h2>Check-In për Fluturim</h2>
      <Formik
        initialValues={{ code: '' }}
        validationSchema={Yup.object({
          code: Yup.string().required('Kodi është i detyrueshëm')
        })}
        onSubmit={handleSearch}
      >
        <Form className="checkinpage-form">
          <label htmlFor="code">Shkruaj Kodin e Biletës</label>
          <Field id="code" name="code" placeholder="p.sh. ABC123" className="checkinpage-input" />
          <ErrorMessage name="code" component="div" className="checkinpage-error" />

          <button type="submit" className="checkinpage-submit">Kërko</button>
          <div className="checkinpage-button-group">
            <button onClick={() => window.history.back()} className="checkinpage-back-button">
              Back
            </button>
          </div>
        </Form>
      </Formik>

      {error && <p className="checkinpage-error">{error}</p>}

      {ticket && (
        <div className="checkinpage-ticket-info">
          <h3>Detajet e Biletës</h3>
          <p><strong>Emri:</strong> {ticket.passengerName}</p>
          <p><strong>Fluturimi:</strong> {ticket.flightNumber}</p>
          <p><strong>Data:</strong> {ticket.date}</p>
          <button onClick={handleCheckIn} className="checkinpage-checkin-btn">Bëj Check-In</button>
        </div>
      )}
    </div>
  );
};

export default CheckInPage;
