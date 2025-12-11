package com.techelevator.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.techelevator.exception.DaoException;
import com.techelevator.model.AvailablePet;
import com.techelevator.model.User;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class JdbcAvailablePetDao implements AvailablePetDao {

    private final JdbcTemplate jdbcTemplate;

    public JdbcAvailablePetDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<AvailablePet> getAvailablePetsByBreed(String breed) {
        List<AvailablePet> petsByBreed = new ArrayList<>();
        String sql = "SELECT animal_id, animal_type, breed, color, age, name, " +
                "adoption_status, image_url, image_url1, image_url2 FROM available_pets " +
                "where breed = ? AND adoption_status = 'available'";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, breed);
        while (results.next()) {
            AvailablePet pet = mapRowToAvailablePet(results);
            petsByBreed.add(pet);
        }
        return petsByBreed;
    }


    @Override
    public List<AvailablePet> getAvailablePetsByColor(String color) {
        List<AvailablePet> petsByColor = new ArrayList<>();
        String sql = "SELECT animal_id, animal_type, breed, color, age, name, " +
                "adoption_status, image_url, image_url1, image_url2 FROM available_pets " +
                "where color = ? AND adoption_status = 'available'";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, color);
        while (results.next()) {
            AvailablePet pet = mapRowToAvailablePet(results);
            petsByColor.add(pet);
        }
        return petsByColor;
    }

    @Override
    public List<AvailablePet> getAvailablePetsByAge(Integer age) {
        List<AvailablePet> petsByAge = new ArrayList<>();
        String sql = "SELECT animal_id, animal_type, breed, color, age, name, " +
                "adoption_status, image_url, image_url1, image_url2 FROM available_pets " +
                "where age = ? AND adoption_status = 'available'";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, age);
        while (results.next()) {
            AvailablePet pet = mapRowToAvailablePet(results);
            petsByAge.add(pet);
        }
        return petsByAge;
    }

    @Override
    public List<AvailablePet> getAvailablePetsByType(String type) {
        List<AvailablePet> petsByType = new ArrayList<>();
        String sql = "SELECT animal_id, animal_type, breed, color, age, name, " +
                "adoption_status, image_url, image_url1, image_url2 FROM available_pets " +
                "where animal_type = ? AND adoption_status = 'available'";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, type);
        while (results.next()) {
            AvailablePet pet = mapRowToAvailablePet(results);
            petsByType.add(pet);
        }
        return petsByType;
    }


    @Override
    public List<AvailablePet> getAvailablePetsByAdoptionStatus(String adoptionStatus) {
        List<AvailablePet> petsByStatus = new ArrayList<>();
        String sql = "SELECT animal_id, animal_type, breed, color, age, name, " +
                "adoption_status, image_url, image_url1, image_url2 FROM available_pets " +
                "where adoption_status = ?";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, adoptionStatus);
        while (results.next()) {
            AvailablePet pet = mapRowToAvailablePet(results);
            petsByStatus.add(pet);
        }
        return petsByStatus;
    }

    //TODO add pet by id to controller
    @Override
    public AvailablePet getPetById(long petId) {
        AvailablePet petById = null;
        String sql = "SELECT animal_id, animal_type, breed, color, age, name, " +
                "adoption_status, image_url, image_url1, image_url2 " +
                "FROM available_pets " +
                "WHERE animal_id = ?;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, petId);
        if (results.next()) {
            petById = mapRowToAvailablePet(results);
        }
        return petById;
    }


    @Override
    public AvailablePet addPet(AvailablePet pet) {
        AvailablePet returnedPet = new AvailablePet();
        String sql = "INSERT INTO available_pets (animal_type, breed, color, age, name, " +
                "adoption_status, image_url, image_url1, image_url2) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?) " +
                "RETURNING animal_id";
        try {
            long newAnimalId = jdbcTemplate.queryForObject(sql, Long.class, pet.getAnimalType(), pet.getAnimalBreed(),
                    pet.getAnimalColor(), pet.getAnimalAge(), pet.getAnimalName(), pet.getAdoptionStatus(),
                    pet.getImageUrl(), pet.getImageUrl1(), pet.getImageUrl2());
            returnedPet = getPetById(newAnimalId);
            pet.setAnimalId(newAnimalId);
            returnedPet = pet;
        } catch (Exception e) {
            throw new DaoException("Cannot add pet.", e);
        }
        return returnedPet;
    }


    @Override
    public AvailablePet updatePet(AvailablePet pet) {
        String sql = "UPDATE available_pets SET animal_type = ?, breed = ?, color = ?, age = ?, " +
                "name = ?, adoption_status = ?, image_url = ?, image_url1 = ?, image_url2 = ? " +
                "WHERE animal_id = ?";
        try {
            int rows = jdbcTemplate.update(sql,
                    pet.getAnimalType(),
                    pet.getAnimalBreed(),
                    pet.getAnimalColor(),
                    pet.getAnimalAge(),
                    pet.getAnimalName(),
                    pet.getAdoptionStatus(),
                    pet.getImageUrl(),
                    pet.getImageUrl1(),
                    pet.getImageUrl2(),
                    pet.getAnimalId());
            if (rows == 0) {
                throw new DaoException("Couldn't update pet with id " + pet.getAnimalId());
            }
            return getPetById(pet.getAnimalId());
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Can't update the pet with the given data", e);
        } catch (Exception e) {
            throw new DaoException("Something went wrong updating the pet.", e);
        }


        // TODO: should we return the updated pet? or keep this void?
    }

    // TODO How do we add pets from available to adopted table? Use insert statement to add
    // pet to adopted table and delete statement to remove from current table


    @Override
    public List<AvailablePet> getAllPets() {
        List<AvailablePet> petList = new ArrayList<>();
        String sql = "SELECT animal_id, animal_type, breed, color, age, " +
                "name, adoption_status, image_url, image_url1, image_url2 FROM available_pets WHERE adoption_status = 'available'";
        SqlRowSet result = jdbcTemplate.queryForRowSet(sql);
        while (result.next()) {
            AvailablePet pet = mapRowToAvailablePet(result);
            petList.add(pet);
        }
        return petList;
    }

