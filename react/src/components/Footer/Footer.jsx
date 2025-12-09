import styles from './Footer.module.css';

export default function Footer() {
     return (
          <footer className={styles.footer}>
               <h1 className = {styles.footerVolunteerAsk}>Are you interested in becoming a volunteer?</h1>
               <button className={styles.volunteerButton}>Sign Up Now</button>
               <p className={styles.copyright}>© 2025 Animal Shelter Manager. All rights reserved.</p>
          </footer>
     );
}

