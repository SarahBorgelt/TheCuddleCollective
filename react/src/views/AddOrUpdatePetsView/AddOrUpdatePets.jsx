import { useState, useEffect, useCallback } from 'react';
import styles from './AddOrUpdatePets.module.css';
import axios from 'axios';

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

  const token = localStorage.getItem('token');

  const axiosWithAuth = axios.create({
    baseURL: 'http://localhost:9000',
    headers: { Authorization: `Bearer ${token}` },
  });

  // FETCH PETS
  const loadPets = useCallback(() => {
    axiosWithAuth
      .get('/availablePets')
      .then((res) => {
        const normalized = res.data.map((p) => ({
          animalId: p.animal_id,
          type: p.animal_type,
          breed: p.animal_breed,
          color: p.animal_color,
          age: p.animal_age,
          name: p.animal_name,
          adoptionStatus: p.adoption_status,
          imageUrl: p.image_url,
          imageUrl1: p.image_url1,
          imageUrl2: p.image_url2,
        }));
        setPetsList(normalized);
      })
      .catch((err) => console.error('Error fetching pets:', err));
  }, [axiosWithAuth]);

  useEffect(() => {
    if (mode === 'update') loadPets();
  }, [mode, loadPets]);

  // Pre-fill form when pet is selected
  useEffect(() => {
    if (selectedPet) {
      setFormData({
        type: selectedPet.type || '',
        breed: selectedPet.breed || '',
        color: selectedPet.color || '',
        age: selectedPet.age ?? '',
        name: selectedPet.name || '',
        adoptionStatus: selectedPet.adoptionStatus || 'available',
        imageUrl: selectedPet.imageUrl || '',
        imageUrl1: selectedPet.imageUrl1 || '',
        imageUrl2: selectedPet.imageUrl2 || '',
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

  // Handle form submission
  const handleSubmit = async (e) => {
    e.preventDefault();
    if (!token) return alert('You must be logged in');

    const hasFile =
      imageOptions.imageUrl === 'upload' ||
      imageOptions.imageUrl1 === 'upload' ||
      imageOptions.imageUrl2 === 'upload';

    let payload;
    let headers = { Authorization: `Bearer ${token}` };

    if (hasFile) {
      payload = new FormData();
      Object.keys(formData).forEach((key) => {
        payload.append(key, formData[key]);
      });
      headers['Content-Type'] = 'multipart/form-data';
    } else {
      payload = { ...formData };
    }

    try {
      if (mode === 'add') {
        const res = await axiosWithAuth.post('/availablePets', payload, { headers });
        setSuccessMessage(`Pet "${res.data.name}" added successfully!`);
      } else if (mode === 'update') {
        if (!selectedPet) return alert('Select a pet to update');
        const res = await axiosWithAuth.put(`/availablePets/${selectedPet.animalId}`, payload, { headers });
        setSuccessMessage(`Pet "${res.data.name}" updated successfully!`);
        loadPets();
      }

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
      });
      setSelectedPet(null);
    } catch (err) {
      console.error('Failed to save pet:', err);
      alert('Failed to save pet. Check console for details.');
    }
  };

  // Search pets
  const handleSearch = () => {
    if (!searchTerm) return loadPets();
    setPetsList((prev) =>
      prev.filter((p) =>
        p.name.toLowerCase().includes(searchTerm.toLowerCase())
      )
    );
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
            <button onClick={() => { setMode(null); setSelectedPet(null); setSearchTerm(''); }} className={styles.backButton}>Back</button>
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
              <input type="text" id="name" name="name" value={formData.name} onChange={handleChange} required />
            </div>
            <div className={styles.field}>
              <label htmlFor="type">Type</label>
              <input type="text" id="type" name="type" value={formData.type} onChange={handleChange} required />
            </div>
          </div>

          <div className={styles.row}>
            <div className={styles.field}>
              <label htmlFor="breed">Breed</label>
              <input type="text" id="breed" name="breed" value={formData.breed} onChange={handleChange} required />
            </div>
            <div className={styles.field}>
              <label htmlFor="color">Color</label>
              <input type="text" id="color" name="color" value={formData.color} onChange={handleChange} required />
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
