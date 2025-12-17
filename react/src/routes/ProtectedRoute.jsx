import { Navigate } from 'react-router-dom';
import { useContext } from 'react';
import { UserContext } from '../context/UserContext';

export default function ProtectedRoute({ children, adminOnly = false }) {
  const { user } = useContext(UserContext);

  if (!user) {
    return <Navigate to="/login" replace />;
  }

if (user.firstLogin && user.roles.includes('ROLE_VOLUNTEER')) {
  return <Navigate to="/first-login" replace />;
}

  if (adminOnly && user && !user.authorities?.includes('ROLE_ADMIN')){
    return <Navigate to="/" replace />;
  }

  return children;
}