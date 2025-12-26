import { useEffect, useState, useContext } from "react";
import VolunteerService from "../../services/VolunteerService";
import styles from "./VolunteerDirectoryView.module.css";
import { UserContext } from "../../context/UserContext";
import axios from "axios";


export default function VolunteerDirectory() {
  const { user } = useContext(UserContext);
  const [approvedApps, setApprovedApps] = useState([]);
  const [volunteers, setVolunteers] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");
  const [formData, setFormData] = useState({ firstName: "", lastName: "", email: "" });
  const [editingId, setEditingId] = useState(null);
  const [selectedId, setSelectedId] = useState(null);

const fetchVolunteers = async () => {
  try {
    setLoading(true);
    const volunteersRes = await VolunteerService.getAllVolunteers();
    let volunteersData = volunteersRes.data;
    const approvedRes = await VolunteerService.getApprovedApplicationsWithCodes();
    const approvedAppsData = approvedRes.data;
    volunteersData = volunteersData.map(v => {
      const matchedApp = approvedAppsData.find(app => app.email === v.email);
      return {
        ...v,
        inviteCode: matchedApp ? matchedApp.inviteCode : null
      };
    });
    setVolunteers(volunteersData);
    setLoading(false);
  } catch (err) {
    console.error("Failed to fetch volunteers:", err);
    setError("Failed to load volunteers.");
    setLoading(false);
  }
};



useEffect(() => {
  fetchVolunteers();
}, []);

  const handleChange = (e) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
  };

  const resetForm = () => {
    setFormData({ firstName: "", lastName: "", email: "" });
    setEditingId(null);
    setSelectedId(null);
  };

  const handleUpdate = async () => {
    if (!user || !editingId) return;
    try {
      await VolunteerService.updateVolunteer(editingId, formData);
      fetchVolunteers();
      resetForm();
    } catch (err) {
      console.error("Update failed:", err.response || err);
    }
  };

  const handleDelete = async () => {
    if (!user || !selectedId) return;
    if (!window.confirm("Are you sure you want to delete this volunteer?")) return;
    try {
      await VolunteerService.deleteFromVolunteer(selectedId);
      fetchVolunteers();
      resetForm();
    } catch (err) {
      console.error("Delete failed:", err.response || err);
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
{user?.authorities?.some(a => a.name === "ROLE_ADMIN") && (
  <th>Invite Code</th>
)}          </tr>
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
                 {user?.authorities?.some(a => a.name === "ROLE_ADMIN") ? (
                 <td><code>{volunteer.inviteCode || "—"}</code></td>
                 ) : null}
            </tr>

          ))}
        </tbody>
      </table>

      <div className={styles.buttonContainer}>
       {user?.authorities?.some(a => a.name === "ROLE_ADMIN") && editingId && (
        <button
            className={styles.updateButton}
            onClick={handleUpdate}
            disabled={!formData.firstName || !formData.lastName || !formData.email}
        >
            Update Volunteer
        </button>
        )}

        {user?.authorities?.some(a => a.name === "ROLE_ADMIN") && selectedId && (
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
