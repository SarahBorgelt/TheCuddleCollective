import styles from './PetCard.module.css';

export default function PetCard({ pet }) {
    return (
        <div className={styles.card}>
            <img
                src={pet.imageUrl} 
                alt={pet.name} 
                className={styles.petImage} 
            />
            <h3>{pet.name}</h3>
            <p>Type: {pet.animalType}</p>
            <p>Breed: {pet.breed}</p>
            <p>Age: {pet.age}</p>
            <p>Status: {pet.adoptionStatus}</p>
        </div>
    );
}
