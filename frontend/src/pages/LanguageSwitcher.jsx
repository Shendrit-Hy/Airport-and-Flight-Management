import React, { useEffect, useState } from 'react';
import axios from '../utils/axiosInstance';
import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { useLanguage } from '../context/LanguageContext'; // kjo lidhet me LanguageProvider

const LanguageSwitcher = () => {
  const [languages, setLanguages] = useState([]);
  const { language, setLanguage } = useLanguage();

  useEffect(() => {
    axios.get('/api/languages')
      .then(res => {
        setLanguages(res.data);
      })
      .catch(err => {
        console.error("Failed to fetch languages:", err);
        // Opsionale: fallback në rast se nuk ka API
        setLanguages([
          { code: 'en', name: 'English' },
          { code: 'sq', name: 'Shqip' }
        ]);
      });
  }, []);

  const handleChange = (e) => {
    const lang = e.target.value;
    setLanguage(lang); // përditëson context-in dhe localStorage përmes useEffect-it të LanguageProvider-it
  };

  return (
    <select value={language} onChange={handleChange}>
      {languages.map(lang => (
        <option key={lang.code} value={lang.code}>
          {lang.name}
        </option>
      ))}
    </select>
  );
};

export default LanguageSwitcher;
