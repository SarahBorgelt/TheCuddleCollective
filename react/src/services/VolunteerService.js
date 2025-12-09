import axios from "axios";

export default{

    getAllVolunteers(){
        return axios.get("/volunteers");
},

    addNewVolunteer(volunteer_id){
        return axios.post(`volunteers/${volunteer_id}`);
},

    deleteFromVolunteer(volunteer_id){
        return axios.delete(`volunteers/${volunteer_id}`);
}
}