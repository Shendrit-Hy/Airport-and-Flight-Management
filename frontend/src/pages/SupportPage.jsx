import React, { useContext } from 'react';
import { Formik, Form, Field, ErrorMessage } from 'formik';
import * as Yup from 'yup';
import { AuthContext } from '../context/AuthContext';
import '../styles/SupportPage.css';
import { sendSupportRequest } from '../api/supportService';
import { getTenantIdFromSubdomain } from '../utils/getTenantId';
import { useLanguage } from '../context/LanguageContext'; // ✅ importo përkthimin

const SupportPage = () => {
  const { user } = useContext(AuthContext);
  const { t } = useLanguage(); // ✅ përdor për përkthim


  const validate = (values) => {
    const errors = {};
    if (!values.subject) errors.subject = 'Subjekti është i detyrueshëm';
    if (!values.message) errors.message = 'Mesazhi është i detyrueshëm';
    if (!values.email) errors.email = 'Email-i është i detyrueshëm';
    if (
      (values.type === 'Baggage' || values.type === 'Lost Item') &&
      !values.flightNumber
    ) {
      errors.flightNumber = 'Numri i fluturimit është i detyrueshëm për këtë kategori';
    }
    return errors;
  };
  const validationSchema = Yup.object().shape({
    type: Yup.string().required(),
    subject: Yup.string().required(t('Subject is required', 'Subjekti është i detyrueshëm')),
    message: Yup.string().required(t('Message is required', 'Mesazhi është i detyrueshëm')),
    email: Yup.string().email(t('Invalid email', 'Email i pavlefshëm')).required(t('Email is required', 'Email-i është i detyrueshëm')),
    flightNumber: Yup.string().when('type', {
      is: (type) => type === 'Baggage' || type === 'Lost Item',
      then: (schema) => schema.required(t('Flight number is required for this type', 'Numri i fluturimit është i detyrueshëm për këtë kategori')),
      otherwise: (schema) => schema.notRequired()
    })
  });


  const handleSubmit = async (values, { resetForm }) => {
    const tenantId = getTenantIdFromSubdomain();
    try {
      await sendSupportRequest(values, tenantId);
      alert(t('Request sent successfully!', 'Kërkesa u dërgua me sukses!'));
      resetForm();
    } catch (error) {
      if (error.response && error.response.status === 404) {
        alert(t("This flight does not exist.", "Ky fluturim nuk ekziston."));
      } else {
        alert(t("An error occurred. Please try again.", "Ndodhi një gabim. Ju lutem provoni përsëri."));
      }
    }
  };

  return (
    <div className="supportpage-wrapper">
      <div className="supportpage-container">
        <h2>{t("Support Center", "Qendra e Mbështetjes")}</h2>

        <section className="supportpage-form">
          <h3 className="supportpage-title">{t("Submit a Request", "Dërgo një Kërkesë")}</h3>
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
                  <option value="General">{t("General", "Të përgjithshme")}</option>
                  <option value="Feedback">{t("Feedback", "Komente")}</option>
                  <option value="Lost Item">{t("Lost Item", "Send i humbur")}</option>
                  <option value="Baggage">{t("Baggage", "Bagazh")}</option>
                  <option value="Immigration">{t("Immigration", "Imigrim")}</option>
                </Field>

                <Field name="subject" placeholder={t("Subject", "Subjekti")} className="supportpage-input" />
                <ErrorMessage name="subject" component="div" className="supportpage-error" />

                <Field as="textarea" name="message" placeholder={t("Your message", "Mesazhi yt")} className="supportpage-textarea" />
                <ErrorMessage name="message" component="div" className="supportpage-error" />

                <Field name="email" placeholder={t("Your email", "Email-i yt")} className="supportpage-input" />
                <ErrorMessage name="email" component="div" className="supportpage-error" />

                <Field name="flightNumber" placeholder={t("Flight number", "Numri i fluturimit")} className="supportpage-input" />
                <ErrorMessage name="flightNumber" component="div" className="supportpage-error" />

                <button type="submit" className="supportpage-submit">{t("Submit", "Dërgo")}</button>
              </Form>
            )}
          </Formik>
        </section>
      </div>
    </div>
  );
};

export default SupportPage;