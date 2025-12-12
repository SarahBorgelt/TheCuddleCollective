import axios from "axios";

const API_URL = "http://localhost:9000/availablePets";

const PetService = {
    getAllAvailablePets() {
        return axios.get(API_URL);
    },

    addNewPet(pet) {
        const token = localStorage.getItem('token');
        return axios.post(API_URL, pet, {
            headers: { Authorization: `Bearer ${token}` }
        });
    },

    updatePet(id, pet) {
        const token = localStorage.getItem('token');
        return axios.put(`${API_URL}/${id}`, pet, {
            headers: { Authorization: `Bearer ${token}` }
        });
    },

    deleteFromPets(id) {
        const token = localStorage.getItem('token');
        return axios.delete(`${API_URL}/${id}`, {
            headers: { Authorization: `Bearer ${token}` }
        });
    }
};

export default PetService;
