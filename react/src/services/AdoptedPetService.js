import axios from "axios";

const ADOPTED_API_URL = "http://localhost:9000/adoptedPets";

export default{
    getAllAdoptedPets(){
        return axios.get(ADOPTED_API_URL);
    },

/* 
    In theory, we won't need to update or delete a pet that has been adopted

    We shouldn't need to delete from the database but only display a limited amount.
    We display the nth (ie 3) most recent adoptions and keep the rest in the database.
*/

    addNewAdoptedPet(){
        return axios.get(`${ADOPTED_API_URL}`); 
    }

}