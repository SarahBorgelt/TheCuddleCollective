import { useContext } from 'react';
import { UserContext } from '../../context/UserContext';
import styles from './UserProfileView.module.css';

export default function UserProfileView() {
  const { user } = useContext(UserContext);
  const isAdmin = user?.authorities?.some(auth => auth.name == "ROLE_ADMIN");

  return (
    <div className={styles.userProfile}>
      <h1>User Profile</h1>
      <br />
      <p className={styles.profileGreeting}>Hello, {user.username}!</p>
            {isAdmin ? (
              <p className={styles.accessLevel}> Role: Admin</p>
            ) : <p className={styles.accessLevel}>Role: Volunteer</p>} 
    </div>
  );
}