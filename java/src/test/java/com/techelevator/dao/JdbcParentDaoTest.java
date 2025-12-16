package com.techelevator.dao;

import com.techelevator.model.Parent;
import com.techelevator.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.jdbc.core.JdbcTemplate;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;


public class JdbcParentDaoTest extends BaseDaoTest {

    protected static final Parent PARENT_1 = new Parent(1, "Jen", "Jennington", "2162554000",
            180, "Fun Dr", "Cleveland", "OH");
    protected static final Parent PARENT_2 = new Parent(2, "Jake", "Jakington", "3303405000",
            360, "Cool Dr", "Seattle", "WA");
    private static final Parent PARENT_3 = new Parent(3, "Carol", "Carrington", "4403409000",
            720, "Cozytown Ln", "Smith", "AK");

//    long parentId, String firstName, String lastName,
//    int phoneNumber, int streetNumber, String streetName,
//    String cityName, String stateAbbreviation

    private JdbcParentDao sut;
    private JdbcTemplate jdbcTemplate;


    @BeforeEach
    public void setup() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        sut = new JdbcParentDao(jdbcTemplate);



        jdbcTemplate.update("DELETE FROM parent");

        String insertSql = "INSERT INTO parent (parent_id, first_name, last_name, phone_number, street_number, " +
                "street_name, city_name, state_abbreviation) " +
                " VALUES (?, ?, ?, ?, ?, ?, ?, ?)";;

        jdbcTemplate.update(insertSql, PARENT_1.getParentId(),
                PARENT_1.getFirstName(), PARENT_1.getLastName(), PARENT_1.getPhoneNumber(), PARENT_1.getStreetNumber(),
                PARENT_1.getStreetName(), PARENT_1.getCityName(), PARENT_1.getStateAbbreviation());

        jdbcTemplate.update(insertSql, PARENT_2.getParentId(),
                PARENT_2.getFirstName(), PARENT_2.getLastName(), PARENT_2.getPhoneNumber(), PARENT_2.getStreetNumber(),
                PARENT_2.getStreetName(), PARENT_2.getCityName(), PARENT_2.getStateAbbreviation());

        jdbcTemplate.update(insertSql, PARENT_3.getParentId(),
                PARENT_3.getFirstName(), PARENT_3.getLastName(), PARENT_3.getPhoneNumber(), PARENT_3.getStreetNumber(),
                PARENT_3.getStreetName(), PARENT_3.getCityName(), PARENT_3.getStateAbbreviation());
    }

    // TODO: Edit SQL Grammar in Parent class.

//    Parent addNewParent(Parent parent);
    @Test
    public void addNewParent_addsParentAndReturnsId() {
        Parent NEW_PARENT = new Parent(
                0,
                "Keni",
                "Kennington",
                "555-9012",
                444,
                "Test St",
                "Gotham",
                "NY"
        );

        Parent addedParent = sut.addNewParent(NEW_PARENT);

        assertNotNull(addedParent, "The returned Parent object should not be null.");
        assertTrue(addedParent.getParentId() > 3, "The generated Parent ID should be greater than the setup IDs (1, 2, 3).");

        Parent retrievedParent = sut.getParentById(addedParent.getParentId());

        assertNotNull(retrievedParent, "The record must be retrievable from the database.");

        assertEquals(addedParent.getParentId(), retrievedParent.getParentId(), "The IDs must match.");
        assertEquals(NEW_PARENT.getFirstName(), retrievedParent.getFirstName(), "First names must match.");
        assertEquals(NEW_PARENT.getLastName(), retrievedParent.getLastName(), "Last names must match.");

        assertEquals(NEW_PARENT.getCityName(), retrievedParent.getCityName(), "City names must match.");
    }


    //    Parent getParentById(long parentId);
    @Test
    public void getParentById_returnsCorrectParent() {
          long validId = PARENT_1.getParentId();

        Parent actualParent = sut.getParentById(validId);

        assertNotNull(actualParent, "The returned Parent should not be null.");

        assertEquals(PARENT_1.getParentId(), actualParent.getParentId(), "Parent ID should match.");
        assertEquals(PARENT_1.getFirstName(), actualParent.getFirstName(), "First name should match.");
        assertEquals(PARENT_1.getLastName(), actualParent.getLastName(), "Last name should match.");
        assertEquals(PARENT_1.getCityName(), actualParent.getCityName(), "City name should match.");

    }
}


