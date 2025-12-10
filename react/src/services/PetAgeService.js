import axios from "axios";

export default{
    getAllPetsAge(){
        return axios.get("/availablePets/petAge/");
    },

    getPetsByAge(age){
        return axios.get(`${age}`);
    }
}