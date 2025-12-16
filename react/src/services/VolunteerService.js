import axios from "axios";

export default{

    getAllVolunteers(){
        const token = localStorage.getItem('token');
        return axios.get("/volunteer/directory", {
            headers: {Authorization: `Bearer ${token}`}
        });
},

    addNewVolunteer(volunteer){
        const token = localStorage.getItem('token');
        return axios.post("/volunteers", volunteer, {
            headers: {Authorization: `Bearer ${token}`}
        });
},

    deleteFromVolunteer(volunteer_id){
        const token = localStorage.getItem('token');
        return axios.delete(`/volunteers/${volunteer_id}`, {
            headers: {Authorization: `Bearer ${token}`}
        });
},

    submitApplication(applicant) {
        return axios.post("/volunteer/apply", null, {
            params: {
                firstName: applicant.firstName,
                lastName: applicant.lastName,
                email: applicant.email,
                phoneNumber: applicant.phoneNumber
            }
        });
},

    updateVolunteer(volunteer){
        const token = localStorage.getItem('token');
        return axios.put(`/volunteers/${volunteer.volunteerId}`, volunteer, {
            headers: {Authorization: `Bearer ${token}`}
        });
    }
}