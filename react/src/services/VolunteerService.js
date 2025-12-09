import axios from "axios";

export default{

    getAllVolunteers(){
        return axios.get("/volunteer/directory");
},

    addNewVolunteer(volunteer_id){
        return axios.post(`volunteers/${volunteer_id}`);
},

    deleteFromVolunteer(volunteer_id){
        return axios.delete(`volunteers/${volunteer_id}`);
},

    submitApplication(applicant) {
        return axios.post("/volunteer/submit", null, {
            params: {
                firstName: applicant.firstName,
                lastName: applicant.lastName,
                email: applicant.email
            }
        });
}
}