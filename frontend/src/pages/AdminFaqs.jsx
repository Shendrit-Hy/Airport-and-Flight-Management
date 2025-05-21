import React, { useEffect, useState } from "react";
import axios from "axios";
import { getTenantIdFromSubdomain } from "../utils/getTenantId";
import "../styles/AdminBooking.css";
import "../styles/AdminAirportPage.css"

function AdminFaqs() {
  const [faqs, setFaqs] = useState([]);
  const [form, setForm] = useState({ question: "", answer: "" });

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

  const handleInputChange = (e) => {
    setForm({ ...form, [e.target.name]: e.target.value });
  };

  const handleAddFaq = () => {
    if (!form.question || !form.answer) {
      alert("Please fill in both question and answer.");
      return;
    }

    axios
      .post(
        "http://localhost:8080/api/faqs",
        { ...form },
        { headers: { "X-Tenant-ID": tenantId } }
      )
      .then((res) => {
        setFaqs((prev) => (Array.isArray(prev) ? [...prev, res.data] : [res.data]));
        setForm({ question: "", answer: "" });
      })
      .catch((err) => console.error("Error adding FAQ:", err));
  };

  return (
    <div className="adminbooking-container">
      <aside className="airport-sidebar">
        <div className="airport-logo">MBI RE</div>
        <nav className="airport-nav-group">
          <div className="airport-nav-row">
            <a href="/admin/dashboard">DASHBOARD</a>
          </div>
          <div className="airport-nav-row">
            <a href="/admin/airport">AIRPORT</a>
          </div>
          <div className="airport-nav-row">
            <a href="/admin/booking">BOOKING</a>
          </div>
          <div className="airport-nav-row">
            <a href="/admin/faqs">FAQ'S</a>
          </div>
          <div className="airport-nav-row">
            <a href="/admin/flightspage">FLIGHTS</a>
          </div>
          <div className="airport-nav-row">
            <a href="/admin/maintenance">MAINTENANCE</a>
          </div>
          <div className="airport-nav-row">
            <a href="/admin/passangers">PASSANGERS</a>
          </div>
          <div className="airport-nav-row">
            <a href="/admin/payments">PAYMENTS</a>
          </div>
          <div className="airport-nav-row">
            <a href="/admin/staff">STAFF</a>
          </div>
          <div className="airport-nav-row">
            <a href="/admin/support">SUPPORT</a>
          </div>
          <div className="airport-nav-row">
            <a href="/admin/announcements">ANNOUNCEMENTS</a>
          </div>
        </nav>
      </aside>

      <main className="adminbooking-content">
        <header className="adminbooking-header">
          <h2>FAQS</h2>
          <div className="adminbooking-title">ADMIN</div>
        </header>

        <div style={{ maxWidth: 600, margin: "0 20px 30px 20px" }}>
          <input
            type="text"
            name="question"
            placeholder="Question"
            value={form.question}
            onChange={handleInputChange}
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
          <textarea
            name="answer"
            placeholder="Answer"
            value={form.answer}
            onChange={handleInputChange}
            rows={4}
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
          <button
            onClick={handleAddFaq}
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
