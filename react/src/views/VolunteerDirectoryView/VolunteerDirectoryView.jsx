import { useEffect, useState, useContext } from "react";
import VolunteerService from "../../services/VolunteerService";
import styles from "./VolunteerDirectoryView.module.css";
import { UserContext } from "../../context/UserContext";
import axios from "axios";

export default function VolunteerDirectory() {
  const { user } = useContext(UserContext);

  const [volunteers, setVolunteers] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");
  const [formData, setFormData] = useState({ firstName: "", lastName: "", email: "" });
  const [editingId, setEditingId] = useState(null);
  const [selectedId, setSelectedId] = useState(null);
  const [inviteCodes, setInviteCodes] = useState([]);


  const fetchVolunteers = () => {
    setLoading(true);
    VolunteerService.getAllVolunteers()
      .then((res) => {
        setVolunteers(res.data);
        setLoading(false);
      })
      .catch((err) => {
        console.error(err);
        setError("Failed to load volunteers.");
        setLoading(false);
      });
  };

useEffect(() => {
  if (!user) return;
  fetchVolunteers();
  if (user.authorities?.includes('ROLE_ADMIN')) {
    const token = localStorage.getItem('token');
    axios.get('/admin/applications/approved', {
      headers: { Authorization: `Bearer ${token}` }
    })
      .then(res => setInviteCodes(res.data))
      .catch(err => console.error('Failed to load invite codes', err));
  }
}, [user]);

  const handleChange = (e) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
  };

  const resetForm = () => {
    setFormData({ firstName: "", lastName: "", email: "" });
    setEditingId(null);
    setSelectedId(null);
  };

  const handleUpdate = () => {
    if (!user || !editingId) return;
    VolunteerService.updateVolunteer(editingId, formData)
      .then(() => {
        fetchVolunteers();
        resetForm();
      })
      .catch((err) => console.error("Update failed:", err.response || err));
  };

  const handleDelete = () => {
    if (!user || !selectedId) return;
    if (window.confirm("Are you sure you want to delete this volunteer?")) {
      VolunteerService.deleteFromVolunteer(selectedId)
        .then(() => {
          fetchVolunteers();
          resetForm();
        })
        .catch((err) => console.error("Delete failed:", err.response || err));
    }
  };

  const handleSelect = (volunteer) => {
    setSelectedId(volunteer.volunteerId);
    setFormData({
      firstName: volunteer.firstName,
      lastName: volunteer.lastName,
      email: volunteer.email,
    });
    setEditingId(volunteer.volunteerId);
  };

  const getInviteCodeForVolunteer = (email) => {
  const app = inviteCodes.find(app => app.email === email);
  return app ? app.inviteCode : '—';
};

  if (!user) return <p className={styles.message}>Loading user info...</p>;
  if (loading) return <p className={styles.message}>Loading volunteers...</p>;
  if (error) return <p className={styles.message}>{error}</p>;

  return (
    <div className={styles.container}>
      <h2 className={styles.title}>Volunteer Directory</h2>

      <div className={styles.form}>
        <input
          type="text"
          name="firstName"
          placeholder="First Name"
          value={formData.firstName}
          onChange={handleChange}
        />
        <input
          type="text"
          name="lastName"
          placeholder="Last Name"
          value={formData.lastName}
          onChange={handleChange}
        />
        <input
          type="email"
          name="email"
          placeholder="Email"
          value={formData.email}
          onChange={handleChange}
        />
      </div>

      <table className={styles.table}>
        <thead>
          <tr>
            <th>First Name</th>
            <th>Last Name</th>
            <th>Email</th>

            {user && user.authorities?.includes('ROLE_ADMIN') && (<th>Invite Code</th>)}
          </tr>
        </thead>
        <tbody>
          {volunteers.map((volunteer) => (
            <tr
              key={volunteer.volunteerId}
              className={selectedId === volunteer.volunteerId ? styles.selectedRow : ""}
              onClick={() => handleSelect(volunteer)}
            >
              <td>{volunteer.firstName}</td>
              <td>{volunteer.lastName}</td>
              <td>{volunteer.email}</td>
              {user && user.authorities?.includes('ROLE_ADMIN') && (
        <td>
          <code>{getInviteCodeForVolunteer(volunteer.email)}</code>
        </td>
      )}
            </tr>
          ))}
        </tbody>
      </table>

      <div className={styles.buttonContainer}>
        {editingId && (
          <button
            className={styles.updateButton}
            onClick={handleUpdate}
            disabled={!formData.firstName || !formData.lastName || !formData.email}
          >
            Update Volunteer
          </button>
        )}

        {selectedId && (
          <button
            className={styles.deleteButton}
            onClick={handleDelete}
          >
            Delete Volunteer
          </button>
        )}

        {editingId && (
          <button className={styles.cancelButton} onClick={resetForm}>
            Cancel
          </button>
        )}
      </div>
    </div>
  );
}
