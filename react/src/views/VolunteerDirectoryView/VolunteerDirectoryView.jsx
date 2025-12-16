import { useEffect, useState, useContext } from "react";
import VolunteerService from "../../services/VolunteerService";
import styles from "./VolunteerDirectoryView.module.css";
import { UserContext } from "../../context/UserContext";

export default function VolunteerDirectory() {
  const { user } = useContext(UserContext);

  const [volunteers, setVolunteers] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");
  const [formData, setFormData] = useState({ firstName: "", lastName: "", email: "" });
  const [editingId, setEditingId] = useState(null);
  const [selectedId, setSelectedId] = useState(null); // For row selection

  const isAdmin = user?.role == "ROLE_ADMIN";

  // Fetch volunteers
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
    if (user) fetchVolunteers();
  }, [user]);

  const handleChange = (e) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
  };

  const handleAddOrUpdate = () => {
    if (!isAdmin) return;
    if (editingId) {
      VolunteerService.updateVolunteer({ ...formData, volunteerId: editingId })
        .then(() => {
          fetchVolunteers();
          resetForm();
        })
        .catch((err) => console.error(err));
    } else {
      VolunteerService.addNewVolunteer(formData)
        .then(() => {
          fetchVolunteers();
          resetForm();
        })
        .catch((err) => console.error(err));
    }
  };

  const handleDelete = () => {
    if (!isAdmin || !selectedId) return;
    if (window.confirm("Are you sure you want to delete this volunteer?")) {
      VolunteerService.deleteFromVolunteer(selectedId)
        .then(() => {
          fetchVolunteers();
          resetForm();
        })
        .catch((err) => console.error(err));
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

  const resetForm = () => {
    setFormData({ firstName: "", lastName: "", email: "" });
    setEditingId(null);
    setSelectedId(null);
  };

  if (!user) return <p className={styles.message}>Loading user info...</p>;
  if (loading) return <p className={styles.message}>Loading volunteers...</p>;
  if (error) return <p className={styles.message}>{error}</p>;

  return (
    <div className={styles.container}>
      <h2 className={styles.title}>Volunteer Directory</h2>

      {isAdmin && (
        <div className={styles.form}>
          <input
            type="text"
            name="firstName"
            placeholder="First Name"
            value={formData.firstName}
            onChange={handleChange}
            required
          />
          <input
            type="text"
            name="lastName"
            placeholder="Last Name"
            value={formData.lastName}
            onChange={handleChange}
            required
          />
          <input
            type="email"
            name="email"
            placeholder="Email"
            value={formData.email}
            onChange={handleChange}
            required
          />
        </div>
      )}

      <table className={styles.table}>
        <thead>
          <tr>
            <th>First Name</th>
            <th>Last Name</th>
            <th>Email</th>
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
            </tr>
          ))}
        </tbody>
      </table>

      {/* Admin buttons at the bottom */}
      {isAdmin && (
        <div className={styles.buttonContainer}>
          <button className={styles.addButton} onClick={handleAddOrUpdate}>
            {editingId ? "Update" : "Add"} Volunteer
          </button>
          <button
            className={styles.deleteButton}
            onClick={handleDelete}
            disabled={!selectedId}
          >
            Delete Volunteer
          </button>
          {editingId && (
            <button className={styles.cancelButton} onClick={resetForm}>
              Cancel
            </button>
          )}
        </div>
      )}
    </div>
  );
}
