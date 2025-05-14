import React from 'react';
import { BrowserRouter, Routes, Route } from 'react-router-dom';
import DashboardLayout from './components/DashboardLayout';
import TicketsPage from './pages/TicketsPage';
import FlightsPage from './pages/FlightsPage';
import RoutesPage from './pages/RoutesPage';
import SupportPage from './pages/SupportPage';
import LoginPage from './pages/LoginPage';
import RegisterPage from './pages/RegisterPage';

function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<LoginPage />} />
        <Route path="/signup" element={<RegisterPage />} />
        <Route path="/dashboard" element={<DashboardLayout />} />
        <Route path="/tickets" element={<TicketsPage />} />
        <Route path="/flights" element={<FlightsPage />} />
        <Route path="/routes" element={<RoutesPage />} />
        <Route path="/support" element={<SupportPage />} />
      </Routes>
    </BrowserRouter>
  );
}

export default App;
