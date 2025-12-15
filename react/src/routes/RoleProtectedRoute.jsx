import { Navigate } from 'react-router-dom';
import { useContext } from 'react';
import { UserContext } from '../context/UserContext';

export default function RoleProtectedRoute({ allowedRoles, children }) {
  const { user } = useContext(UserContext);

  if (!user) return <Navigate to="/login" />;

  if (!allowedRoles.includes(user.authorities)) {
    return <Navigate to="/" />;
  }

  return children;
}