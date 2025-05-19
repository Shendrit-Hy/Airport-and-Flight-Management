import React, { useEffect, useState } from "react";
import axios from "axios";
import { getTenantIdFromSubdomain } from "../utils/getTenantId";
import "../styles/AdminBooking.css";

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
      <aside className="adminbooking-sidebar">
        <img src="/logo.png" alt="Logo" className="adminbooking-logo" />
        <button className="adminbooking-side-button">DASHBOARD</button>
        <button className="adminbooking-side-button">SEARCH</button>
        <button className="adminbooking-side-button">FAQS</button>
      </aside>

      <main className="adminbooking-content">
        <div className="adminbooking-ribbon">
          <span className="adminbooking-label">ADMIN</span>
        </div>

        <h2 className="adminbooking-title">FAQ Management</h2>

        <div style={{ maxWidth: 600, margin: "0 20px 30px 20px" }}>
          <input
            type="text"
            name="question"
            placeholder="Question"
            value={form.question}
            onChange={handleInputChange}
            style={{
              backgroundColor: "#1d1d1d",
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
              backgroundColor: "#1d1d1d",
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
              backgroundColor: "#333",
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
