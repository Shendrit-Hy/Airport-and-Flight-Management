import React, { useEffect, useState } from 'react';
import { Formik, Form, Field, ErrorMessage } from 'formik';
import * as Yup from 'yup';
import '../styles/AdminAirportPage.css';
import languageService from '../api/languageService';
import { getTenantIdFromSubdomain } from '../utils/getTenantId';

export default function AdminLanguages() {
  const [languages, setLanguages] = useState([]);
  const tenantId = getTenantIdFromSubdomain();

  useEffect(() => {
    loadLanguages();
  }, []);

const loadLanguages = async () => {
  try {
    const res = await languageService.getLanguages(tenantId);
    console.log('API Response:', res);
    if (Array.isArray(res)) {
      setLanguages(res);
    } else if (res.languages && Array.isArray(res.languages)) {
      setLanguages(res.languages);
    } else {
      console.warn('Unexpected response format:', res);
      setLanguages([]);
    }
  } catch (error) {
    console.error('Error loading languages:', error);
  }
};


  const handleAdd = async (values, { resetForm }) => {
    console.log(values);
    const payload = {
      name: values.languagename,
      code: values.languagecode
    };

    try {
      await languageService.addLanguage(payload, tenantId);
      resetForm();
      loadLanguages();
    } catch (error) {
      console.error('❌ Error creating language:', error);
    }
  };

  const handleDelete = async (id) => {
    try {
      await languageService.deleteLanguage(id, tenantId);
      loadLanguages();
    } catch (error) {
      console.error('❌ Error deleting language:', error);
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
            'announcements', 'city', 'languages', 'trending', 'policy', 'gate','terminal'
          ].map((item) => (
            <div className="airport-nav-row" key={item}>
              <a href={`/admin/${item}`}>{item.toUpperCase()}</a>
            </div>
          ))}
        </nav>
      </aside>

      <main className="airport-main-content" style={{
        backgroundImage: "url('/AdminLanguagesImage.jpg')",
        backgroundRepeat: 'no-repeat',
        backgroundSize: 'cover',
        backgroundPosition: 'center',
        width: '100%',
        height: 'auto'
      }}>
        <header className="airport-header">
          <h2>LANGUAGES</h2>
          <div className="airport-admin-title">ADMIN</div>
        </header>

        <Formik
          initialValues={{ languagename: '', languagecode: '' }}
          validationSchema={Yup.object({
            languagename: Yup.string().required('Required'),
            languagecode: Yup.string().required('Required'),
          })}
          onSubmit={handleAdd}
        >
          <Form className="admin-city-country-form">
            <div className="admin-city-country-row">
              <div className="admin-city-country-input-group">
                <Field
                  type="text"
                  name="languagename"
                  placeholder="Language Name"
                  className="admin-city-country-input"
                />
                <ErrorMessage name="languagename" component="div" className="admin-city-country-error" />
              </div>
              <div className="admin-city-country-input-group">
                <Field
                  type="text"
                  name="languagecode"
                  placeholder="Language Code"
                  className="admin-city-country-input"
                />
                <ErrorMessage name="languagecode" component="div" className="admin-city-country-error" />
              </div>
              <button type="submit" className="admin-city-country-add-btn">ADD</button>
            </div>
          </Form>
        </Formik>

        <div className="airport-table">
          <div className="airport-table-header" style={{ display: 'flex', width: '100%' }}>
            <span style={{ width: '33%' }}>Language Name</span>
            <span style={{ width: '33%' }}>Language Code</span>
            <span style={{ width: '33%' }}>Actions</span>
          </div>

          {Array.isArray(languages) && languages.map((lang) => (
            <div className="airport-table-row" key={lang.id} style={{ display: 'flex', width: '100%' }}>
              <span style={{ width: '33%' }}>{lang.name}</span>
              <span style={{ width: '33%' }}>{lang.code}</span>
              <span style={{ width: '33%' }}>
                <button className="airport-delete-btn" onClick={() => handleDelete(lang.id)}>🗑</button>
              </span>
            </div>
          ))}
        </div>
      </main>
    </div>
  );
}
