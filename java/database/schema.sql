BEGIN TRANSACTION;

DROP TABLE IF EXISTS users;

CREATE TABLE users (
	user_id SERIAL,
	username varchar(50) NOT NULL UNIQUE,
	password_hash varchar(200) NOT NULL,
	role varchar(50) NOT NULL,
	first_login boolean NOT NULL DEFAULT TRUE,
	CONSTRAINT PK_user PRIMARY KEY (user_id)
);

--Create table to store volunteers
CREATE TABLE volunteers (
    volunteer_id INT PRIMARY KEY REFERENCES users(user_id),
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    email VARCHAR(150) NOT NULL UNIQUE
);

--Create enum to only pull from available values
CREATE TYPE adoption_status_enum AS ENUM ('pending', 'approved', 'rejected', 'available');

--Create table for available pets
CREATE TABLE available_pets (
    animal_id SERIAL,
    animal_type VARCHAR(150) NOT NULL,
    breed VARCHAR(200) NOT NULL,
    color VARCHAR(150) NOT NULL,
    age INT NOT NULL,
    name VARCHAR(150) NOT NULL,
    adoption_status adoption_status_enum NOT NULL,
    image_url VARCHAR(200) NOT NULL UNIQUE,
    image_url1 VARCHAR(200) UNIQUE,
    image_url2 VARCHAR(200) UNIQUE,
    CONSTRAINT PK_animal_id PRIMARY KEY (animal_id)
    );

--Create enum to only pull from available statuses
CREATE TYPE volunteer_application_status_enum AS ENUM ('pending', 'approved', 'rejected');

--Create table for pending volunteer applications
CREATE TABLE volunteer_applications (
    volunteer_application_id SERIAL PRIMARY KEY,
    volunteer_id INT REFERENCES volunteers(volunteer_id),
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    email VARCHAR(150) NOT NULL UNIQUE,
    volunteer_application_status volunteer_application_status_enum NOT NULL
);

--Create table for adopted pets
CREATE TABLE adopted_pets(
    adoption_id SERIAL,
    pet_name VARCHAR(150) NOT NULL,
    animal_id INT NOT NULL,
    adoption_status adoption_status_enum NOT NULL,
    pet_parent_first_name VARCHAR(150) NOT NULL,
    pet_parent_last_name VARCHAR(150) NOT NULL,
    pet_parent_phone_number VARCHAR(12) NOT NULL,
    pet_parent_street_number INT NOT NULL,
    pet_parent_street_name VARCHAR(150) NOT NULL,
    pet_parent_city_name VARCHAR(150) NOT NULL,
    pet_parent_state_abbreviation VARCHAR(2) NOT NULL,
    CONSTRAINT PK_adoption_id PRIMARY KEY (adoption_id),
    CONSTRAINT FK_animal_id_adopted_animal_id FOREIGN KEY (animal_id) REFERENCES available_pets(animal_id)
 );

COMMIT TRANSACTION;
