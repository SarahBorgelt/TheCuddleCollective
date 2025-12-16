package com.techelevator.dao;

import com.techelevator.model.AvailablePet;
import com.techelevator.model.Parent;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.jdbc.core.JdbcTemplate;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;


public class JdbcAvailablePetDaoTest extends BaseDaoTest {

    protected static final AvailablePet PET_1 = new AvailablePet(1L, "cat", "tabby",
            "grey", 4, "cheese",
            "available", "img1", "img2", "img3");
    protected static final AvailablePet PET_2 = new AvailablePet(2L, "dog", "pitbull",
            "brown", 8, "gator", "pending",
            "img01", "img02", "img03");
    private static final AvailablePet PET_3 = new AvailablePet(3L, "cat", "mutt",
            "orange", 4, "kyo", "available",
            "img001", "img002", "img003");
//
//    Long animalId, String animalType, String animalBreed, String animalColor,
//    Integer animalAge, String animalName, String adoptionStatus,
//    String imageUrl, String imageUrl1, String imageUrl2

    private JdbcAvailablePetDao sut;
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    public void setup() {
        jdbcTemplate = new JdbcTemplate(dataSource);
        sut = new JdbcAvailablePetDao(jdbcTemplate);
        jdbcTemplate.update("DELETE FROM available_pets");

//        animal_id SERIAL,
//        animal_type VARCHAR(150) NOT NULL,
//        breed VARCHAR(200) NOT NULL,
//        color VARCHAR(150) NOT NULL,
//        age INT NOT NULL,
//        name VARCHAR(150) NOT NULL,
//        parent_id int,
//        adoption_status adoption_status_enum NOT NULL,
//        image_url VARCHAR(500) NOT NULL UNIQUE,
//                image_url1 VARCHAR(500) UNIQUE,
//                image_url2 VARCHAR(500) UNIQUE,

        String insertSql = "INSERT INTO available_pets (animal_id, animal_type," +
                "breed, color, age, name, adoption_status, image_url, image_url1, image_url2) " +
                "VALUES (?, ?, ?, ?, ?, ?, CAST(? AS adoption_status_enum), ?, ?, ?)";;

        jdbcTemplate.update(insertSql,
                PET_1.getAnimalId(), PET_1.getAnimalType(), PET_1.getAnimalBreed(),
                PET_1.getAnimalColor(), PET_1.getAnimalAge(), PET_1.getAnimalName(),
                PET_1.getAdoptionStatus(), PET_1.getImageUrl(), PET_1.getImageUrl1(), PET_1.getImageUrl2());

        jdbcTemplate.update(insertSql,
                PET_2.getAnimalId(), PET_2.getAnimalType(), PET_2.getAnimalBreed(),
                PET_2.getAnimalColor(), PET_2.getAnimalAge(), PET_2.getAnimalName(), PET_2.getAdoptionStatus(),
                PET_2.getImageUrl(), PET_2.getImageUrl1(), PET_2.getImageUrl2());

        jdbcTemplate.update(insertSql,
                PET_3.getAnimalId(), PET_3.getAnimalType(), PET_3.getAnimalBreed(),
                PET_3.getAnimalColor(), PET_3.getAnimalAge(), PET_3.getAnimalName(), PET_3.getAdoptionStatus(),
                PET_3.getImageUrl(), PET_3.getImageUrl1(), PET_3.getImageUrl2());
    }



    @Test
    public void getAvailablePetsByBreed_returnsCorrectNumberOfAvailPets() {
        String testBreed = "tabby";
        List<AvailablePet> result = sut.getAvailablePetsByBreed(testBreed);
        assertNotNull(result, "The result list should not be null.");
        assertEquals(1, result.size(), "Only one pet of breed 'tabby' should be returned.");
        AvailablePet actualPet = result.get(0);
        assertEquals(PET_1.getAnimalBreed(), actualPet.getAnimalBreed(), "Animal Breed should match 'tabby'.");
    }

