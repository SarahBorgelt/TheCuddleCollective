import { useContext, useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';

import AuthService from '../../services/AuthService';
import Notification from '../../components/Notification/Notification';
import { UserContext } from '../../context/UserContext';
import axios from 'axios';

import styles from './LoginView.module.css';

export default function LoginView() {
  const { setUser } = useContext(UserContext);
  const navigate = useNavigate();
  const [notification, setNotification] = useState(null);

  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');

  function handleSubmit(event) {
    event.preventDefault();

    AuthService.login({ username, password })
      .then((response) => {
        const user = response.data.user;
        const token = response.data.token;
        axios.defaults.headers.common['Authorization'] = `Bearer ${token}`;

        localStorage.setItem('user', JSON.stringify(user));
        localStorage.setItem('token', token);

        setUser(user);

        if (user.firstLogin) {
          navigate('/first-login');
        } else {
          navigate('/');
        }
      })
      .catch((error) => {
        const message = error.response?.data?.message || 'Login failed.';
        setNotification({ type: 'error', message });
      });
  }

  return (
    <div className={styles.loginView}>
      <h2>Log in</h2>

      <Notification notification={notification} clearNotification={() => setNotification(null)} />

      <form onSubmit={handleSubmit} className={styles.loginForm}>

  <div className={styles.formControl}>
    <label htmlFor="username">Username:</label>
    <input 
      type="text" 
      id="username" 
      value={username} 
      required 
      autoFocus 
      autoComplete="username"
      onChange={(event) => setUsername(event.target.value)} 
    />
  </div>

  <div className={styles.formControl}>
    <label htmlFor="password">Password:</label>
    <input 
      type="password" 
      id="password" 
      value={password} 
      required
      onChange={(event) => setPassword(event.target.value)} 
    />
  </div>

  <button type="submit" className={`btn-primary ${styles.formButton}`}>Sign in</button>
  <Link to="/register" className={styles.registerLink}>New? Register here!</Link>
</form>

    </div>
  );
}
