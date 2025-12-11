import Placeholder2 from '../../assets/Placeholder2.jpg';
import styles from './Header.module.css';

export default function Header() {
  return (
    <div className={styles.banner}>
      <img 
        src={Placeholder2.jpg} 
        alt="Collective Cuddle Logo" 
        className={styles.bannerImage} 
      />
      <div className={styles.bannerText}>
        <p className={styles.italicize}>Finding pets homes, fur good!</p>
      </div>
    </div>
  );
}
