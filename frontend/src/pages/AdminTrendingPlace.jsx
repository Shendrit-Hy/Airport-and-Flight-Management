import React, { useEffect, useState } from 'react';
import { Formik, Form, Field, ErrorMessage } from 'formik';
import * as Yup from 'yup';
import '../styles/AdminAirportPage.css';
import trendingPlaceService from '../api/trendingPlaceService';

export default function AdminTrendingPlace() {
  const [places, setPlaces] = useState([]);
  const [imagePreview, setImagePreview] = useState('');
  const tenantId = localStorage.getItem('tenantId');

  useEffect(() => {
    loadTrendingPlaces();
  }, []);

  const loadTrendingPlaces = async () => {
    try {
      const res = await trendingPlaceService.getTrendingPlaces(tenantId);
      setPlaces(Array.isArray(res) ? res : []);
    } catch (err) {
      console.error('❌ Error loading trending places:', err);
    }
  };

const handleAdd = async (values, { resetForm }) => {
  try {
    // Convert form values to match backend field names
    const payload = {
      ...values,
      image_url: values.imageUrl, // map to correct backend field
    };

    await trendingPlaceService.createTrendingPlace(payload, tenantId);
    resetForm();
    setImagePreview('');
    loadTrendingPlaces();
  } catch (err) {
    console.error('❌ Error creating trending place:', err);
  }
};

  const handleDelete = async (id) => {
    try {
      await trendingPlaceService.deleteTrendingPlace(id, tenantId);
      loadTrendingPlaces();
    } catch (err) {
      console.error('❌ Error deleting trending place:', err);
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
        backgroundImage: "url('/AdminTrendingImage.webp')",
        backgroundRepeat: 'no-repeat',
        backgroundSize: 'cover',
        backgroundPosition: 'center',
        width: '100%',
        height: 'auto'
      }}>
        <header className="airport-header">
          <h2>TRENDING PLACE</h2>
          <div className="airport-admin-title">ADMIN</div>
        </header>

        <Formik
          initialValues={{ name: '', description: '', season: '', imageUrl: '' }}
          validationSchema={Yup.object({
            name: Yup.string().required('Required'),
            description: Yup.string().required('Required'),
            season: Yup.string()
              .oneOf(['SPRING', 'SUMMER', 'FALL', 'WINTER'], 'Invalid season')
              .required('Required'),
              imageUrl: Yup.string()
                .url('Invalid URL format')
                .required('Required'),

          })}
          onSubmit={handleAdd}
        >
          {({ setFieldValue }) => (
            <Form className="airport-add-form">
              <div className="airport-form-grid">
                <div className="airport-input-group">
                  <Field
                    type="text"
                    name="name"
                    placeholder="Name"
                    className="airport-input"
                  />
                  <ErrorMessage name="name" component="div" className="airport-error" />
                </div>

                <div className="airport-input-group">
                  <Field
                    type="text"
                    name="description"
                    placeholder="Description"
                    className="airport-input"
                  />
                  <ErrorMessage name="description" component="div" className="airport-error" />
                </div>

                <div className="airport-input-group">
                  <Field as="select" name="season" className="airport-input">
                    <option value="">Select Season</option>
                    <option value="SPRING">SPRING</option>
                    <option value="SUMMER">SUMMER</option>
                    <option value="FALL">FALL</option>
                    <option value="WINTER">WINTER</option>
                  </Field>
                  <ErrorMessage name="season" component="div" className="airport-error" />
                </div>

                <div className="airport-input-group">
                  <Field
                    type="text"
                    name="imageUrl"
                    placeholder="Image URL"
                    className="airport-input"
                    onChange={(e) => {
                      const value = e.target.value;
                      setFieldValue('imageUrl', value);
                      setImagePreview(value);
                    }}
                  />
                  <ErrorMessage name="imageUrl" component="div" className="airport-error" />
                </div>
              </div>

              {imagePreview && (
                <div className="airport-input-group">
                  <label style={{ color: '#555', marginBottom: '5px' }}>Image Preview</label>
                  <img
                    src={imagePreview}
                    alt="Preview"
                    style={{
                      maxWidth: '200px',
                      borderRadius: '8px',
                      border: '1px solid #ccc',
                      marginTop: '5px'
                    }}
                  />
                </div>
              )}

              <button type="submit" className="airport-add-btn">ADD</button>
            </Form>
          )}
        </Formik>

        <div className="airport-table">
          <div className="airport-table-header" style={{ display: 'flex', width: '100%' }}>
            <span style={{ width: '20%' }}>Name</span>
            <span style={{ width: '20%' }}>Description</span>
            <span style={{ width: '20%' }}>Season</span>
            <span style={{ width: '20%' }}>Image URL</span>
            <span style={{ width: '20%' }}>Actions</span>
          </div>

          {places.map((place) => (
            <div className="airport-table-row" key={place.id} style={{ display: 'flex', width: '100%' }}>
              <span style={{ width: '20%' }}>{place.name}</span>
              <span style={{ width: '20%' }}>{place.description}</span>
              <span style={{ width: '20%' }}>{place.season}</span>
              <span style={{ width: '20%' }}>{place.imageUrl}</span>
              <span style={{ width: '20%' }}>
                <button className="airport-delete-btn" onClick={() => handleDelete(place.id)}>🗑</button>
              </span>
            </div>
          ))}
        </div>
      </main>
    </div>
  );
}
