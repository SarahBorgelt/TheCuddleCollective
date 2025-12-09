import axios from "axios";

export default {
    
    getAllPetsType(){
        return axios.get("/availablePets/type/");
    },

    getPetsByType(type){
        return axios.get(`${type}`);
    }
}