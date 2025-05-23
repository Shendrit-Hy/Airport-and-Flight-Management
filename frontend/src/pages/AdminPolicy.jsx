import React, { useEffect, useState } from 'react';
import { Formik, Form, Field, ErrorMessage } from 'formik';
import * as Yup from 'yup';
import '../styles/AdminAirportPage.css';
import {
  getAllPolicies,
  createPolicy,
  deletePolicy
} from '../api/policyService';
import { getTenantIdFromSubdomain } from '../utils/getTenantId';

export default function AdminPolicy() {
  const [policies, setPolicies] = useState([]);
  const token = localStorage.getItem('token');
  const tenantId = getTenantIdFromSubdomain();

  const loadPolicies = async () => {
    try {
      const policyList = await getAllPolicies(tenantId);
      setPolicies(Array.isArray(policyList) ? policyList : []);
    } catch (err) {
      console.error('Error loading policies:', err);
    }
  };


  useEffect(() => {
    loadPolicies();
  }, []);

  const handleAdd = async (values, { resetForm }) => {
    try {
      await createPolicy(values, token, tenantId);
      resetForm();
      loadPolicies();
    } catch (err) {
      console.error('Error creating policy:', err);
    }
  };

  const handleDelete = async (id) => {
    try {
      await deletePolicy(id, token, tenantId);
      loadPolicies();
    } catch (err) {
      console.error('Error deleting policy:', err);
    }
  };

  return (
    <div className="airport-layout">
      <aside className="airport-sidebar">
        <div className="airport-logo">MBI RE</div>
        <nav className="airport-nav-group">
          {['dashboard', 'airport', 'booking', 'faqs', 'flightspage', 'maintenance', 'passangers', 'payments', 'staff', 'support', 'announcements', 'city', 'languages', 'trending', 'policy', 'gate', 'terminal']
            .map((item) => (
              <div className="airport-nav-row" key={item}>
                <a href={`/admin/${item}`}>{item.toUpperCase()}</a>
              </div>
            ))}
        </nav>
      </aside>

      <main className="airport-main-content" style={{
        backgroundImage: "url('../../public/AdminPolicyImage.avif')",
        backgroundRepeat: 'no-repeat',
        backgroundSize: 'cover',
        backgroundPosition: 'center',
        width: '100%',
        height: 'auto'
      }}>
        <header className="airport-header">
          <h2 style={{ color: "black" }}>POLICY</h2>
          <div className="airport-admin-title" style={{ color: "black" }}>ADMIN</div>
        </header>

        <Formik
          initialValues={{ title: '', content: '', type: '' }}
          validationSchema={Yup.object({
            title: Yup.string().required('Required'),
            content: Yup.string().required('Required'),
            type: Yup.string().required('Required'),
          })}
          onSubmit={handleAdd}
        >
          <Form className="airport-add-form">
            <div style={{ display: 'flex', flexWrap: 'wrap', gap: '10px' }}>
              {['title', 'content', 'type'].map((field) => (
                <div className="airport-input-group" key={field} style={{ flex: '1 1 200px' }}>
                  <Field
                    type="text"
                    id={field}
                    name={field}
                    placeholder={field.charAt(0).toUpperCase() + field.slice(1)}
                    className="airport-input"
                  />
                  <ErrorMessage name={field} component="div" className="airport-error" />
                </div>
              ))}
              <div style={{ display: 'flex', alignItems: 'center', gap: '10px', flex: '1 1 200px' }}>
                <button type="submit" className="airport-add-btn" style={{ height: '38px' }}>
                  ADD
                </button>
              </div>
            </div>
          </Form>
        </Formik>

        <div className="airport-table">
          <div className="airport-table-header" style={{ display: 'flex', width: '100%', fontWeight: 'bold' }}>
            <span style={{ width: '30%' }}>Title</span>
            <span style={{ width: '40%' }}>Content</span>
            <span style={{ width: '15%' }}>Type</span>
            <span style={{ width: '15%' }}>Actions</span>
          </div>

          {policies.map((policy) => (
            <div className="airport-table-row" key={policy.id} style={{ display: 'flex', width: '100%' }}>
              <span style={{ width: '30%' }}>{policy.title}</span>
              <span style={{ width: '40%' }}>{policy.content}</span>
              <span style={{ width: '15%' }}>{policy.type}</span>
              <span style={{ width: '15%' }}>
                <button className="airport-delete-btn" onClick={() => handleDelete(policy.id)}>
                  🗑
                </button>
              </span>
            </div>
          ))}
        </div>
      </main>
    </div>
  );
}
