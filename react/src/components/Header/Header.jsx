import Placeholder2 from '../../assets/Placeholder2.jpg';
import styles from './Header.module.css';

export default function Header() {
     return (
          <div className="header-container">         
               <h1 className = {styles.header}>Together Fur-Ever</h1>
               <img src={Placeholder2} alt = "A placeholder"/>
          </div>
     );
}    