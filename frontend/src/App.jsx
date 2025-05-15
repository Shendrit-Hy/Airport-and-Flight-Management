import React from 'react';
import { BrowserRouter, Routes, Route } from 'react-router-dom';
import TicketsPage from './pages/TicketsPage';
import FlightsPage from './pages/FlightsPage';
import RoutesPage from './pages/RoutesPage';
import SupportPage from './pages/SupportPage';
import LoginPage from './pages/LoginPage';
import RegisterPage from './pages/RegisterPage';
import { AuthProvider } from './context/AuthContext';
import UserProfile from './pages/UserProfile';
import CheckInPage from './pages/CheckInPage';
import BookingPage from './pages/BookingPage';
import AdminDashboard from './components/DashboardLayout';
import AdminLogin from './pages/AdminLoginPage';
import AdminRoute from './components/AdminRoute';


function App() {
  return (
    <BrowserRouter>
        <AuthProvider>
      <Routes>
        <Route path="/login" element={<LoginPage />} />
        <Route path="/booking" element={<BookingPage />} />
        <Route path="/signup" element={<RegisterPage />} />
        <Route path="/profile" element={<UserProfile />} />
        <Route path="/checkin" element={<CheckInPage />} />
        <Route path="/tickets" element={<TicketsPage />} />
        <Route path="/flights" element={<FlightsPage />} />
        <Route path="/routes" element={<RoutesPage />} />
        <Route path="/support" element={<SupportPage />} />
        <Route path="/admin" element={<AdminLogin />} />
        <Route path="/admin/dashboard" element={
            <AdminRoute>
              <AdminDashboard />
            </AdminRoute>
          }
        />
      </Routes>
      </AuthProvider>
    </BrowserRouter>
  );
}

export default App;
