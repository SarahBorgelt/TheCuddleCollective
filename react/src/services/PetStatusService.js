import axios from "axios";

export default{

       getAllPetsStatus(){
        return axios.get("/availablePets/status/");
    },

    getPetsByStatus(status){
        return axios.get(`${status}`);
    }
    
}