import { useEffect, useState } from 'react';
import axios from 'axios';
import Notification from '../../components/Notification/Notification';
import ProtectedRoute from '../../components/ProtectedRoute';
import styles from './AdminApplicationView.module.css';

export default function AdminApplicationsView() {
  const [applications, setApplications] = useState([]);
  const [notification, setNotification] = useState(null);

  const token = localStorage.getItem('token');

  useEffect(() => {
    axios.get('/admin/applications/pending', {
      headers: { Authorization: `Bearer ${token}` }
    })
      .then(res => setApplications(res.data))
      .catch(err => {
        setNotification({ type: 'error', message: 'Failed to fetch applications.' });
      });
  }, [token]);


  const handleApprove = (id) => {
    axios.post(`/admin/applications/${id}/approve`, null, {
      headers: { Authorization: `Bearer ${token}` }
    })      .then(() => {
        setNotification({ type: 'success', message: 'Application approved!' });
        setApplications(applications.filter(app => app.applicationId !== id));
      })
      .catch(() => {
        setNotification({ type: 'error', message: 'Failed to approve application.' });
      });
  };

  const handleDeny = (id) => {
    axios.post(`/admin/applications/${id}/deny`, null, {
      headers: { Authorization: `Bearer ${token}` }
    })      .then(() => {
        setNotification({ type: 'success', message: 'Application denied.' });
        setApplications(applications.filter(app => app.applicationId !== id));
      })
      .catch(() => {
        setNotification({ type: 'error', message: 'Failed to deny application.' });
      });
  };

  return (
    <ProtectedRoute adminOnly={true}>
      <div className={styles.formGroup}>
        <h2>Pending Volunteer Applications</h2>

        <Notification notification={notification} clearNotification={() => setNotification(null)} />

        {applications.length === 0 ? (
          <p>No pending applications.</p>
        ) : (
          <table>
            <thead>
              <tr>
                <th>Name</th>
                <th>Email</th>
                <th>Phone</th>
                <th>Actions</th>
              </tr>
            </thead>
            <tbody className={styles.applicationContainer}>
              {applications.map(app => (
                <tr key={app.applicationId}>
                  <td>{app.firstName} {app.lastName}</td>
                  <td>{app.email}</td>
                  <td>{app.phoneNumber}</td>
                  <td className={styles.button}>
                    <button className={styles.approveButton} onClick={() => handleApprove(app.applicationId)}>Approve</button>
                    <button className={styles.denyButton} onClick={() => handleDeny(app.applicationId)}>Deny</button>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        )}
      </div>
    </ProtectedRoute>
  );
}