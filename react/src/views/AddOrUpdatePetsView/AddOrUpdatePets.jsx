import { useState, useEffect, useCallback } from 'react';
import styles from './AddOrUpdatePets.module.css';
import PetService from '../../services/PetService';

export default function AddOrUpdatePets() {
  const [mode, setMode] = useState(null);
  const [formData, setFormData] = useState({
    type: '',
    breed: '',
    color: '',
    age: '',
    name: '',
    adoptionStatus: 'available',
    imageUrl: '',
    imageUrl1: '',
    imageUrl2: '',
    animalId: null,
    parentId: null,
  });
  const [petsList, setPetsList] = useState([]);
  const [searchTerm, setSearchTerm] = useState('');
  const [selectedPet, setSelectedPet] = useState(null);
  const [imageOptions, setImageOptions] = useState({
    imageUrl: 'url',
    imageUrl1: 'url',
    imageUrl2: 'url',
  });
  const [successMessage, setSuccessMessage] = useState('');

  // Normalize backend snake_case -> frontend camelCase
  const normalizePet = (p) => ({
    animalId: p.animal_id,
    type: p.animal_type,
    breed: p.animal_breed,
    color: p.animal_color,
    age: p.animal_age,
    name: p.animal_name,
    parentId: p.parent_id,
    adoptionStatus: p.adoption_status,
    imageUrl: p.image_url,
    imageUrl1: p.image_url1,
    imageUrl2: p.image_url2,
  });

  // Load pets from backend
  const loadPets = useCallback(async () => {
    try {
      const res = await PetService.getAllPetsForUpdates();
      setPetsList(res.data.map(normalizePet));
    } catch (err) {
      console.error('Error fetching pets:', err);
    }
  }, []);

  useEffect(() => {
    if (mode === 'update') loadPets();
  }, [mode, loadPets]);

  // Prefill form when a pet is selected
  useEffect(() => {
    if (selectedPet) {
      setFormData({
        animalId: selectedPet.animalId,
        type: selectedPet.type ?? '',
        breed: selectedPet.breed ?? '',
        color: selectedPet.color ?? '',
        age: selectedPet.age ?? '',
        name: selectedPet.name ?? '',
        parentId: selectedPet.parentId ?? null,
        adoptionStatus: selectedPet.adoptionStatus ?? 'available',
        imageUrl: selectedPet.imageUrl ?? '',
        imageUrl1: selectedPet.imageUrl1 ?? '',
        imageUrl2: selectedPet.imageUrl2 ?? '',
      });
    }
  }, [selectedPet]);

  // Handle input changes
  const handleChange = (e) => {
    const { name, value, files } = e.target;
    if (files && files[0]) {
      setFormData((prev) => ({ ...prev, [name]: files[0] }));
    } else {
      setFormData((prev) => ({ ...prev, [name]: value }));
    }
  };

  // Map camelCase frontend -> snake_case backend
  const mapToSnakeCase = (data) => ({
    animal_id: data.animalId,
    animal_type: data.type,
    animal_breed: data.breed,
    animal_color: data.color,
    animal_age: Number(data.age),
    animal_name: data.name,
    parent_id: data.parentId,
    adoption_status: data.adoptionStatus,
    image_url: data.imageUrl instanceof File ? undefined : data.imageUrl,
    image_url1: data.imageUrl1 instanceof File ? undefined : data.imageUrl1,
    image_url2: data.imageUrl2 instanceof File ? undefined : data.imageUrl2,
  });

  // Handle form submission with file uploads
  const handleSubmit = async (e) => {
    e.preventDefault();

    try {
      const formPayload = new FormData();
      // JSON part
      formPayload.append(
        'data',
        new Blob([JSON.stringify(mapToSnakeCase(formData))], { type: 'application/json' })
      );

      // File parts
      if (imageOptions.imageUrl === 'upload' && formData.imageUrl instanceof File) {
        formPayload.append('imageUrl', formData.imageUrl);
      }
      if (imageOptions.imageUrl1 === 'upload' && formData.imageUrl1 instanceof File) {
        formPayload.append('imageUrl1', formData.imageUrl1);
      }
      if (imageOptions.imageUrl2 === 'upload' && formData.imageUrl2 instanceof File) {
        formPayload.append('imageUrl2', formData.imageUrl2);
      }

      let res;
      if (mode === 'add') {
        res = await PetService.addNewPet(formPayload);
        setSuccessMessage(`Pet "${res.data.animal_name}" added successfully!`);
      } else if (mode === 'update') {
        if (!selectedPet) return alert('Select a pet to update');
        res = await PetService.updatePet(selectedPet.animalId, formPayload);
        setSuccessMessage(`Pet "${res.data.animal_name}" updated successfully!`);
        loadPets();
      }

      setTimeout(() => setSuccessMessage(''), 20 * 1000);

      // Reset form
      setFormData({
        type: '',
        breed: '',
        color: '',
        age: '',
        name: '',
        adoptionStatus: 'available',
        imageUrl: '',
        imageUrl1: '',
        imageUrl2: '',
        animalId: null,
        parentId: null,
      });
      setSelectedPet(null);
    } catch (err) {
      console.error('Failed to save pet:', err);
      alert('Failed to save pet. Check console for details.');
    }
  };

  // Filter pets by any attribute
  const filterPets = (pets, term) => {
    if (!term) return pets;
    const words = term.toLowerCase().split(/\s+/);
    return pets.filter((p) =>
      words.some((word) =>
        (p.name && p.name.toLowerCase().includes(word)) ||
        (p.type && p.type.toLowerCase().includes(word)) ||
        (p.breed && p.breed.toLowerCase().includes(word)) ||
        (p.color && p.color.toLowerCase().includes(word)) ||
        (p.age && p.age.toString().includes(word)) ||
        (p.adoptionStatus && p.adoptionStatus.toLowerCase().includes(word))
      )
    );
  };

  // Update filtered pets as user types
  useEffect(() => {
    if (!searchTerm) {
      loadPets();
      return;
    }
    setPetsList((prev) => filterPets(prev, searchTerm));
  }, [searchTerm, loadPets]);

  // Handle search button
  const handleSearch = () => {
    loadPets().then((res) => {
      setPetsList(filterPets(res.data.map(normalizePet), searchTerm));
    });
  };

  // Mode selection screen
  if (!mode) {
    return (
      <div className={styles.container}>
        <h2 className={styles.title}>What would you like to do?</h2>
        <div className={styles.buttonRow}>
          <button onClick={() => setMode('add')} className={styles.actionButton}>Add Pet</button>
          <button onClick={() => setMode('update')} className={styles.actionButton}>Update Pet</button>
        </div>
      </div>
    );
  }

  return (
    <div className={styles.container}>
      <h2 className={styles.title}>{mode === 'add' ? 'Add New Pet' : 'Update Pet'}</h2>

      {successMessage && <div className={styles.successMessage}>{successMessage}</div>}

      {mode === 'update' && (
        <div className={styles.updateSection}>
          <div className={styles.searchRow}>
            <input
              type="text"
              placeholder="Search pets..."
              value={searchTerm}
              onChange={(e) => setSearchTerm(e.target.value)}
              className={styles.searchInput}
            />
            <button onClick={handleSearch} className={styles.searchButton}>Search</button>
            <button
              onClick={() => {
                setMode(null);
                setSelectedPet(null);
                setSearchTerm('');
              }}
              className={styles.backButton}
            >
              Back
            </button>
          </div>

          <div className={styles.petsList}>
            {petsList.map((pet) => (
              <div
                key={pet.animalId}
                className={`${styles.petCard} ${selectedPet?.animalId === pet.animalId ? styles.selectedPet : ''}`}
                onClick={() => setSelectedPet(pet)}
              >
                <img src={pet.imageUrl} alt={pet.name} className={styles.petImage} />
                <div>{pet.name} ({pet.type})</div>
              </div>
            ))}
            {petsList.length === 0 && <div>No pets found</div>}
          </div>
        </div>
      )}

      {(mode === 'add' || selectedPet) && (
        <form onSubmit={handleSubmit} className={styles.form}>
          <div className={styles.row}>
            <div className={styles.field}>
              <label htmlFor="name">Name</label>
              <input type="text" id="name" name="name" value={formData.name ?? ''} onChange={handleChange} required />
            </div>
            <div className={styles.field}>
              <label htmlFor="type">Type</label>
              <input type="text" id="type" name="type" value={formData.type ?? ''} onChange={handleChange} required />
            </div>
          </div>

          <div className={styles.row}>
            <div className={styles.field}>
              <label htmlFor="breed">Breed</label>
              <input type="text" id="breed" name="breed" value={formData.breed ?? ''} onChange={handleChange} required />
            </div>
            <div className={styles.field}>
              <label htmlFor="color">Color</label>
              <input type="text" id="color" name="color" value={formData.color ?? ''} onChange={handleChange} required />
            </div>
          </div>

          <div className={styles.row}>
            <div className={styles.field}>
              <label htmlFor="age">Age</label>
              <input type="number" id="age" name="age" value={formData.age ?? ''} onChange={handleChange} required />
            </div>
          </div>

          <div className={styles.row}>
            <div className={styles.field}>
              <label htmlFor="adoptionStatus">Adoption Status</label>
              <select id="adoptionStatus" name="adoptionStatus" value={formData.adoptionStatus ?? 'available'} onChange={handleChange}>
                <option value="available">Available</option>
                <option value="pending">Pending</option>
                <option value="approved">Approved</option>
                <option value="rejected">Rejected</option>
              </select>
            </div>
          </div>

          {['imageUrl', 'imageUrl1', 'imageUrl2'].map((img, i) => (
            <div className={styles.row} key={img}>
              <div className={styles.field}>
                <label htmlFor={img}>{i === 0 ? 'Main Image' : `Additional Image ${i}`}</label>
                <div className={styles.imageOptions}>
                  <label>
                    <input type="radio" name={`${img}_option`} checked={imageOptions[img] === 'url'} onChange={() => setImageOptions(p => ({ ...p, [img]: 'url' }))} />
                    URL
                  </label>
                  <label>
                    <input type="radio" name={`${img}_option`} checked={imageOptions[img] === 'upload'} onChange={() => setImageOptions(p => ({ ...p, [img]: 'upload' }))} />
                    Upload
                  </label>
                </div>

                {imageOptions[img] === 'url' ? (
                  <input type="text" id={img} name={img} value={formData[img] ?? ''} onChange={handleChange} />
                ) : (
                  <input type="file" id={img} name={img} accept="image/*" onChange={handleChange} />
                )}
              </div>
            </div>
          ))}

          <div className={styles.buttonRow}>
            <button type="submit" className={styles.submitButton}>{mode === 'add' ? 'Add Pet' : 'Update Pet'}</button>
            <button type="button" className={styles.backButton} onClick={() => { setMode(null); setSelectedPet(null); }}>Back</button>
          </div>
        </form>
      )}
    </div>
  );
}
