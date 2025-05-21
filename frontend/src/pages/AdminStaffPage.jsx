import React, { useEffect, useState } from 'react';
import { Formik, Form, Field, ErrorMessage } from 'formik';
import * as Yup from 'yup';
import '../styles/AdminStaffPage.css';
import "../styles/AdminAirportPage.css";
import {
  getStaffList,
  deleteStaffById,
  addStaff
} from '../api/staffService';
import { getTenantIdFromSubdomain } from '../utils/getTenantId';

export default function AdminStaffPage() {
  const [staffList, setStaffList] = useState([]);
  const token = localStorage.getItem('token');
  const tenantId = getTenantIdFromSubdomain();

  useEffect(() => {
    if (token && tenantId) {
      loadStaff();
    }
  }, []);

  const loadStaff = async () => {
    try {
      const data = await getStaffList(tenantId, token);
      setStaffList(data.data);
    } catch (err) {
      console.error('Failed to load staff:', err);
    }
  };

  const handleDelete = async (id) => {
    try {
      await deleteStaffById(id, tenantId, token);
      loadStaff();
    } catch (err) {
      console.error('Failed to delete staff:', err);
    }
  };

  const handleAddStaff = async (values, { resetForm }) => {
    try {
      await addStaff(values, tenantId, token);
      resetForm();
      loadStaff();
    } catch (err) {
      console.error('Failed to add staff:', err);
    }
  };

  return (
    <div className="adminstaff-layout">
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

      <main className="adminstaff-main">
        <header className="adminstaff-header">
          <h2>STAFF</h2>
          <div className="adminstaff-title">ADMIN</div>
        </header>

        <Formik
          initialValues={{
            name: '',
            role: '',
            email: '',
            shiftStart: '',
            shiftEnd: ''
          }}
          validationSchema={Yup.object({
            name: Yup.string().required('Required'),
            role: Yup.string().required('Required'),
            email: Yup.string().email('Invalid email').required('Required'),
            shiftStart: Yup.string().required('Required'),
            shiftEnd: Yup.string().required('Required')
          })}
          onSubmit={handleAddStaff}
        >
          <Form className="adminstaff-add-form">
            <div className="adminstaff-form-left">
              <Field type="text" name="name" placeholder="Full Name" required />
              <ErrorMessage name="name" component="div" className="adminstaff-error" />

              <Field type="time" name="shiftStart" placeholder="Shift Start" required />
              <ErrorMessage name="shiftStart" component="div" className="adminstaff-error" />

              <Field type="time" name="shiftEnd" placeholder="Shift End" required />
              <ErrorMessage name="shiftEnd" component="div" className="adminstaff-error" />

              <Field type="text" name="role" placeholder="Role" required />
              <ErrorMessage name="role" component="div" className="adminstaff-error" />

              <Field type="email" name="email" placeholder="Email" required />
              <ErrorMessage name="email" component="div" className="adminstaff-error" />
            </div>
            <button type="submit" className="adminstaff-add-btn">ADD</button>
          </Form>
        </Formik>

        <div className="adminstaff-table">
          <div className="adminstaff-table-header">
            <span>Full Name</span>
            <span>Role</span>
            <span>Email</span>
            <span>Shift Start</span>
            <span>Shift End</span>
            <span>Actions</span>
          </div>

          {staffList.map((staff) => (
            <div className="adminstaff-table-row" key={staff.id}>
              <span>{staff.name}</span>
              <span>{staff.role}</span>
              <span>{staff.email}</span>
              <span>{staff.shiftStart}</span>
              <span>{staff.shiftEnd}</span>
              <span>
                <button onClick={() => handleDelete(staff.id)} className="adminstaff-delete-btn">🗑</button>
              </span>
            </div>
          ))}
        </div>
      </main>
    </div>
  );
}
