import React, { useEffect, useState } from "react";
import axios from "axios";
import { getTenantIdFromSubdomain } from "../utils/getTenantId";
import "../styles/AdminBooking.css"; // Reuse your admin CSS

function AdminAnnouncements() {
  const [announcements, setAnnouncements] = useState([]);
  const [form, setForm] = useState({
    title: "",
    message: "",
  });

  const tenantId = getTenantIdFromSubdomain();

  useEffect(() => {
    fetchAnnouncements();
  }, []);

  const fetchAnnouncements = () => {
    console.log("Fetching announcements for tenant:", tenantId);
    axios
      .get("http://localhost:8080/api/announcements", {
        headers: { "X-Tenant-ID": tenantId },
      })
      .then((res) => {
        const data = Array.isArray(res.data) ? res.data : res.data.announcements;
        setAnnouncements(data);
      })
      .catch((err) => console.error("Error fetching announcements:", err));
  };

  const handleAddAnnouncement = () => {
    if (!form.title || !form.message) {
      alert("Please fill in both title and message.");
      return;
    }

    axios
      .post("http://localhost:8080/api/announcements", form, {
        headers: { "X-Tenant-ID": tenantId },
      })
      .then((res) => {
        setAnnouncements((prev) => [...prev, res.data]);
        setForm({ title: "", message: "" });
      })
      .catch((err) => console.error("Error adding announcement:", err));
  };

  const handleInputChange = (e) => {
    setForm({ ...form, [e.target.name]: e.target.value });
  };

  return (
    <div className="adminpayments-container">
      <aside className="adminpayments-sidebar">
        <img src="/logo.png" alt="Logo" className="adminpayments-logo" />
        <button className="adminpayments-side-button">DASHBOARD</button>
        <button className="adminpayments-side-button">SEARCH</button>
        <button className="adminpayments-side-button">ANNOUNCEMENTS</button>
      </aside>

      <main className="adminpayments-content">
        <div className="adminpayments-ribbon">
          <span className="adminpayments-label">ADMIN</span>
        </div>

        <h2 className="adminpayments-title">ANNOUNCEMENTS</h2>

        {/* Add Announcement Form */}
        <div className="adminpayments-form">
          <input
            name="title"
            placeholder="Title"
            value={form.title}
            onChange={handleInputChange}
          />
          <textarea
            name="message"
            placeholder="Message"
            value={form.message}
            onChange={handleInputChange}
            rows={3}
            style={{ resize: "none", width: "100%", marginTop: 8 }}
          />
          <button className="adminpayments-add-btn" onClick={handleAddAnnouncement}>
            Add Announcement
          </button>
        </div>

        {/* Announcements Table */}
        <div className="adminpayments-table-container">
          <table className="adminpayments-table">
            <thead>
              <tr>
                <th>Title</th>
                <th>Message</th>
                <th>Created At</th>
              </tr>
            </thead>
            <tbody>
              {Array.isArray(announcements) && announcements.length > 0 ? (
                announcements.map((a, i) => (
                  <tr key={a.id || i}>
                    <td>{a.title}</td>
                    <td>{a.message}</td>
                    <td>{a.createdAt?.replace("T", " ").slice(0, 19)}</td>
                  </tr>
                ))
              ) : (
                <tr>
                  <td colSpan="3">No announcements found.</td>
                </tr>
              )}
            </tbody>
          </table>
        </div>
      </main>
    </div>
  );
}

export default AdminAnnouncements;
