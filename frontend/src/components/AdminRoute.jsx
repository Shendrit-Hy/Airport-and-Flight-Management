import { Navigate } from 'react-router-dom';

export default function AdminRoute({ children }) {
  const token = localStorage.getItem('token');

  if (!token) {
    return <Navigate to="/admin" replace />;
  }

  try {
    const payload = JSON.parse(atob(token.split('.')[1]));
    if (payload.role !== 'ADMIN') {
      return <Navigate to="/admin" replace />;
    }
    return children;
  } catch (e) {
    console.error('Invalid token:', e);
    return <Navigate to="/admin" replace />;
  }
}