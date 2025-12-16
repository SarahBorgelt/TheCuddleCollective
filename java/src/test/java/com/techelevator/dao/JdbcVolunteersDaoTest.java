package com.techelevator.dao;

import com.techelevator.model.AvailablePet;
import com.techelevator.model.ShelterVolunteer;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.jdbc.core.JdbcTemplate;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;


public class JdbcVolunteersDaoTest extends BaseDaoTest {

    private JdbcVolunteersDao sut;

    protected static final ShelterVolunteer VOL_1 = new ShelterVolunteer("1", "Chris", "Chrissington",
            "chris@shelter.org", "2202004000", true, true);
    protected static final ShelterVolunteer VOL_2 = new ShelterVolunteer("2", "Sarah", "Sarrington",
            "sarah@shelter.org", "5505004000", true, true);
    private static final ShelterVolunteer VOL_3 = new ShelterVolunteer("3", "Max", "Maxxington",
            "max@shelter.org", "3303005000", true, true);

JdbcTemplate jdbcTemplate = new JdbcTemplate();

    @BeforeEach
    public void setup() {
        jdbcTemplate = new JdbcTemplate(dataSource);
        sut = new JdbcVolunteersDao(jdbcTemplate);
        jdbcTemplate.update("DELETE FROM volunteers");

        String insertSql = "INSERT INTO volunteers (first_name, last_name, email) \" +\n" +
                "                \"VALUES (?, ?, ?) RETURNING volunteer_id;";;

        jdbcTemplate.update(insertSql,
                VOL_1.getFirstName(), VOL_1.getLastName(), VOL_1.getEmail());

        jdbcTemplate.update(insertSql,
                VOL_2.getFirstName(), VOL_2.getLastName(), VOL_2.getEmail());

        jdbcTemplate.update(insertSql,
                VOL_3.getFirstName(), VOL_3.getLastName(), VOL_3.getEmail());
    }

// TODO: VolunteersDAO needs getVolunteersByID method to test.

    @Test
    public void getVolunteers_shouldReturnListOfVolunteers(){
        List<ShelterVolunteer> volunteers = sut.getAllVolunteers();

        assertNotNull(volunteers);
        assertEquals(3, volunteers.size(), "Should retrieve all 3 volunteers inserted in setup.");

//        ShelterVolunteer sarah = sut.getAllVolunteers(VOL_2.getVolunteerId());

//        assertNotNull(sarah, "Sarah should populate");
//        assertEquals(VOL_2.getFirstName(), sarah.getFirstName());
//        assertEquals(VOL_2.getLastName(), sarah.getLastName());
//        assertEquals(VOL_2.getEmail(), sarah.getEmail());
    }
    }


//    List<ShelterVolunteer> getAllVolunteers();

//    public ShelterVolunteer createVolunteer(ShelterVolunteer volunteer);
//
//    boolean deleteVolunteer(int volunteerId);
//
//    ShelterVolunteer findByUsername(String username);
//
//    void update(ShelterVolunteer v);

