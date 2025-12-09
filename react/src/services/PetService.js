import axios from "axios";

export default{

    getAllAvailablePets(){
        return axios.get("/availablePets");
    },
    
    
}