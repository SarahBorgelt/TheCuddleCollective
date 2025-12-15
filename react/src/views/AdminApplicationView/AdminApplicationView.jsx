import { useEffect, useState } from 'react';
import axios from 'axios';
import Notification from '../../components/Notification/Notification';
import ProtectedRoute from '../../components/ProtectedRoute';

export default function AdminApplicationView() {
  const [applications, setApplications] = useState([]);
  const [notification, setNotification] = useState(null);

  // Fetch pending applications
  useEffect(() => {
    axios.get('/admin/applications/pending')
      .then(res => setApplications(res.data))
      .catch(err => {
        setNotification({ type: 'error', message: 'Failed to fetch applications.' });
      });
  }, []);

  // Approve an application
  const handleApprove = (id) => {
    axios.post(`/admin/applications/${id}/approve`)
      .then(() => {
        setNotification({ type: 'success', message: 'Application approved!' });
        setApplications(applications.filter(app => app.applicationId !== id));
      })
      .catch(() => {
        setNotification({ type: 'error', message: 'Failed to approve application.' });
      });
  };

  // Deny an application
  const handleDeny = (id) => {
    axios.post(`/admin/applications/${id}/deny`)
      .then(() => {
        setNotification({ type: 'success', message: 'Application denied.' });
        setApplications(applications.filter(app => app.applicationId !== id));
      })
      .catch(() => {
        setNotification({ type: 'error', message: 'Failed to deny application.' });
      });
  };

  return (
    <ProtectedRoute adminOnly={true}>
      <div>
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
            <tbody>
              {applications.map(app => (
                <tr key={app.applicationId}>
                  <td>{app.firstName} {app.lastName}</td>
                  <td>{app.email}</td>
                  <td>{app.phoneNumber}</td>
                  <td>
                    <button onClick={() => handleApprove(app.applicationId)}>Approve</button>
                    <button onClick={() => handleDeny(app.applicationId)}>Deny</button>
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