import React, { useEffect, useState } from "react";
import axios from "axios";
import { Formik, Form, Field, ErrorMessage } from "formik";
import * as Yup from "yup";
import { getTenantIdFromSubdomain } from "../utils/getTenantId";
import "../styles/AdminBooking.css";
import "../styles/AdminAirportPage.css";

function AdminAnnouncements() {
  const [announcements, setAnnouncements] = useState([]);
  const tenantId = getTenantIdFromSubdomain();

  useEffect(() => {
    fetchAnnouncements();
  }, []);

  const fetchAnnouncements = () => {
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

  const handleAddAnnouncement = async (values, { resetForm }) => {
    try {
      const res = await axios.post(
        "http://localhost:8080/api/announcements",
        values,
        {
          headers: { "X-Tenant-ID": tenantId },
        }
      );
      setAnnouncements((prev) => [...prev, res.data]);
      resetForm();
    } catch (err) {
      console.error("Error adding announcement:", err);
    }
  };

  return (
    <div className="adminpayments-container">
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

        <div className="adminpayments-form">
          <Formik
            initialValues={{ title: "", message: "" }}
            validationSchema={Yup.object({
              title: Yup.string().required("Title is required"),
              message: Yup.string().required("Message is required")
            })}
            onSubmit={handleAddAnnouncement}
          >
            <Form>
              <Field
                name="title"
                placeholder="Title"
                style={{
                  marginLeft: 20,
                  height: 30,
                  borderRadius: 10,
                  border: 0,
                  backgroundColor: "rgb(27, 27, 27)",
                  color: "white",
                  padding: 10,
                  width: "96%"
                }}
              />
              <ErrorMessage name="title" component="div" className="admin-error" />

              <Field
                name="message"
                as="textarea"
                placeholder="Message"
                rows={3}
                style={{
                  resize: "none",
                  width: "96%",
                  marginTop: 8,
                  marginLeft: 20,
                  borderRadius: 10,
                  border: 0,
                  backgroundColor: "rgb(27, 27, 27)",
                  color: "white",
                  padding: 10
                }}
              />
              <ErrorMessage name="message" component="div" className="admin-error" />

              <button
                type="submit"
                className="adminpayments-add-btn"
                style={{
                  padding: 10,
                  marginLeft: 20,
                  marginBottom: 20,
                  borderRadius: 10,
                  border: 0,
                  backgroundColor: "blue",
                  height: 30,
                  color: "white"
                }}
              >
                Add Announcement
              </button>
            </Form>
          </Formik>
        </div>

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