<<<<<<< java/src/main/java/com/techelevator/dao/JdbcAvailablePetDao.java
    @Override
    public List<AvailablePet> getAllAdoptedPets() {
        List<AvailablePet> adoptedPets = new ArrayList<>();
        String sql = "SELECT animal_id, animal_type, breed, color, age, " +
                "name, image_url, image_url1, image_url2 FROM available_pets where adoption_status = 'approved'";
        SqlRowSet result = jdbcTemplate.queryForRowSet(sql);
        while(result.next()){
            AvailablePet pet = mapRowToAvailablePet(result);
            adoptedPets.add(pet);
        }
        return adoptedPets;

            public void updatePetToAdopted(AvailablePet pet, int parent_id) {
        AvailablePet updatedPet = new AvailablePet();
        String sql = "UPDATE available_pets SET animal_type = ?, breed = ?, color = ?, age = ?," +
                " name = ?, adoption_status = ?, image_url = ?, image_url1 = ?, image_url2 = ?, parent_id = ?" +
                "WHERE animal_id = ?;";
        try{
            int numberOfRows = jdbcTemplate.update(sql, pet.getAnimalType(), pet.getAnimalBreed(),
                    pet.getAnimalColor(), pet.getAnimalAge(), pet.getAnimalName(), pet.getAdoptionStatus(),
                    pet.getImageUrl(), pet.getImageUrl1(), pet.getImageUrl2(), parent_id, pet.getAnimalId());
            if (numberOfRows==0){
                throw new DaoException("Couldn't update this pet!");
            } else {
                updatedPet = getPetById(pet.getAnimalId());
            }
        } catch(DataIntegrityViolationException e){
            throw new DaoException("Can't update the pet with the given data", e);
        }catch(Exception e){
            throw new DaoException("Something went wrong updating the pet.",e);
        }

=======
@Override
    public void updatePetToAdopted(AvailablePet pet, int parent_id) {
        AvailablePet updatedPet = new AvailablePet();
        String sql = "UPDATE available_pets SET animal_type = ?, breed = ?, color = ?, age = ?," +
                " name = ?, adoption_status = ?, image_url = ?, image_url1 = ?, image_url2 = ?, parent_id = ?" +
                "WHERE animal_id = ?;";
        try{
            int numberOfRows = jdbcTemplate.update(sql, pet.getAnimalType(), pet.getAnimalBreed(),
                    pet.getAnimalColor(), pet.getAnimalAge(), pet.getAnimalName(), pet.getAdoptionStatus(),
                    pet.getImageUrl(), pet.getImageUrl1(), pet.getImageUrl2(), parent_id, pet.getAnimalId());
            if (numberOfRows==0){
                throw new DaoException("Couldn't update this pet!");
            } else {
                updatedPet = getPetById(pet.getAnimalId());
            }
        } catch(DataIntegrityViolationException e){
            throw new DaoException("Can't update the pet with the given data", e);
        }catch(Exception e){
            throw new DaoException("Something went wrong updating the pet.",e);
        }
>>>>>>> java/src/main/java/com/techelevator/dao/JdbcAvailablePetDao.java
    }

    private AvailablePet mapRowToAvailablePet(SqlRowSet rs) {
        AvailablePet availablePet = new AvailablePet();
        availablePet.setAnimalId(rs.getLong("animal_id"));
        availablePet.setAnimalType(rs.getString("animal_type"));
        availablePet.setAnimalBreed(rs.getString("breed"));
        availablePet.setAnimalColor(rs.getString("color"));
        availablePet.setAnimalAge(rs.getInt("age"));
        availablePet.setAnimalName(rs.getString("name"));
        availablePet.setAdoptionStatus(rs.getString("adoption_status"));
        availablePet.setImageUrl(rs.getString("image_url") != null ? rs.getString("image_url") : "/images/default.png");
        availablePet.setImageUrl1(rs.getString("image_url1") != null ? rs.getString("image_url1") : "/images/default.png");
        availablePet.setImageUrl2(rs.getString("image_url2") != null ? rs.getString("image_url2") : "/images/default.png");

        return availablePet;
    }
}

