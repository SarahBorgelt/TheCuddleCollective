import { useState } from "react";
import VolunteerService from "../../services/VolunteerService";
import styles from "./VolunteerApplicationView.module.css"; 

export default function VolunteerApplicationForm() {
    const [firstName, setFirstName] = useState("");
    const [lastName, setLastName] = useState("");
    const [email, setEmail] = useState("");
    const [phoneNumber, setPhoneNumber] = useState("");

    const handleSubmit = async (e) => {
        e.preventDefault();
        const applicant = { firstName, lastName, email, phoneNumber };
        try {
            await VolunteerService.submitApplication(applicant);
            alert("Application Submitted! We will contact you with further info.");
        } catch (err) {
            alert("Error submitting application");
            console.error(err);
        }
    };

return (
    <div className={styles.volunteerContainer}>
        <div className={styles.volunteerFormWrapper}>
        <h1 className={styles.heading}>Volunteer Application</h1>
        <form onSubmit={handleSubmit}>
            <div className={styles.formGroup}>
                <label className={styles.label}>First Name:</label>
                <input
                    className={styles.input}
                    type="text"
                    placeholder="First Name"
                    value={firstName}
                    onChange={(e) => setFirstName(e.target.value)}
                />
            </div>

            <div className={styles.formGroup}>
                <label className={styles.label}>Last Name:</label>
                <input
                    className={styles.input}
                    type="text"
                    placeholder="Last Name"
                    value={lastName}
                    onChange={(e) => setLastName(e.target.value)}
                />
            </div>

            <div className={styles.formGroup}>
                <label className={styles.label}>Email:</label>
                <input
                    className={styles.input}
                    type="email"
                    placeholder="Email"
                    value={email}
                    onChange={(e) => setEmail(e.target.value)}
                />
            </div>

            <div className={styles.formGroup}>
                <label className={styles.label}>Phone Number:</label>
                <input
                    className={styles.input}
                    type="text"
                    placeholder="Phone Number"
                    value={phoneNumber}
                    onChange={(e) => setPhoneNumber(e.target.value)}
                />
            </div>

        <div className={styles.formGroup}>
            <label className={styles.label}>Location:</label>
            <div className={styles.radioGroup}>
                <label className={styles.radioLabel}>
                <input type="radio" name="location" value="Cincinnati" />
                Cincinnati
                </label>
                <label className={styles.radioLabel}>
                <input type="radio" name="location" value="Cleveland" />
                Cleveland
                </label>
                <label className={styles.radioLabel}>
                <input type="radio" name="location" value="Columbus" />
                Columbus
                </label>
                <label className={styles.radioLabel}>
                <input type="radio" name="location" value="Atlanta" />
                Atlanta
                </label>
            </div>
        </div>

            <button type="submit" className={styles.submitButton}>
            Submit
            </button>
        </form>
        </div>
    </div>
    );
}
