import React, { useEffect, useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import '../styles/Navbar.css';
import { FaUser } from 'react-icons/fa';
import { useLanguage } from '../context/LanguageContext';

const Navbar = () => {
  const [selectedLanguage, setSelectedLanguage] = useState(localStorage.getItem('language') || 'en');
  const [isLoggedIn, setIsLoggedIn] = useState(false);
  const [username, setUsername] = useState('');
  const navigate = useNavigate();

  const { t } = useLanguage();

  const parseJwt = (token) => {
    try {
      const base64Url = token.split('.')[1];
      const base64 = base64Url.replace(/-/g, '+').replace(/_/g, '/');
      const jsonPayload = decodeURIComponent(
        atob(base64)
          .split('')
          .map((c) => '%' + ('00' + c.charCodeAt(0).toString(16)).slice(-2))
          .join('')
      );
      return JSON.parse(jsonPayload);
    } catch (e) {
      return null;
    }
  };

  useEffect(() => {
    const token = localStorage.getItem('token');
    if (token) {
      const decoded = parseJwt(token);
      if (decoded?.sub) {
        setIsLoggedIn(true);
        setUsername(decoded.sub);
      }
    }
  }, []);

  const handleLogout = () => {
    localStorage.removeItem('token');
    setIsLoggedIn(false);
    setUsername('');
    navigate('/login'); // redirect to login page
  };

  const toggleLanguage = () => {
    const newLang = selectedLanguage === 'en' ? 'sq' : 'en';
    setSelectedLanguage(newLang);
    localStorage.setItem('language', newLang);
    window.location.reload();
  };

  return (
    <nav className="navbar">
      <div className="navbar-left">
        <Link to="/home" className="navbar-logo">MBI RE</Link>
      </div>
      <div className="navbar-right">
        <Link to="/flights" className="navbar-link">{t("Flights", "Fluturimet")}</Link>
        <Link to="/help" className="navbar-link">{t("Help", "Ndihmë")}</Link>
        <Link to="/announcements" className="navbar-link">{t("Announcements", "Lajmërimet")}</Link>

        {!isLoggedIn ? (
          <Link to="/login" className="navbar-link">{t("Log In", "Kyçu")}</Link>
        ) : (
          <>
            <span className="navbar-link">{username}</span>
            <button className="navbar-link logout-btn" onClick={handleLogout}>
              {t("Logout", "Dil")}
            </button>
          </>
        )}

        <Link to="/profile" className="profile-icon">
          <FaUser />
        </Link>
        <button className="lang-toggle-btn" onClick={toggleLanguage}>
          {selectedLanguage.toUpperCase()}
        </button>
      </div>
    </nav>
  );
};

export default Navbar;
