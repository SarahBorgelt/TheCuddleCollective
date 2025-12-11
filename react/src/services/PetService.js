import axios from "axios";

const API_URL = "http://localhost:9000/availablePets";

const PetService = {

    getAllAvailablePets() {
        return axios.get(API_URL);
    },

    addNewPet(pet) {
        return axios.post(API_URL, pet);
    },

    updatePet(id, pet) {
        return axios.put(`${API_URL}/${id}`, pet);
    },

    deleteFromPets(id) {
        return axios.delete(`${API_URL}/${id}`);
    }
};

export default PetService;
