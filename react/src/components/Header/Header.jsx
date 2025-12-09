import Placeholder2 from '../../assets/Placeholder2.jpg';
import styles from './Header.module.css';

export default function Header() {
     return (
          <div className="header-container">         
               <h1 className = {styles.header}>The Cuddle Collective</h1>
               <p className={styles.italicize}>Finding pets homes, fur good!</p>
               <img src={Placeholder2} alt = "A placeholder"/>
          </div>
     );
}    