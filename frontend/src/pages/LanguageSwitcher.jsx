import React, { useEffect, useState } from 'react';
import axios from '../utils/axiosInstance';

const LanguageSwitcher = ({ onLanguageChange }) => {
  const [languages, setLanguages] = useState([]);
  const [selected, setSelected] = useState(localStorage.getItem('language') || 'en');

  useEffect(() => {
    axios.get('/api/languages').then(res => setLanguages(res.data));
  }, []);

  const handleChange = (e) => {
    const lang = e.target.value;
    setSelected(lang);
    localStorage.setItem('language', lang);
    onLanguageChange(lang);
  };

  return (
    <select value={selected} onChange={handleChange}>
      {languages.map(lang => (
        <option key={lang.code} value={lang.code}>
          {lang.name}
        </option>
      ))}
    </select>
  );
};

export default LanguageSwitcher;
