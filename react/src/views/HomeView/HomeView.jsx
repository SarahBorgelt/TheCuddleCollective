// import AdoptedPetService from '../../services/AdoptedPetService';
// import AdoptedPetCarousel from '../../components/AdoptedPetCarousel/AdoptedPetCarousel'
import styles from './HomeView.module.css';

export default function HomeView() {

  return (
    <div className={styles.container}>
      <h1 className={styles.welcome}>Welcome to the Cuddle Collective</h1>
      {/* <br />
      <h2>Recently Adopted Pets:
        <AdoptedPetCarousel />
      </h2>
      <br /> */}
      <p className={styles.homePageText}>The Cuddle Collective is a warm, community-driven space dedicated to helping every animal 
        find a loving home. Here, future pet parents can browse adoptable pets, learn their stories, 
        and connect with shelters in their area. We also make it easy for animal lovers to get involved 
        — from signing up to volunteer at local shelters to supporting rescue efforts across the community. 
        Together, we’re creating a world where every pet is welcomed, cared for, and truly home — 
        fur good.</p>  
    </div>
  
  );
}