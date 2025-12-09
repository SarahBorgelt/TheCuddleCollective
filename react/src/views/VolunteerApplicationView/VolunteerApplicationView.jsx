import { useState } from "react";
import VolunteerService from "../../services/VolunteerService";

export default function VolunteerApplicationForm(){
    const [firstName, setFirstName] = useState("");
    const [lastName, setLastName] = useState("");
    const [email, setEmail] = useState("");

    const handleSubmit = async (e) => {
        e.preventDefault();

        const applicant = {firstName, lastName, email};

        try{
            await VolunteerService.submitApplication(applicant);
            alert("Application Submitted! We will contact you with further info.");
        } catch (err) {
            alert("Error submitting applicatioN");
            console.error(err)
        }
    };
return(
    <div>
    <h1>Volunteer Application</h1>
        <form onSubmit={handleSubmit}>
            <input type="text" placeholder="First Name" value={firstName} name="firstName" onChange={e => setFirstName(e.target.value)}></input><br/>
            
            <input type="text" placeholder="Last Name" value={lastName} name="lastName" onChange={e => setLastName(e.target.value)}></input><br/>
            
            <input type="email" placeholder="Email" value={email} name="email" onChange={e => setEmail(e.target.value)}></input><br/>
            
            <button type="submit">Submit</button>
    </form>
    </div>
)

}