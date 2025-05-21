import React, { useContext } from 'react';
import { Formik, Form, Field, ErrorMessage } from 'formik';
import * as Yup from 'yup';
import { AuthContext } from '../context/AuthContext';
import '../styles/SupportPage.css';
import { sendSupportRequest } from '../api/supportService';
import { getTenantIdFromSubdomain } from '../utils/getTenantId';

const SupportPage = () => {
  const { user } = useContext(AuthContext);

  const validationSchema = Yup.object().shape({
    type: Yup.string().required(),
    subject: Yup.string().required('Subjekti është i detyrueshëm'),
    message: Yup.string().required('Mesazhi është i detyrueshëm'),
    email: Yup.string().email('Email i pavlefshëm').required('Email-i është i detyrueshëm'),
    flightNumber: Yup.string().when('type', {
      is: (type) => type === 'Baggage' || type === 'Lost Item',
      then: (schema) => schema.required('Numri i fluturimit është i detyrueshëm për këtë kategori'),
      otherwise: (schema) => schema.notRequired()
    })
  });

  const handleSubmit = async (values, { resetForm }) => {
    const tenantId = getTenantIdFromSubdomain();
    try {
      await sendSupportRequest(values, tenantId);
      alert('Kërkesa u dërgua me sukses!');
      resetForm();
    } catch (error) {
      if (error.response && error.response.status === 404) {
        alert("Ky fluturim nuk ekziston.");
      } else {
        alert("Ndodhi një gabim. Ju lutem provoni përsëri.");
      }
    }
  };

  return (
    <div className="supportpage-wrapper">
      <div className="supportpage-container">
        <h2>Qendra e Mbështetjes</h2>

        <section className="supportpage-form">
          <h3 className="supportpage-title">Dërgo një Kërkesë</h3>
          <Formik
            initialValues={{
              type: 'General',
              subject: '',
              message: '',
              email: user?.email || '',
              flightNumber: '',
            }}
            validationSchema={validationSchema}
            onSubmit={handleSubmit}
          >
            {({ values }) => (
              <Form>
                <Field as="select" name="type" className="supportpage-input">
                  <option value="General">General</option>
                  <option value="Feedback">Feedback</option>
                  <option value="Lost Item">Lost Item</option>
                  <option value="Baggage">Baggage</option>
                  <option value="Immigration">Immigration</option>
                </Field>

                <Field name="subject" placeholder="Subjekti" className="supportpage-input" />
                <ErrorMessage name="subject" component="div" className="supportpage-error" />

                <Field as="textarea" name="message" placeholder="Mesazhi yt" className="supportpage-textarea" />
                <ErrorMessage name="message" component="div" className="supportpage-error" />

                <Field name="email" placeholder="Email-i yt" className="supportpage-input" />
                <ErrorMessage name="email" component="div" className="supportpage-error" />

                <Field name="flightNumber" placeholder="Numri i fluturimit" className="supportpage-input" />
                <ErrorMessage name="flightNumber" component="div" className="supportpage-error" />

                <button type="submit" className="supportpage-submit">Dërgo</button>
              </Form>
            )}
          </Formik>
        </section>
      </div>
    </div>
  );
};

export default SupportPage;
