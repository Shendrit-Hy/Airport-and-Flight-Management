import React, { useState, useContext } from 'react';
import { Formik, Form, Field } from 'formik';
import { AuthContext } from '../context/AuthContext';
import '../styles/CheckInPage.css';

const CheckInPage = () => {
  const { user } = useContext(AuthContext);
  const [ticket, setTicket] = useState(null);
  const [error, setError] = useState('');
  const handleSearch = async (values) => {

//     // mock për testim
//     const fakeData = {
//       id: 1,
//       passengerName: 'Vlera Derplanin',
//       flightNumber: 'AF129',
//       date: '2025-05-20'
//     };
//
//     setTicket(fakeData);
//     setError('');
//   };


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
    <div className="checkin-container">
      <h2>Check-In për Fluturim</h2>
      <Formik initialValues={{ code: '' }} onSubmit={handleSearch}>
        <Form className="checkin-form">
          <label htmlFor="code">Shkruaj Kodin e Biletës</label>
          <Field id="code" name="code" placeholder="p.sh. ABC123" />
          <button type="submit">Kërko</button>
           <div className="button-group">
              <button onClick={() => window.history.back()} className="back-button">
                  Back
              </button>
           </div>
        </Form>
      </Formik>

      {error && <p className="error">{error}</p>}

      {ticket && (
        <div className="ticket-info">
          <h3>Detajet e Biletës</h3>
          <p><strong>Emri:</strong> {ticket.passengerName}</p>
          <p><strong>Fluturimi:</strong> {ticket.flightNumber}</p>
          <p><strong>Data:</strong> {ticket.date}</p>
          <button onClick={handleCheckIn}>Bëj Check-In</button>

        </div>
      )}
    </div>
  );
};

export default CheckInPage;
