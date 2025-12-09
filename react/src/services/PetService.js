import axios from "axios";

export default{

    getAllAvailablePets(){
        return axios.get("/availablePets");
    },
    
    addNewPet(id){
        return axios.post(`/availablePets/${id}`);
    },

    deleteFromPets(id){
        return axios.delete(`/availablePets/${id}`);
    }
}