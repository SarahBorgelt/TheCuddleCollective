import { useContext, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';

import { UserContext } from '../../context/UserContext';
import Notification from '../../components/Notification/Notification';


export default function FirstLoginView() {
  const { user, setUser } = useContext(UserContext);
  const navigate = useNavigate();

  const [newPassword, setNewPassword] = useState('');
  const [notification, setNotification] = useState(null);

  if (!user || !user.firstLogin) {
  navigate('/');
  return null;
}

  function handleSubmit(e) {
    e.preventDefault();

    axios.post('/auth/first-login', {
      username: user.username,
      newPassword
    })
    .then(() => {
      // update local user state to reflect activation
      const updatedUser = { ...user, firstLogin: false };

      localStorage.setItem('user', JSON.stringify(updatedUser));
      setUser(updatedUser);

      navigate('/');
    })
    .catch((error) => {
      const message =
        error.response?.data?.message ||
        'Password does not meet requirements.';

      setNotification({ type: 'error', message });
    });
  }

  return (
    <div>
      <h2>Activate Your Account</h2>

      <Notification
        notification={notification}
        clearNotification={() => setNotification(null)}
      />

      <form onSubmit={handleSubmit}>
        <label htmlFor="password">New Password</label>
        <input
          type="password"
          id="password"
          value={newPassword}
          required
          onChange={(e) => setNewPassword(e.target.value)}
        />

        <button type="submit">
          Set Password
        </button>
      </form>
    </div>
  );
}