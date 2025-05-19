import React from 'react';
import { BrowserRouter, Routes, Route, useLocation } from 'react-router-dom';
import TicketsPage from './pages/TicketsPage';
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
import Navbar from './components/Navbar';
// import FlightsPage from './pages/FlightsPage';
import Flights from './pages/Flights';
import FilteredFlights from './pages/FilteredFlights';
import Home from './pages/Home';
import AdminStaffPage from './pages/AdminStaffPage';
import AdminBooking from './pages/AdminBooking';
import AdminFlightsPage from './pages/AdminFlightsPage';
import AdminMaintenancePage from './pages/AdminMaintenancePage';
import AdminAirportPage from './pages/AdminAirportPage';
import AdminSupportPage from './pages/AdminSupportPage';
import AdminPassangers from './pages/AdminPassangers';
import AdminPayments from './pages/AdminPayments';
import AnnouncementManager from './pages/AnnouncementManager';
import UserAnnouncements from './pages/UserAnnouncements';



function LayoutWithNavbar({ children }) {
  const location = useLocation();
  const hideNavbarPaths = [
    '/login',
    '/signup',
    '/checkin',
    '/admin'
  ];

  const shouldHideNavbar = hideNavbarPaths.some(path =>
    location.pathname === path || location.pathname.startsWith(path)
  );

  return (
    <div>
      {!shouldHideNavbar && <Navbar />}
      <div style={{ paddingTop: !shouldHideNavbar ? '60px' : 0 }}>
        {children}
      </div>
    </div>
  );
}


function App() {
  return (
    <BrowserRouter>
      <AuthProvider>
        <LayoutWithNavbar>
          <Routes>
        <Route path="/login" element={<LoginPage />} />
        <Route path="/home" element={<Home />} />
        <Route path="/booking" element={<BookingPage />} />
        <Route path="/signup" element={<RegisterPage />} />
        <Route path="/profile" element={<UserProfile />} />
        <Route path="/checkin" element={<CheckInPage />} />
        <Route path="/tickets" element={<TicketsPage />} />
{/*         <Route path="/admin/flights" element={<FlightsPage />} /> */}
        <Route path="/flights" element={<Flights />} />
        <Route path="/routes" element={<RoutesPage />} />
        <Route path="/help" element={<SupportPage />} />
        <Route path="/filteredflights" element={<FilteredFlights />} />
        <Route path="/admin" element={<AdminLogin />} />
        <Route path="/admin/staff" element={<AdminStaffPage />} />
        <Route path="/admin/booking" element={<AdminBooking />} />
        <Route path="/admin/maintenance" element={<AdminMaintenancePage />} />
        <Route path="/admin/airport" element={<AdminAirportPage />} />
         <Route path="/admin/support" element={<AdminSupportPage />} />
        <Route path="/admin/passangers" element={<AdminPassangers />} />
        <Route path="/admin/payments" element={<AdminPayments />} />
        <Route path="/admin/announcements" element={<AnnouncementManager />} />
        <Route path="/announcements" element={<UserAnnouncements />} />
        <Route
          path="/admin/flightspage"
          element={
            <AdminRoute>
              <AdminFlightsPage />
            </AdminRoute>
          }
        />
        <Route
          path="/admin/airport"
          element={
            <AdminRoute>
              <AdminAirportPage />
            </AdminRoute>
          }
        />
        <Route path="/admin/dashboard" element={<AdminRoute>
            <AdminDashboard />
               </AdminRoute>
                    }
                       />
                     </Routes>
                   </LayoutWithNavbar>
                 </AuthProvider>
               </BrowserRouter>
             );
           }
       export default App;

