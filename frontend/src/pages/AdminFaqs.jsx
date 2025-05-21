import React, { useEffect, useState } from "react";
import { Formik, Form, Field, ErrorMessage } from "formik";
import * as Yup from "yup";
import axios from "axios";
import { getTenantIdFromSubdomain } from "../utils/getTenantId";
import "../styles/AdminBooking.css";
import "../styles/AdminAirportPage.css";

function AdminFaqs() {
  const [faqs, setFaqs] = useState([]);
  const tenantId = getTenantIdFromSubdomain();

  useEffect(() => {
    fetchFaqs();
  }, []);

  const fetchFaqs = () => {
    axios
      .get("http://localhost:8080/api/faqs", {
        headers: { "X-Tenant-ID": tenantId },
      })
      .then((res) => {
        setFaqs(Array.isArray(res.data) ? res.data : []);
      })
      .catch((err) => console.error("Error fetching FAQs:", err));
  };

  const handleAddFaq = (values, { resetForm }) => {
    axios
      .post(
        "http://localhost:8080/api/faqs",
        { ...values },
        { headers: { "X-Tenant-ID": tenantId } }
      )
      .then((res) => {
        setFaqs((prev) => (Array.isArray(prev) ? [...prev, res.data] : [res.data]));
        resetForm();
      })
      .catch((err) => console.error("Error adding FAQ:", err));
  };

  return (
    <div className="adminbooking-container">
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

      <main className="adminbooking-content">
        <header className="adminbooking-header">
          <h2>FAQS</h2>
          <div className="adminbooking-title">ADMIN</div>
        </header>

        <div style={{ maxWidth: 600, margin: "0 20px 30px 20px" }}>
          <Formik
            initialValues={{ question: "", answer: "" }}
            validationSchema={Yup.object({
              question: Yup.string().required("Question is required"),
              answer: Yup.string().required("Answer is required")
            })}
            onSubmit={handleAddFaq}
          >
            <Form>
              <Field
                name="question"
                placeholder="Question"
                className="airport-input"
                style={{
                  backgroundColor: "rgb(27,27,27)",
                  border: "none",
                  borderRadius: 8,
                  padding: 12,
                  marginBottom: 15,
                  width: "100%",
                  color: "white",
                  fontSize: "1rem",
                }}
              />
              <ErrorMessage name="question" component="div" className="airport-error" />

              <Field
                name="answer"
                as="textarea"
                placeholder="Answer"
                rows={4}
                className="airport-input"
                style={{
                  backgroundColor: "rgb(27,27,27)",
                  border: "none",
                  borderRadius: 8,
                  padding: 12,
                  marginBottom: 15,
                  width: "100%",
                  color: "white",
                  fontSize: "1rem",
                  resize: "vertical",
                }}
              />
              <ErrorMessage name="answer" component="div" className="airport-error" />

              <button
                type="submit"
                style={{
                  backgroundColor: "rgb(0,13,255)",
                  color: "white",
                  border: "none",
                  padding: "12px 20px",
                  fontSize: "1rem",
                  borderRadius: 8,
                  cursor: "pointer",
                  transition: "background-color 0.3s",
                  width: 140,
                }}
                onMouseOver={(e) => (e.target.style.backgroundColor = "#444")}
                onMouseOut={(e) => (e.target.style.backgroundColor = "#333")}
              >
                Add FAQ
              </button>
            </Form>
          </Formik>
        </div>

        <div className="adminbooking-table-container">
          <table className="adminbooking-table">
            <thead>
              <tr>
                <th>Question</th>
                <th>Answer</th>
              </tr>
            </thead>
            <tbody>
              {faqs.length === 0 ? (
                <tr>
                  <td colSpan="2" style={{ color: "#ccc", textAlign: "center" }}>
                    No FAQs found.
                  </td>
                </tr>
              ) : (
                faqs.map((faq) => (
                  <tr key={faq.id}>
                    <td style={{ whiteSpace: "pre-wrap" }}>{faq.question}</td>
                    <td style={{ whiteSpace: "pre-wrap" }}>{faq.answer}</td>
                  </tr>
                ))
              )}
            </tbody>
          </table>
        </div>
      </main>
    </div>
  );
}

export default AdminFaqs;
