# 🐾 The Cuddle Collective 🐾

![The homepage of The Cuddle Collective](react/src/assets/PetShelterHomePage.png)

**The Cuddle Collective** is a collaborative full-stack web application designed to help animal shelters manage pets, volunteers, and adoptions efficiently. Built by a team of four, the application allows shelter staff, volunteers, and the public to interact with pet and volunteer information in a user-friendly and secure way.

<details>

<summary>Project Overview</summary>

## 📋 Project Overview

The application provides the following functionality:

- Display pets available for adoption

- Track adopted pets

- Add and update pet information

- Manage pet parent details securely

- Volunteer signup and applications

- Volunteer directory access

- Admin functionalities for approving/rejecting volunteer applications and locating referral codes

### Access Control:

- Logged-in users and admins can manage pets, pet parents, and volunteer information
- Anonymous users can browse available and adopted pets

</details>

---

<details>

<summary>Purpose and Importance</summary>

## 🎯 Purpose and Importance
**The Cuddle Collective** was built to address the challenges animal shelters face in managing pets, volunteers, and adoptions. Many shelters struggle with manual record-keeping, scattered volunteer information, and inefficient tracking of pet adoptions.

This application provides a centralized, digital platform to:

- Ensure pets are accurately tracked from arrival to adoption

- Streamline volunteer management, making it easier to sign up, apply, and connect with shelter staff

- Improve communication between admins, volunteers, and pet parents

- Increase visibility of available and adopted pets to the public, helping more animals find homes

By combining efficient backend management with a friendly, intuitive frontend, the system not only saves time for shelter staff but also enhances the adoption experience for pet seekers and volunteers, making a tangible difference in the lives of both animals and the community.

</details>

---

<details>

<summary>Features</summary>

## ✨ Features ✨
### 🐕 Pet Management

- View all pets available for adoption

- Track adopted pets
 
- Admins and logged-in volunteers can add and update pet records

### 👪 Pet Parents 

- Add and update pet parent information

- Accessible to logged-in volunteers and admins

### 🙋‍♂️🙋‍♀️ Volunteer Management 

- Volunteer signup and application submission

- Volunteer directory access

- Admins can approve/reject applications and view referral codes

### 🔑 User Access 

- Anonymous users can view available and adopted pets

- Logged-in volunteers have access to pet management and volunteer directory

- Admins have full control over all features

</details>

--- 

<details>

<summary>Tech Stack</summary>

## 💻 Tech Stack 

**Backend:** Java, Spring Boot, JDBC, Jakarta Persistence

**Frontend:** React, JavaScript, HTML, CSS

**Database:** PostgreSQL

**Testing:** JUnit

**Version Control & Collaboration:** Git, GitLabs, GitHub

</details>

---

<details>

<summary>Starting Code</summary>

## 🚀 Starting Code 
The below section contains our starting point and instructions from the Tech Elevator education team. From this starting point, we built an application that can successfully add or update pets, showcase available pets, display adopted pets, enable users to volunteer, upgrade accounts from user to volunteer using a referral code, view the volunteer directory, etc.

### Final Capstone
​
This directory contains all of the starter projects for the final capstone.
​
Each project contains instructions that provides information about the starting code and explains how to get started with the final capstone project.

![Starter Code Screenshot](react/src/assets/StarterCode.png)

</details>

---

<details>

<summary>Setup Instructions</summary>

## Setup Instructions
### 1. Clone the repository
     git clone https://github.com/SarahBorgelt/PetShelterManager.git

### 2. Configure PostgreSQL
In the application.properties (src/main/resources/application.properties) file,
update the datasource username and password to your PostgreSQL credentials.

### 3. Set Up the Databases
Within the Java folder, there is database folder that contains a create.sh file. Run the create.sh file to automatically generate the necessary databases to run the program.

### 4. Start the Application in IntelliJ
Open the Java folder in IntelliJ and run the application.java file to start the backend.

### 5. Start the Application in React
Open the react file in Visual Studio Code and enter the below command in terminal:
     
     npm install

Once you've run npm install, you can then run this command in terminal to open the project:

     npm run dev

After the command is run, you will see a url link to localhost. Copy the url to your preferred browser to open the application.

</details>

---

<details>

<summary>Team Contributions</summary>

## 👩‍💻👨‍💻 Team Contributions 
This project was completed collaboratively by four software developers. To learn more or connect with us, feel free to check out our profiles below:

| Name | Contribution | LinkedIn | GitHub |
|------|----------|--------|--------|
| **Sarah Borgelt** | Header, footer, and pet card components in React for available pets, available pets controllers, Pet Parents using JPA and React, styling for volunteer application page, README.md generation, contributed to Volunteer Directory view in React | [LinkedIn](https://www.linkedin.com/in/sarahnicole1) | [GitHub](https://github.com/SarahBorgelt) |
| **Jenna Mastrodonato** | Created unit testing for all of the backend, add or update pets models and DAO using JDBC template|[LinkedIn](https://www.linkedin.com/in/jemastro/) | [GitHub](https://github.com/jemastro) |
| **Max Logue** | Created the adopted pets view in React|[LinkedIn](https://www.linkedin.com/in/maxlogue/) | [GitHub](https://github.com/MaxL64) |
| **Chris Bussey** | Created the Volunteer Applications and Admin Volunteer Applications for Approval/Denial in React, completed the backend for all volunteer functionality using JDBC template following the MVC pattern, contributed to Volunteer Directory view in React|[LinkedIn](https://www.linkedin.com/in/chrisbussey-developer/) | [GitHub](https://github.com/ChrisB-us) |

</details>

---

<details>

<summary>Screenshots</summary>

## 📷 Screenshots
![The add a new pet page](react/src/assets/AddNewPet.png)

![The update existing pet view. Click on a card to open the menu below!](react/src/assets/UpdatePet.png)

![The available pets page shows the available pets for adoption!](react/src/assets/AvailablePets.png)

![The Admin view of the volunteer directory](react/src/assets/AdminVolunteerDirectory.png)

![The Admin view of the volunteer applications](react/src/assets/AdminVolunteerView.png)

![Are you interested in volunteering? Fill out the application to learn more!](react/src/assets/VolunteerApplication.png)

</details>