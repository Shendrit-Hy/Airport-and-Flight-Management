import React, { useState, useContext, useEffect } from 'react';
import { Formik, Form, Field } from 'formik';
import { AuthContext } from '../context/AuthContext';
import '../styles/SupportPage.css';
import { sendSupportRequest, getSupportRequests } from '../api/supportService';

const SupportPage = () => {
  const { user, token } = useContext(AuthContext);
  const [tickets, setTickets] = useState([]);

  const fetchTickets = async () => {
    try {
      const res = await getSupportRequests(token, user?.tenantId);
      setTickets(res.data);
    } catch (err) {
      console.error('Error loading tickets:', err);
    }
  };

  const handleSubmit = async (values, { resetForm }) => {
    try {
      await sendSupportRequest(values, token, user?.tenantId);
      alert('Support request sent!');
      resetForm();
      fetchTickets();
    } catch (err) {
      alert('Failed to send support request.');
    }
  };


  return (
       <div className="support-wrapper">
    <div className="support-page">
      <h2>Support Center</h2>

      {/* === Form për kërkesë mbështetje === */}
      <section className="support-form">
        <h3 className="titulli">Dërgo një Kërkesë</h3>
        <Formik
          initialValues={{ subject: '', message: '', email: user?.email || '' }}
          onSubmit={handleSubmit}
        >
          <Form>
            <Field name="subject" placeholder="Subjekti" className="input" />
            <Field as="textarea" name="message" placeholder="Mesazhi yt" className="textarea" />
            <Field name="email" placeholder="Email-i yt" className="input" />
            <button type="submit" className="button">Dërgo</button>
          </Form>
        </Formik>
      </section>

      {/* === Tiketat e dërguara (vetëm nëse është loguar) === */}
      {user && (
        <section className="tickets">
          <h3>Tiketat e tua</h3>
          {tickets.length === 0 ? (
            <p>Asnjë tiketë e regjistruar.</p>
          ) : (
            <ul>
              {tickets.map((t, i) => (
                <li key={i}>
                  <strong>{t.subject}</strong> - {t.message}
                </li>
              ))}
            </ul>
          )}
        </section>
      )}

      {/* === Informacione për shërbime === */}
      <section className="airport-services">
        <h3>Shërbimet e Aeroportit</h3>
        <ul>
          <li><strong>Baggage Services:</strong> Gjetja e bagazheve të humbura</li>
          <li><strong>Lost & Found:</strong> Kontakt për objekte të humbura</li>
          <li><strong>Immigration:</strong> Ndihmë për dokumentacion dhe kalime kufitare</li>
        </ul>
      </section>

      {/* === Seksioni i kontaktit/ndihmës === */}
      <section className="contact">
        <h3>Kontakt</h3>
        <p>📧 Email: support@airport.com</p>
        <p>📞 Tel: +383 44 000 000</p>
        <p>📍 Adresa: Aeroporti Ndërkombëtar, Prishtinë</p>
      </section>
    </div>
      </div>
  );
};

export default SupportPage;
