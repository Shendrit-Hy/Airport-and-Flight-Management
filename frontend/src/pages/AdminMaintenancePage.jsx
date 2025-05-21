import React, { useEffect, useState } from 'react';
import { Formik, Form, Field, ErrorMessage } from 'formik';
import * as Yup from 'yup';
import '../styles/AdminMaintenancePage.css';
import "../styles/AdminAirportPage.css";
import {
  getMaintenances,
  createMaintenance,
  deleteMaintenance
} from '../api/maintenanceService';

export default function AdminMaintenancePage() {
  const [maintenances, setMaintenances] = useState([]);

  useEffect(() => {
    loadData();
  }, []);

  const loadData = async () => {
    const res = await getMaintenances();
    setMaintenances(res.data);
  };

  const handleAdd = async (values, { resetForm }) => {
    const payload = {
      airportCode: values.airportId,
      location: values.location,
      issueType: values.issueType,
      reportedBy: values.reportedBy,
      priority: values.priority,
      status: values.status,
      description: values.description
    };

    await createMaintenance(payload);
    resetForm();
    loadData();
  };

  const handleDelete = async (id) => {
    await deleteMaintenance(id);
    loadData();
  };

  return (
    <div className="adminmaintenance-layout">
      <aside className="airport-sidebar">
        <div className="airport-logo">MBI RE</div>
        <nav className="airport-nav-group">
          {["dashboard", "airport", "booking", "faqs", "flightspage", "maintenance", "passangers", "payments", "staff", "support", "announcements"].map((item) => (
            <div className="airport-nav-row" key={item}>
              <a href={`/admin/${item}`}>{item.toUpperCase()}</a>
            </div>
          ))}
        </nav>
      </aside>

      <main className="adminmaintenance-main-content">
        <header className="adminmaintenance-header">
          <h2>MAINTENANCE</h2>
          <div className="adminmaintenance-title">ADMIN</div>
        </header>

        <Formik
          initialValues={{
            airportId: '',
            location: '',
            issueType: '',
            reportedBy: '',
            priority: '',
            status: '',
            description: ''
          }}
          validationSchema={Yup.object({
            airportId: Yup.string().required('Required'),
            location: Yup.string().required('Required'),
            issueType: Yup.string().required('Required'),
            reportedBy: Yup.string().required('Required'),
            priority: Yup.string().required('Required'),
            status: Yup.string().required('Required'),
            description: Yup.string().required('Required')
          })}
          onSubmit={handleAdd}
        >
          <Form className="adminmaintenance-form">
            <div className="adminmaintenance-form-grid">
              {["airportId", "location", "issueType", "reportedBy", "priority", "status", "description"].map((field) => (
                <div className="adminmaintenance-input-group" key={field}>
                  <Field
                    type="text"
                    name={field}
                    placeholder={field.replace(/([A-Z])/g, ' $1')}
                    className="adminmaintenance-input"
                    style={{ backgroundColor: 'rgb(53,53,53)' }}
                  />
                  <ErrorMessage name={field} component="div" className="adminmaintenance-error" />
                </div>
              ))}
            </div>
            <button type="submit" className="adminmaintenance-add-btn">ADD</button>
          </Form>
        </Formik>

        <div className="adminmaintenance-table">
          <div className="adminmaintenance-table-header">
            <span>Airport ID</span>
            <span>Location</span>
            <span>Issue Type</span>
            <span>Reported By</span>
            <span>Priority</span>
            <span>Status</span>
            <span>Description</span>
            <span>Actions</span>
          </div>

          {maintenances.map((item) => (
            <div className="adminmaintenance-table-row" key={item.id}>
              <span>{item.airportCode}</span>
              <span>{item.location}</span>
              <span>{item.issueType}</span>
              <span>{item.reportedBy}</span>
              <span>{item.priority}</span>
              <span>{item.status}</span>
              <span>{item.description}</span>
              <span>
                <button className="adminmaintenance-delete-btn" onClick={() => handleDelete(item.id)}>🗑</button>
              </span>
            </div>
          ))}
        </div>
      </main>
    </div>
  );
}
