import React, { useEffect, useState } from 'react';
import { Formik, Form, Field, ErrorMessage } from 'formik';
import * as Yup from 'yup';
import '../styles/AdminAirportPage.css';
import { getGates, createGate, deleteGate } from '../api/gateService';
import { getTenantIdFromSubdomain } from '../utils/getTenantId';

export default function AdminGate() {
  const [gates, setGates] = useState([]);
  const tenantId = getTenantIdFromSubdomain();
  const token = localStorage.getItem("token");

  useEffect(() => {
    loadGates();
  }, []);

  const loadGates = async () => {
    try {
      const data = await getGates(tenantId);
      console.log('Loaded gates:', data);
      setGates(Array.isArray(data) ? data : []);
    } catch (err) {
      console.error('Error loading gates:', err);
    }
  };

  const handleAdd = async (values, { resetForm }) => {
    try {
      await createGate(tenantId, values, token);
      resetForm();
      loadGates();
    } catch (err) {
      console.error('Error creating gate:', err);
    }
  };

  const handleDelete = async (id) => {
    try {
      await deleteGate(tenantId, id, token);
      loadGates();
    } catch (err) {
      console.error('Error deleting gate:', err);
    }
  };

  return (
    <div className="airport-layout">
      <aside className="airport-sidebar">
        <div className="airport-logo">MBI RE</div>
        <nav className="airport-nav-group">
          {[
            'dashboard', 'airport', 'booking', 'faqs', 'flightspage',
            'maintenance', 'passangers', 'payments', 'staff', 'support',
            'announcements', 'city', 'languages', 'trending', 'policy', 'gate', 'terminal'
          ].map((item) => (
            <div className="airport-nav-row" key={item}>
              <a href={`/admin/${item}`}>{item.toUpperCase()}</a>
            </div>
          ))}
        </nav>
      </aside>

      <main className="airport-main-content" style={{
        backgroundImage: "url('../../public/AdminGateImage.avif')",
        backgroundRepeat: 'no-repeat',
        backgroundSize: 'cover',
        backgroundPosition: 'center',
        width: '100%',
        height: 'auto'
      }}>
        <header className="airport-header">
          <h2>GATE</h2>
          <div className="airport-admin-title">ADMIN</div>
        </header>

        <Formik
          initialValues={{ gateNumber: '', terminalId: '', terminalName: '' }}
          validationSchema={Yup.object({
            gateNumber: Yup.string().required('Required'),
            terminalId: Yup.string().required('Required'),
            terminalName: Yup.string().required('Required'),
          })}
          onSubmit={handleAdd}
        >
          <Form className="airport-add-form">
            <div className="airport-form-grid">
              <div className="airport-input-group">
                <Field type="text" name="gateNumber" placeholder="Gate Number" className="airport-input" />
                <ErrorMessage name="gateNumber" component="div" className="airport-error" />
              </div>
              <div className="airport-input-group">
                <Field type="text" name="terminalId" placeholder="Terminal ID" className="airport-input" />
                <ErrorMessage name="terminalId" component="div" className="airport-error" />
              </div>
              <div className="airport-input-group">
                <Field type="text" name="terminalName" placeholder="Terminal Name" className="airport-input" />
                <ErrorMessage name="terminalName" component="div" className="airport-error" />
              </div>
            </div>
            <button type="submit" className="airport-add-btn">ADD</button>
          </Form>
        </Formik>

        <div className="airport-table">
          <div className="airport-table-header" style={{ width: '100%', display: 'flex' }}>
            <span style={{ width: '25%' }}>Gate Number</span>
            <span style={{ width: '25%' }}>Terminal ID</span>
            <span style={{ width: '25%' }}>Terminal Name</span>
            <span style={{ width: '25%' }}>Actions</span>
          </div>

          {gates.map((gate) => (
            <div className="airport-table-row" key={gate.id} style={{ display: 'flex', width: '100%' }}>
              <span style={{ width: '25%' }}>{gate.gateNumber}</span>
              <span style={{ width: '25%' }}>{gate.terminalId}</span>
              <span style={{ width: '25%' }}>{gate.terminalName}</span>
              <span style={{ width: '25%' }}>
                <button className="airport-delete-btn" onClick={() => handleDelete(gate.id)}>🗑</button>
              </span>
            </div>
          ))}
        </div>
      </main>
    </div>
  );
}
