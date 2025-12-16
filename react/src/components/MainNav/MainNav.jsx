import { useContext } from 'react';
import { Link, NavLink } from 'react-router-dom';
import { UserContext } from '../../context/UserContext';
import styles from './MainNav.module.css';
// NavLink on line 32 was added by me, may need to remove later.

export default function MainNav() {
  const { user } = useContext(UserContext);

  return (
    <aside className={styles.sidebar}>
      <nav className={styles.nav}>
        
        <NavLink
          to="/"
          className={({ isActive }) =>
            isActive ? styles.activeLink : styles.link
          }
        >
          Home
        </NavLink>

        <NavLink
          to="/availablePets"
          className={({ isActive }) =>
            isActive ? styles.activeLink : styles.link
          }
        >
          Available Pets
        </NavLink>

           <NavLink
          to="/adoptedPets"
          className={({ isActive }) =>
            isActive ? styles.activeLink : styles.link
          }
        >
          Adopted Pets
        </NavLink>

        {user ? (
          <>
            <NavLink
              to="/addOrUpdatePets"
              className={({ isActive }) =>
                isActive ? styles.activeLink : styles.link
              }
            >
              Add or Update Pets
            </NavLink>

            <NavLink to="/volunteer/directory" className={({isActive})=>
              isActive ? styles.activeLink : styles.link}>
                Volunteer Directory
            </NavLink>

            <NavLink
              to="/userProfile"
              className={({ isActive }) =>
                isActive ? styles.activeLink : styles.link
              }
            >
              Profile
            </NavLink>

            <Link to="/logout" className={styles.logout}>
              Logout
            </Link>
          </>
        ) : (
          <NavLink
            to="/login"
            className={({ isActive }) =>
              isActive ? styles.activeLink : styles.link
            }
          >
            Login
          </NavLink>
        )}
      </nav>
    </aside>
  );
}