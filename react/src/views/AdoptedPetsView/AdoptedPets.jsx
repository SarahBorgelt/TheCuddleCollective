import { useEffect, useState } from 'react';
import styles from './AdoptedPets.module.css'
import AdoptedPetService from '../../services/AdoptedPetService';
import PetCard from '../../components/PetCard/PetCard';

export default function AdoptedPets() {
    const [pets, setPets] = useState([]);
    const [featured, setFeatured] = useState(null);

    useEffect(() => {
        AdoptedPetService.getAllAdoptedPets()
            .then((response) => {
                const adoptedPets = response.data.map(pet => ({
                    animalId: pet.animal_id,
                    animalType: pet.animal_type,
                    breed: pet.animal_breed,
                    color: pet.animal_color,
                    age: pet.animal_age,
                    name: pet.animal_name,
                    adoptionStatus: pet.adoption_status,
                    imageUrl: pet.image_url || '/images/default.png',
                    imageUrl1: pet.image_url1 || '/images/default.png',
                    imageUrl2: pet.image_url2 || '/images/default.png',
                }));
                setPets(adoptedPets);
                const index = Math.floor(Math.random() * adoptedPets.length);
                setFeatured(adoptedPets[index]);
            })
            .catch((error) => {
                console.error("Error fetching pets:", error);
            })

    }, []);

    return (
        <div className={styles.container}>
            <h1 className={styles.title}>Most Recent Fur-Ever Home Finders</h1>
            <div className={styles.petList}>
                {pets.length > 0 ? (
                    // pets.map(pet => <PetCard key={pet.animalId} pet={pet} />)
                     <PetCard key={featured.animalId} pet={featured} />
                ) : (
                    <div>No pets available at the moment.</div>
                )}
            </div>
        </div>
    );
}