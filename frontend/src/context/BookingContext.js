// src/context/BookingContext.js

import React, { createContext, useContext, useState } from 'react';

// 1. Create the context
const BookingContext = createContext();

// 2. Create a provider component
export const BookingProvider = ({ children }) => {
  const [userInfo, setUserInfo] = useState(null);
  const [selectedFlight, setSelectedFlight] = useState(null); // optional, if you also want to store flight

  return (
    <BookingContext.Provider value={{ userInfo, setUserInfo, selectedFlight, setSelectedFlight }}>
      {children}
    </BookingContext.Provider>
  );
};

// 3. Custom hook to use the context
export const useBookingContext = () => {
  const context = useContext(BookingContext);
  if (!context) {
    throw new Error('useBookingContext must be used within a BookingProvider');
  }
  return context;
};
