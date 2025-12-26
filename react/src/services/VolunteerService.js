import axios from "axios";

const VolunteerService = {

    getAllVolunteers() {
    return axios.get("/volunteer/directory");
  },

  deleteFromVolunteer(volunteerId) {
    return axios.delete(`/volunteers/${volunteerId}`);
  },

  updateVolunteer(id, volunteerData) {
    return axios.put(`/volunteers/${id}`, volunteerData);
  },

  submitApplication(applicant) {
    return axios.post("/volunteer/apply", applicant);
  },

  getApprovedApplicationsWithCodes() {
    return axios.get("/admin/applications/approved");
  }
};

export default VolunteerService;