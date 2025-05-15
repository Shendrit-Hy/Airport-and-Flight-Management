import React from 'react';
import { Link } from 'react-router-dom';
import '../styles/Navbar.css';
import { FaUser } from 'react-icons/fa';

const Navbar = () => {
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
      </div>
    </nav>
  );
};

export default Navbar;
