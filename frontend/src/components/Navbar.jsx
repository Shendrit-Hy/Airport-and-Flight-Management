import React, { useState } from 'react';
import { Link } from 'react-router-dom';
import '../styles/Navbar.css';
import { FaUser } from 'react-icons/fa';

const Navbar = () => {
  const [selectedLanguage, setSelectedLanguage] = useState(localStorage.getItem('language') || 'en');

  const toggleLanguage = () => {
    const newLang = selectedLanguage === 'en' ? 'sq' : 'en';
    setSelectedLanguage(newLang);
    localStorage.setItem('language', newLang);
    window.location.reload();
  };

  return (
    <nav className="navbar">
      <div className="navbar-left">
        <Link to="/" className="navbar-logo">Logo</Link>
      </div>
      <div className="navbar-right">
        <Link to="/flights" className="navbar-link">Flights</Link>
        <Link to="/help" className="navbar-link">Help</Link>
        <Link to="/login" className="navbar-link">Log In</Link>
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
