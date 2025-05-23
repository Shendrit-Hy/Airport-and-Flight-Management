import React, { useState } from 'react';
import { Link } from 'react-router-dom';
import '../styles/Navbar.css';
import { FaUser } from 'react-icons/fa';
import { useLanguage } from '../context/LanguageContext';

const Navbar = () => {
  const [selectedLanguage, setSelectedLanguage] = useState(localStorage.getItem('language') || 'en');

  const { t } = useLanguage();

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
        <Link to="/login" className="navbar-link">{t("Log In", "Kyçu")}</Link>
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