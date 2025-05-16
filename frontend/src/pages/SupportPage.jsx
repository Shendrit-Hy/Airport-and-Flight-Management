import React, { useContext } from 'react';
import { Formik, Form, Field, ErrorMessage } from 'formik';
import { AuthContext } from '../context/AuthContext';
import '../styles/SupportPage.css';
import { sendSupportRequest } from '../api/supportService';
import { getTenantIdFromSubdomain } from '../utils/getTenantId';

const SupportPage = () => {
  const { user } = useContext(AuthContext);

  const validate = (values) => {
    const errors = {};
    if (!values.subject) {
      errors.subject = 'Subjekti është i detyrueshëm';
    }
    if (!values.message) {
      errors.message = 'Mesazhi është i detyrueshëm';
    }
    if (!values.email) {
      errors.email = 'Email-i është i detyrueshëm';
    }
    if (
      (values.type === 'Baggage' || values.type === 'Lost Item') &&
      !values.flightNumber
    ) {
      errors.flightNumber = 'Numri i fluturimit është i detyrueshëm për këtë kategori';
    }
    return errors;
  };

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
    <div className="support-wrapper">
      <div className="support-page">
        <h2>Qendra e Mbështetjes</h2>

        <section className="support-form">
          <h3 className="titulli">Dërgo një Kërkesë</h3>
          <Formik
            initialValues={{
              type: 'General',
              subject: '',
              message: '',
              email: user?.email || '',
              flightNumber: '',
            }}
            validate={validate}
            onSubmit={handleSubmit}
          >
            {({ values }) => (
              <Form>
                <Field as="select" name="type" className="input">
                  <option value="General">General</option>
                  <option value="Feedback">Feedback</option>
                  <option value="Lost Item">Lost Item</option>
                  <option value="Baggage">Baggage</option>
                  <option value="Immigration">Immigration</option>
                </Field>

                <Field name="subject" placeholder="Subjekti" className="input" />
                <ErrorMessage name="subject" component="div" className="error" />

                <Field as="textarea" name="message" placeholder="Mesazhi yt" className="textarea" />
                <ErrorMessage name="message" component="div" className="error" />

                <Field name="email" placeholder="Email-i yt" className="input" />
                <ErrorMessage name="email" component="div" className="error" />

                <Field name="flightNumber" placeholder="Numri i fluturimit" className="input" />
                <ErrorMessage name="flightNumber" component="div" className="error" />

                <button type="submit" className="button">Dërgo</button>
              </Form>
            )}
          </Formik>
        </section>
      </div>
    </div>
  );
};

export default SupportPage;
