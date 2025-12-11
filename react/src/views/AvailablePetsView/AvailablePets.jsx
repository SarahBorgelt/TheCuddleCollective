import { useEffect, useState } from 'react';
import styles from './AvailablePets.module.css';
import PetService from '../../services/PetService';
import PetCard from '../../components/PetCard/PetCard';

export default function AvailablePets() {
    const [pets, setPets] = useState([]);

    useEffect(() => {
        async function fetchPets() {
            try {
                const response = await PetService.getAllAvailablePets();
                // Normalize backend data to camelCase
                const normalizedPets = response.data.map(pet => ({
                    animalId: pet.animal_id,
                    animalType: pet.animal_type,
                    breed: pet.breed,
                    color: pet.color,
                    age: pet.age,
                    name: pet.name,
                    adoptionStatus: pet.adoption_status,
                    imageUrl: pet.image_url || '/images/default.png',
                    imageUrl1: pet.image_url1 || '/images/default.png',
                    imageUrl2: pet.image_url2 || '/images/default.png',
                }));
                setPets(normalizedPets);
            } catch (error) {
                console.error("Error fetching pets:", error);
            }
        }
        fetchPets();
    }, []);

    return (
        <div className={styles.container}>
            <h1 className={styles.title}>Available Pets for Adoption</h1>
            <div className={styles.petList}>
                {pets.length > 0 ? (
                    pets.map(pet => <PetCard key={pet.animalId} pet={pet} />)
                ) : (
                    <div>No pets available at the moment.</div>
                )}
            </div>
        </div>
    );
}
