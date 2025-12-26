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

  const loadPets = useCallback(() => {
    PetService.getAllPetsForUpdates()
      .then((res) => setPetsList(res.data.map(normalizePet)))
      .catch((err) => console.error('Error fetching pets:', err));
  }, []);

  useEffect(() => {
    if (mode === 'update') loadPets();
  }, [mode, loadPets]);

  useEffect(() => {
    if (selectedPet && mode === 'update') {
      setFormData({
        animalId: selectedPet.animalId,
        type: selectedPet.type || '',
        breed: selectedPet.breed || '',
        color: selectedPet.color || '',
        age: selectedPet.age ?? '',
        name: selectedPet.name || '',
        parentId: selectedPet.parentId ?? null,
        adoptionStatus: selectedPet.adoptionStatus || '',
        imageUrl: selectedPet.imageUrl || '',
        imageUrl1: selectedPet.imageUrl1 || '',
        imageUrl2: selectedPet.imageUrl2 || '',
      });
    } else if(mode == 'add'){
        setFormData({
        type: '',
        breed: '',
        color: '',
        age: '',
        name: '',
        adoptionStatus: 'Available',
        imageUrl: '',
        imageUrl1: '',
        imageUrl2: '',
        animalId: null,
        parentId: null,
      });
      setSelectedPet(null);
    }
  }, [selectedPet, mode]);

  const handleChange = (e) => {
    const { name, value, files } = e.target;
    if (files && files[0]) {
      setFormData((prev) => ({ ...prev, [name]: files[0] }));
    } else {
      setFormData((prev) => ({ ...prev, [name]: value }));
    }
  };

  const mapToSnakeCase = (data) => ({
    animal_id: data.animalId,
    animal_type: data.type,
    animal_breed: data.breed,
    animal_color: data.color,
    animal_age: Number(data.age),
    animal_name: data.name,
    parent_id: data.parentId,
    adoption_status: data.adoptionStatus.toLowerCase(),
    image_url: data.imageUrl,
    image_url1: data.imageUrl1 || null,
    image_url2: data.imageUrl2 || null,
  });

  const handleSubmit = async (e) => {
    e.preventDefault();

    const hasFile =
      imageOptions.imageUrl === 'upload' ||
      imageOptions.imageUrl1 === 'upload' ||
      imageOptions.imageUrl2 === 'upload';

    let payload;
    if (hasFile) {
      payload = new FormData();
      const mapped = mapToSnakeCase(formData);
      Object.keys(mapped).forEach((key) => payload.append(key, mapped[key]));
    } else {
      payload = mapToSnakeCase(formData);
    }

    try {
      if (mode === 'add') {
        const res = await PetService.addNewPet(payload);
        setSuccessMessage(`Pet "${res.data.animal_name}" added successfully!`);
      } else if (mode === 'update') {
        if (!selectedPet) return alert('Select a pet to update');
        const res = await PetService.updatePet(selectedPet.animalId, payload);
        setSuccessMessage(`Pet "${res.data.animal_name}" updated successfully!`);
        loadPets();
      }

      setTimeout(() => setSuccessMessage(''), 3 * 1000);

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

useEffect (() => {
  if (!searchTerm){
    loadPets();
    return;
  } 
  const lowerTerm = searchTerm.toLowerCase();

  setPetsList((prev) =>
    prev.filter((p) =>
      (p.name && p.name.toLowerCase().includes(lowerTerm)) ||
      (p.type && p.type.toLowerCase().includes(lowerTerm)) ||
      (p.breed && p.breed.toLowerCase().includes(lowerTerm)) ||
      (p.color && p.color.toLowerCase().includes(lowerTerm)) ||
      (p.age && p.age.toString().includes(lowerTerm)) ||
      (p.adoptionStatus && p.adoptionStatus.toLowerCase().includes(lowerTerm))
    )
  );

}, [searchTerm, loadPets]);

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
            <button className={styles.searchButton}>Search</button>
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
              <label className={styles.htmlFor} htmlFor="name">Name</label>
              <input type="text" id="name" name="name" value={formData.name} onChange={handleChange} required />
            </div>
            <div className={styles.field}>
              <label className={styles.htmlFor} htmlFor="type">Type</label>
              <input type="text" id="type" name="type" value={formData.type} onChange={handleChange} required />
            </div>
          </div>

          <div className={styles.row}>
            <div className={styles.field}>
              <label className={styles.htmlFor} htmlFor="breed">Breed</label>
              <input type="text" id="breed" name="breed" value={formData.breed} onChange={handleChange} required />
            </div>
            <div className={styles.field}>
              <label className={styles.htmlFor} htmlFor="color">Color</label>
              <input type="text" id="color" name="color" value={formData.color} onChange={handleChange} required />
            </div>
          </div>

          <div className={styles.row}>
            <div className={styles.field}>
              <label className={styles.htmlFor} htmlFor="age">Age</label>
              <input type="number" id="age" name="age" value={formData.age ?? ''} onChange={handleChange} required />
            </div>
          </div>

          <div className={styles.row}>
            <div className={styles.field}>
              <label className={styles.htmlFor} htmlFor="adoptionStatus">Adoption Status</label>
              <select id="adoptionStatus" name="adoptionStatus" value={formData.adoptionStatus} onChange={handleChange}>
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
                <label className={styles.htmlFor} htmlFor={img}>{i === 0 ? 'Main Image' : `Additional Image ${i}`}</label>
                <div className={styles.imageOptions}>
                  <p>(Please enter the image url below)</p>
  
                </div>

                {imageOptions[img] === 'url' ? (
                  <input type="text" id={img} name={img} value={formData[img]} onChange={handleChange} />
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
