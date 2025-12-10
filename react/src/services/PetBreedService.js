import axios from "axios";

export default {
    
      getAllPetsBreed(){
        return axios.get("/availablePets/breed/");
    },

    getPetsByBreed(breed){
        return axios.get(`/${breed}`);
    }
}