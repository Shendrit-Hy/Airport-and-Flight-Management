import React, { useEffect, useState } from "react";
import axios from "axios";
import { getTenantIdFromSubdomain } from "../utils/getTenantId";
import "../styles/UserAnnouncements.css";
import { useLanguage } from "../context/LanguageContext"; // ✅

function UserAnnouncements() {
  const [announcements, setAnnouncements] = useState([]);
  const [faqs, setFaqs] = useState([]);
  const tenantId = getTenantIdFromSubdomain();
  const { t } = useLanguage(); // ✅

  useEffect(() => {
    axios.get("http://localhost:8080/api/announcements", {
      headers: { "X-Tenant-ID": tenantId },
    })
      .then((res) => setAnnouncements(res.data))
      .catch((err) => console.error("Error fetching announcements:", err));

    axios.get("http://localhost:8080/api/faqs", {
      headers: { "X-Tenant-ID": tenantId },
    })
      .then((res) => setFaqs(res.data))
      .catch((err) => console.error("Error fetching FAQs:", err));
  }, [tenantId]);

  return (
    <div className="announcements-container" style={{ backgroundColor: "#525252", padding: 20, minHeight: "100vh" }}>
      {/* Announcements Section */}
      <h2 className="announcements-title" style={{ color: "white", marginBottom: 15 }}>
        📢 {t("Announcements", "Njoftime")}
      </h2>
      {announcements.length === 0 ? (
        <p style={{ textAlign: "center", color: "#ccc" }}>
          {t("No announcements found.", "Asnjë njoftim nuk u gjet.")}
        </p>
      ) : (
        <div className="announcements-grid">
          {announcements.map((a, i) => (
            <div key={i} className="announcement-card" style={{ backgroundColor: "#3b3b3b", color: "white", padding: 15, borderRadius: 8, marginBottom: 12, wordWrap: "break-word" }}>
              <h3 className="announcement-title" style={{ marginBottom: 8 }}>{a.title}</h3>
              <p className="announcement-message">{a.message}</p>
            </div>
          ))}
        </div>
      )}

      {/* Separator */}
      <hr style={{ borderColor: "#777", margin: "40px 0" }} />

      {/* FAQs Section */}
      <h2 className="announcements-title" style={{ color: "white", marginBottom: 15 }}>
        ❓ {t("Frequently Asked Questions", "Pyetjet e Bëra Shpesh")}
      </h2>
      {faqs.length === 0 ? (
        <p style={{ textAlign: "center", color: "#ccc" }}>
          {t("No FAQs found.", "Asnjë pyetje nuk u gjet.")}
        </p>
      ) : (
        <div className="faqs-grid" style={{ display: "flex", flexDirection: "column", gap: 12 }}>
          {faqs.map((faq, i) => (
            <div key={i} className="faq-card" style={{ backgroundColor: "#3b3b3b", color: "white", padding: 15, borderRadius: 8, wordWrap: "break-word" }}>
              <h3 style={{ marginBottom: 8 }}>{faq.question}</h3>
              <p>{faq.answer}</p>
            </div>
          ))}
        </div>
      )}
    </div>
  );
}

export default UserAnnouncements;
