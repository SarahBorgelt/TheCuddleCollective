import { useContext } from 'react';
import { Link, NavLink } from 'react-router-dom';
import { UserContext } from '../../context/UserContext';
import styles from './MainNav.module.css';

export default function MainNav() {
  const { user } = useContext(UserContext);
  const isAdmin = user?.authorities?.some(auth => auth.name == "ROLE_ADMIN");
  const isVolunteer = user?.authorities?.some(auth => auth.name == "ROLE_VOLUNTEER");

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
              {(!isAdmin && !isVolunteer) && (
              <NavLink
                to="/activate-volunteer"
                className={({ isActive }) => isActive ? styles.activeLink : styles.link}
              >
                Activate Volunteer Account
              </NavLink>
            )}

            {(isAdmin || isVolunteer) && (
            <NavLink
              to="/addOrUpdatePets"
              className={({ isActive }) =>
                isActive ? styles.activeLink : styles.link
              }
            >
              Add or Update Pets
            </NavLink>
             )}
{(isAdmin || isVolunteer) && (
            <NavLink
              to="/petparents"
              className={({ isActive }) =>
                isActive ? styles.activeLink : styles.link
              }
            >
              Pet Parents
            </NavLink>
)}
            {(isAdmin || isVolunteer) && (
            <NavLink to="/volunteer/directory" className={({isActive})=>
              isActive ? styles.activeLink : styles.link}>
                Volunteer Directory
            </NavLink>
            )}

            {isAdmin && (
              <NavLink
                to="/admin/applications"
                className={({ isActive }) => isActive ? styles.activeLink : styles.link}
              >
                Volunteer Applications
              </NavLink>
            )}

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