import { useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import AuthService from '../../services/AuthService';
import Notification from '../../components/Notification/Notification';

import styles from './RegisterView.module.css';

export default function RegisterView() {
  const navigate = useNavigate();

  const [notification, setNotification] = useState(null);

  // Setup state for the registration data
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [confirmPassword, setConfirmPassword] = useState('');
  const [volunteerCode, setVolunteerCode] = useState('');


  function handleSubmit(event) {
    event.preventDefault();


    // Validate the form data
    if (password !== confirmPassword) {
      // Passwords don't match, so display error notification
      setNotification({ type: 'error', message: 'Passwords do not match.' });
    } else {
      // If no errors, send data to server
      AuthService.register({
        username,
        password,
        confirmPassword,
        role: 'user',
      })
        .then(() => {
          setNotification({ type: 'success', message: 'Registration successful! Redirecting you to login page...' });
          setTimeout(() => {
            navigate('/login');
          }, 3000);
        })
        .catch((error) => {
          // Check for a response message, but display a default if that doesn't exist
          const message = error.response?.data?.message || 'Registration failed.';
          setNotification({ type: 'error', message: message });
        });
    }
  }

  return (
    <div id="view-register">
      <h2 className={styles.registerHeader}>Register</h2>

      <Notification notification={notification} clearNotification={() => setNotification(null)} />

      <form onSubmit={handleSubmit}>
        <div className="form-control">
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

        <div className="form-control">
          <label htmlFor="password">Password:</label>
          <input
            type="password"
            id="password"
            value={password}
            required
            onChange={(event) => setPassword(event.target.value)}
          />
        </div>

        <div className="form-control">
          <label htmlFor="confirmPassword">Confirm Password:</label>
          <input
            type="password"
            id="confirmPassword"
            value={confirmPassword}
            required
            onChange={(event) => setConfirmPassword(event.target.value)}
          />
        </div>

        <button type="submit" className={`btn-primary ${styles.formButton}`}>
          Register
        </button>
        <Link to="/login">Have an account? Log in</Link>
      </form>
    <hr />

<h3>Already have a volunteer invite code?</h3>

<form
  onSubmit={(e) => {
    e.preventDefault();

    axios.post(
      '/register/volunteer-code',
      { code: volunteerCode },
      {
        headers: {
          Authorization: `Bearer ${localStorage.getItem('token')}`
        }
      }
    )
    .then(() => {
      setNotification({
        type: 'success',
        message: 'Volunteer code accepted! Please log in again.'
      });

      localStorage.removeItem('user');
      localStorage.removeItem('token');

      setTimeout(() => {
        navigate('/login');
      }, 2000);
    })
    .catch((err) => {
      setNotification({
        type: 'error',
        message: err.response?.data?.message || 'Invalid volunteer code'
      });
    });
  }}
>
  <div className="form-control">
    <label htmlFor="volunteerCode">Volunteer Code:</label>
    <input
      type="text"
      id="volunteerCode"
      value={volunteerCode}
      onChange={(e) => setVolunteerCode(e.target.value)}
      required
    />
  </div>

  <button type="submit" className={`btn-primary ${styles.formButton}`}>
    Submit Volunteer Code
  </button>
</form>
    </div>
    
  );
}
