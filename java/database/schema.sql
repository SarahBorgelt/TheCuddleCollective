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
    parent_id VARCHAR(150),
    adoption_status adoption_status_enum NOT NULL,
    image_url VARCHAR(200) NOT NULL UNIQUE,
    image_url1 VARCHAR(200) UNIQUE,
    image_url2 VARCHAR(200) UNIQUE,
    CONSTRAINT PK_animal_id PRIMARY KEY (animal_id),
     CONSTRAINT FK_parent_id FOREIGN KEY (parent_id) REFERENCES parent(parent_id)
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
CREATE TABLE parent(
    parent_id SERIAL,
    first_name VARCHAR(150) NOT NULL,
    last_name VARCHAR(150) NOT NULL,
    phone_number VARCHAR(12) NOT NULL,
    street_number INT NOT NULL,
    street_name VARCHAR(150) NOT NULL,
    city_name VARCHAR(150) NOT NULL,
    state_abbreviation VARCHAR(2) NOT NULL,
    CONSTRAINT PK_parent_id PRIMARY KEY (parent_id)
 );

COMMIT TRANSACTION;

