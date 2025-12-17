import { useState } from "react";
import { useNavigate } from "react-router-dom";
import axios from "axios";
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
    <div>
      <h2>Activate Volunteer Access</h2>

      <Notification
        notification={notification}
        clearNotification={() => setNotification(null)}
      />

      <form onSubmit={submitCode}>
        <label>
          Volunteer Code
          <input
            type="text"
            value={code}
            onChange={(e) => setCode(e.target.value)}
            required
          />
        </label>

        <button type="submit">Activate</button>
      </form>
    </div>
  );
}