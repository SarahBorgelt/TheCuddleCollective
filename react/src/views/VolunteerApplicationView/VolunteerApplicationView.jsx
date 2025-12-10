import { useState } from "react";
import VolunteerService from "../../services/VolunteerService";

export default function VolunteerApplicationForm(){
    const [firstName, setFirstName] = useState("");
    const [lastName, setLastName] = useState("");
    const [email, setEmail] = useState("");
    const [phoneNumber, setPhoneNumber] = useState("");

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
            <p>First Name:</p>
            <input type="text" placeholder="First Name" value={firstName} name="firstName" onChange={e => setFirstName(e.target.value)}></input><br/>
            <p>Last Name:</p>
            <input type="text" placeholder="Last Name" value={lastName} name="lastName" onChange={e => setLastName(e.target.value)}></input><br/>
            <p>Email:</p>
            <input type="email" placeholder="Email" value={email} name="email" onChange={e => setEmail(e.target.value)}></input><br/>
            <p>Phone Number:</p>
            <input type="text" placeholder="Phone Number" value={phoneNumber} name="phoneNumber" onChange={e => setPhoneNumber(e.target.value)}></input>
            <button type="submit">Submit</button>
    </form>
    </div>
)

}