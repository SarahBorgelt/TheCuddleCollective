import styles from './PetCard.module.css';
import PropTypes from 'prop-types';

const PetCard = ({ pet }) => {
     return (
     <div className={styles.card}>
          <img src={pet.imageUrl} alt={pet.animalName} className={styles.image} />
          <h2 className={styles.name}>{pet.animalName}</h2>
          <p className={styles.breed}>{pet.animalBreed}</p>
          <p className={styles.age}>{pet.animalAge} years old</p>
     </div>
);
};

PetCard.propTypes = {
     pet: PropTypes.shape({
          animalId: PropTypes.number.isRequired,
          animalType: PropTypes.string.isRequired,
          animalBreed: PropTypes.string.isRequired,
          animalColor: PropTypes.string.isRequired,
          animalAge: PropTypes.number.isRequired,
          animalName: PropTypes.string.isRequired,
          adoptionStatus: PropTypes.string.isRequired,
          imageUrl: PropTypes.string.isRequired,
          imageUrl1: PropTypes.string,
          imageUrl2: PropTypes.string,

     }).isRequired,
};

export default PetCard;