import { useEffect, useState } from 'react';
import styles from './AvailablePets.module.css';
import PetService from '../../services/PetService';
import PetCard from '../../components/PetCard/PetCard';

export default function AvailablePets(){
     const [pets, setPets] = useState([]);

     useEffect(() => {
          async function fetchPets() {
               try{
                    const availablePets = await PetService.getAllAvailablePets();
                    setPets(availablePets.data);
               } catch(error){
                    console.error("Error fetching pets:", error);
               }
          }
          fetchPets();
     }, []);

     return (
          <div className={styles.container}>
               <h1 className={styles.title}>Available Pets for Adoption</h1>
               <div className={styles.petList}>
               <div className={styles.petList}>
                    {pets.map(pet => (
                         <PetCard key={pet.animalId} pet={pet} />
                    ))}
               </div>
               </div>
          </div>
     )
}