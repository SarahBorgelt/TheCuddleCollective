import axios from "axios";

export default{

    getAllVolunteers(){
        return axios.get("/volunteer/directory");
},

    addNewVolunteer(volunteer){
        return axios.post("/volunteers", volunteer);
},

    deleteFromVolunteer(volunteer_id){
        return axios.delete(`/volunteers/${volunteer_id}`);
},

    submitApplication(applicant) {
        return axios.post("/volunteer/apply", applicant);
}
}