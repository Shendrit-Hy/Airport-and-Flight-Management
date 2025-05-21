import React, { useEffect, useState } from "react";
import axios from "axios";
import { getTenantIdFromSubdomain } from "../utils/getTenantId";
import "../styles/AdminBooking.css"; // Reuse your admin CSS
import "../styles/AdminAirportPage.css"


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

      <main
        className="adminpayments-content"
        style={{
          backgroundImage: "url('/AdminAnnouncements.png')",
          backgroundPosition: "center",
          backgroundSize: "cover",
          backgroundRepeat: "no-repeat"
        }}
>
        <header className="adminbooking-header">
          <h2>ANNOUNCEMENTS</h2>
          <div className="adminbooking-title">ADMIN</div>
        </header>

        {/* Add Announcement Form */}
        <div className="adminpayments-form">
          <input
            name="title"
            placeholder="Title"
            value={form.title}
            onChange={handleInputChange}
            style={{
              marginLeft: 20,
              height: 30,
              borderRadius: 10,
              border: 0,
              backgroundColor: "rgb(27, 27, 27)",
              color: "white",
              padding:10
            }}

          />
          <textarea
            name="message"
            placeholder="Message"
            value={form.message}
            onChange={handleInputChange}
            rows={3}
            style={{ resize: "none", width: "96%", marginTop: 8,marginLeft: 20,borderRadius:10, border: 0, backgroundColor: "rgb(27, 27, 27)",color:"white",padding:10  }}
          />
          <button className="adminpayments-add-btn" onClick={handleAddAnnouncement} 
            style={{padding:10, marginLeft: 20,marginBottom: 20, borderRadius:10, border: 0, backgroundColor: "rgb(0,13,255)", height:30, color:"white", backgroundColor:"blue" }}> 
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
