import axios from "axios";

export default{
        getAllPetsColor(){
        return axios.get("/availablePets/color/");
    },

    getPetsByColor(color){
        return axios.get(`${color}`);
    }
}