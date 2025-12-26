import { useState } from "react";
import { useNavigate } from "react-router-dom";
import axios from "axios";
import styles from './VolunteerActivationView.module.css';
import Notification from "../../components/Notification/Notification";

export default function VolunteerActivationView() {
  const [code, setCode] = useState("");
  const [notification, setNotification] = useState(null);
  const navigate = useNavigate();

  const submitCode = async (e) => {
    e.preventDefault();

    try {
      await axios.post("/register/volunteer-code", {
        code: code.trim().toUpperCase()
      });

      setNotification({
        type: "success",
        message: "Volunteer code accepted! Please log in again."
      });

      setTimeout(() => navigate("/login"), 2000);

    } catch (err) {
      setNotification({
        type: "error",
        message: err.response?.data?.message || "Invalid volunteer code"
      });
    }
  };

  return (
    <div className={styles.activationContainer}>
    <div className={styles.activationCard}>
      <h2 className={styles.activationTitle}>Activate Volunteer Access</h2>

      <Notification
        notification={notification}
        clearNotification={() => setNotification(null)}
      />

      <form className={styles.activationForm} onSubmit={submitCode}>
        <label className={styles.activationLabel}>
          Volunteer Code
          <input
          className={styles.activationInput}
            type="text"
            value={code}
            onChange={(e) => setCode(e.target.value)}
            required
          />
        </label>

        <button className={styles.activateButton} type="submit">Activate</button>
      </form>
    </div>
  </div>
  );
}