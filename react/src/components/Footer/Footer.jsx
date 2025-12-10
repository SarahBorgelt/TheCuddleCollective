import styles from './Footer.module.css';
import {Routes, Route, useNavigate} from 'react-router-dom';

export default function Footer() {
     const navigate = useNavigate();

     const navigateToApplication = () => {
          navigate('/volunteer/apply');
     };

     return (
          <footer className={styles.footer}>
               <h1 className = {styles.footerVolunteerAsk}>Interested in volunteering?</h1>
               <button className={styles.volunteerButton} onClick={navigateToApplication}>Sign Up Now!</button>
               <p className={styles.copyright}>© 2025 Animal Shelter Manager. All rights reserved.</p>
          </footer>
     );
}