    @Test
    public void getAvailablePetsByBreed_doesntListNonAvailPets() {
        String testBreed = "pitbull";
        List<AvailablePet> result = sut.getAvailablePetsByBreed(testBreed);
        assertNotNull(result, "The result list should not be null.");
        assertEquals(0, result.size(), "Only one pet of breed 'pitbull' should be returned.");
       }

//    List<AvailablePet> getAvailablePetsByColor(String Color);

@Test
public void getAvailablePetsByColor_returnsCorrectAmount() {
    String testColor = "orange";
    List<AvailablePet> result = sut.getAvailablePetsByColor(testColor);
    assertNotNull(result, "The result list should not be null.");
    assertEquals(1, result.size(), "Only one color, orange, should be returned.");
    AvailablePet actualPet = result.get(0);
    assertEquals(PET_3.getAnimalColor(), actualPet.getAnimalColor(), "Animal color should match 'orange'.");
}

    @Test
    public void getAvailablePetsByColor_doesntListNonAvailPets() {
        String testColor = "brown";
        List<AvailablePet> result = sut.getAvailablePetsByColor(testColor);
        assertNotNull(result, "The result list should not be null.");
        assertEquals(0, result.size(), "This pet isn't available");

    }


//    List<AvailablePet> getAvailablePetsByAge(Integer age);

    @Test
    public void getAvailablePetsByAge_returnsCorrectAmount() {
        int testAge = 4;
        List<AvailablePet> result = sut.getAvailablePetsByAge(testAge);
        assertNotNull(result, "The result list should not be null.");
        assertEquals(2, result.size(), "Two pets are 4 years old!");
        AvailablePet actualPet = result.get(0);
        assertEquals(PET_3.getAnimalAge(), actualPet.getAnimalAge(), "animal age should match 4.");
    }

    @Test
    public void getAvailablePetsByAge_doesntListNonAvailPets() {
        int testAge = 8;
        List<AvailablePet> result = sut.getAvailablePetsByAge(testAge);
        assertNotNull(result, "The result list should not be null.");
        assertEquals(0, result.size(), "None should be returned.");

    }

//    List<AvailablePet> getAvailablePetsByType(String type);
//

    @Test
    public void getAvailablePetsByType_returnsCorrectAmount() {
        String testType = "cat";
        List<AvailablePet> result = sut.getAvailablePetsByType(testType);
        assertNotNull(result, "The result list should not be null.");
        assertEquals(2, result.size(), "Two cats are in the database.");
        AvailablePet actualPet = result.get(0);
        assertEquals(PET_3.getAnimalAge(), actualPet.getAnimalAge(), "Animal type should match 'cat'.");
    }

    @Test
    public void getAvailablePetsByType_doesntListNonAvailPets() {
        String testType = "dog";
        List<AvailablePet> result = sut.getAvailablePetsByType(testType);
        assertNotNull(result, "The result list should not be null.");
        assertEquals(0, result.size(), "None should be returned.");
       }

//    List<AvailablePet> getAvailablePetsByAdoptionStatus(String adoptionStatus);
//

    @Test
    public void getAvailablePetsByStatus_returnsCorrectAmount() {
        String testStatus = "available";
        List<AvailablePet> result = sut.getAvailablePetsByAdoptionStatus(testStatus);
        assertNotNull(result, "The result list should not be null.");
        assertEquals(2, result.size(), "Two animals are available in the database.");
        AvailablePet actualPet = result.get(0);
        assertEquals(PET_3.getAdoptionStatus(), actualPet.getAdoptionStatus(), "status should match.");
    }

//    AvailablePet getPetById(long petId);

    @Test
    public void getAvailablePetsById_returnsCorrectAnimal() {
        long testId = 1;
        AvailablePet result = sut.getPetById(testId);
        assertNotNull(result, "The result list should not be null.");
        assertEquals(PET_1.getAnimalId(), result.getAnimalId(), "These animals should match.");
    }


}


