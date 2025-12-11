import { useState, useEffect } from 'react';
import styles from './AddOrUpdatePets.module.css';
import PetService from "../../services/PetService";

export default function AddOrUpdatePets() {
    const [mode, setMode] = useState(null);
    const [formData, setFormData] = useState({
        animal_type: '',
        breed: '',
        color: '',
        age: '',
        name: '',
        adoption_status: '',
        image_url: '',
        image_url1: '',
        image_url2: '',
    });

    const [petsList, setPetsList] = useState([]);
    const [searchTerm, setSearchTerm] = useState('');
    const [selectedPet, setSelectedPet] = useState(null);
    const [imageOptions, setImageOptions] = useState({
        image_url: 'url',
        image_url1: 'url',
        image_url2: 'url'
    });
    const [successMessage, setSuccessMessage] = useState('');

    const loadPets = () => {
        PetService.getAllAvailablePets()
            .then(res => setPetsList(res.data))
            .catch(err => console.error(err));
    };

    useEffect(() => {
        if (mode === 'update') loadPets();
    }, [mode]);

    useEffect(() => {
        if (selectedPet) {
            setFormData({
                animal_type: selectedPet.animal_type || '',
                breed: selectedPet.breed || '',
                color: selectedPet.color || '',
                age: selectedPet.age != null ? selectedPet.age : '',
                name: selectedPet.name || '',
                adoption_status: selectedPet.adoption_status || '',
                image_url: selectedPet.image_url || '',
                image_url1: selectedPet.image_url1 || '',
                image_url2: selectedPet.image_url2 || '',
            });
        }
    }, [selectedPet]);

    const handleChange = e => {
        const { name, value, files } = e.target;
        if (files) {
            setFormData(prev => ({ ...prev, [name]: files[0] }));
        } else {
            setFormData(prev => ({ ...prev, [name]: value }));
        }
    };

    const handleSubmit = async e => {
        e.preventDefault();
        try {
            if (mode === 'add') {
                const addedPet = await PetService.addNewPet(formData);
                setSuccessMessage(`Pet "${addedPet.data.name}" added successfully!`);
                setFormData({
                    animal_type: '',
                    breed: '',
                    color: '',
                    age: '',
                    name: '',
                    adoption_status: '',
                    image_url: '',
                    image_url1: '',
                    image_url2: '',
                });
            } else if (mode === 'update') {
                if (!selectedPet) return alert('Select a pet to update');
                const updatedPet = await PetService.updatePet(selectedPet.animal_id, formData);
                setSuccessMessage(`Pet "${updatedPet.data.name}" updated successfully!`);
                loadPets();
            }
        } catch (err) {
            console.error(err);
            alert('Failed to save pet. Check console for details.');
        }
    };

    const handleSearch = () => {
        if (!searchTerm) {
            loadPets();
        } else {
            const filtered = petsList.filter(p =>
                p.name.toLowerCase().includes(searchTerm.toLowerCase())
            );
            setPetsList(filtered);
        }
    };

    if (!mode) {
        return (
            <div className={styles.container}>
                <h2 className={styles.title}>What would you like to do?</h2>
                <div className={styles.buttonRow}>
                    <button className={styles.actionButton} onClick={() => setMode('add')}>Add Pet</button>
                    <button className={styles.actionButton} onClick={() => setMode('update')}>Update Pet</button>
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
                    <div className={styles.updateHeader}>
                        <div className={styles.searchRow}>
                            <input
                                type="text"
                                placeholder="Search pets..."
                                value={searchTerm}
                                onChange={e => setSearchTerm(e.target.value)}
                                className={styles.searchInput}
                            />
                            <button className={styles.searchButton} onClick={handleSearch}>Search</button>
                            <button className={styles.backButton} onClick={() => {
                                setMode(null);
                                setSelectedPet(null);
                                setSearchTerm('');
                                loadPets();
                            }}>
                                Back
                            </button>
                        </div>
                    </div>
                    <div className={styles.petsList}>
                        {petsList.map(pet => (
                            <div
                                key={pet.animal_id}
                                className={`${styles.petCard} ${selectedPet?.animal_id === pet.animal_id ? styles.selectedPet : ''}`}
                                onClick={() => setSelectedPet(pet)}
                            >
                                <img src={pet.image_url} alt={pet.name} className={styles.petImage} />
                                <div>{pet.name} ({pet.animal_type})</div>
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
                            <label>Name</label>
                            <input type="text" name="name" value={formData.name} onChange={handleChange} required />
                        </div>
                        <div className={styles.field}>
                            <label>Type</label>
                            <input type="text" name="animal_type" value={formData.animal_type} onChange={handleChange} required />
                        </div>
                    </div>

                    <div className={styles.row}>
                        <div className={styles.field}>
                            <label>Breed</label>
                            <input type="text" name="breed" value={formData.breed} onChange={handleChange} required />
                        </div>
                        <div className={styles.field}>
                            <label>Color</label>
                            <input type="text" name="color" value={formData.color} onChange={handleChange} required />
                        </div>
                    </div>

                    <div className={styles.row}>
                        <div className={styles.field}>
                            <label>Age</label>
                            <input type="number" name="age" value={formData.age ?? ''} onChange={handleChange} required />
                        </div>
                    </div>

                    <div className={styles.row}>
                        <div className={styles.field}>
                            <label>Adoption Status</label>
                            <select name="adoption_status" value={formData.adoption_status} onChange={handleChange}>
                                <option value="available">Available</option>
                                <option value="pending">Pending</option>
                                <option value="approved">Approved</option>
                                <option value="rejected">Rejected</option>
                            </select>
                        </div>
                    </div>

                    {['image_url', 'image_url1', 'image_url2'].map((imgField, idx) => (
                        <div className={styles.row} key={imgField}>
                            <div className={styles.field}>
                                <label>{idx === 0 ? 'Main Image' : `Additional Image ${idx}`}</label>
                                <div className={styles.imageOptions}>
                                    <label>
                                        <input
                                            type="radio"
                                            name={`${imgField}_option`}
                                            checked={imageOptions[imgField] === 'url'}
                                            onChange={() => setImageOptions(prev => ({ ...prev, [imgField]: 'url' }))}
                                        /> URL
                                    </label>
                                    <label>
                                        <input
                                            type="radio"
                                            name={`${imgField}_option`}
                                            checked={imageOptions[imgField] === 'upload'}
                                            onChange={() => setImageOptions(prev => ({ ...prev, [imgField]: 'upload' }))}
                                        /> Upload
                                    </label>
                                </div>
                                {imageOptions[imgField] === 'url' ? (
                                    <input type="text" name={imgField} value={formData[imgField]} onChange={handleChange} />
                                ) : (
                                    <input type="file" name={imgField} onChange={handleChange} />
                                )}
                            </div>
                        </div>
                    ))}

                    <div className={styles.buttonRow}>
                        <button type="submit" className={styles.submitButton}>
                            {mode === 'add' ? 'Add Pet' : 'Update Pet'}
                        </button>
                        <button type="button" className={styles.backButton} onClick={() => { setMode(null); setSelectedPet(null); }}>
                            Back
                        </button>
                    </div>
                </form>
            )}
        </div>
    );
}
