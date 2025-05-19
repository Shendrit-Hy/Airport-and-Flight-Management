import React, { useEffect, useState } from "react";
import axios from "axios";
import { getTenantIdFromSubdomain } from "../utils/getTenantId";
import "../styles/UserAnnouncements.css";

function UserAnnouncements() {
  const [announcements, setAnnouncements] = useState([]);
  const tenantId = getTenantIdFromSubdomain();

  useEffect(() => {
    axios
      .get("http://localhost:8080/api/announcements", {
        headers: { "X-Tenant-ID": tenantId },
      })
      .then((res) => setAnnouncements(res.data))
      .catch((err) => console.error("Error fetching announcements:", err));
  }, [tenantId]);

  return (
    <div className="announcements-container">
      <h2 className="announcements-title">📢 Announcements</h2>
      {announcements.length === 0 ? (
        <p style={{ textAlign: "center", color: "#ccc" }}>
          No announcements found.
        </p>
      ) : (
        <div className="announcements-grid">
          {announcements.map((a, i) => (
            <div key={i} className="announcement-card">
              <h3 className="announcement-title">{a.title}</h3>
              <p className="announcement-message">{a.message}</p>
            </div>
          ))}
        </div>
      )}
    </div>
  );
}

export default UserAnnouncements;
