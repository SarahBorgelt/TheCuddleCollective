package com.techelevator.dao;

import com.techelevator.model.AvailablePet;
import com.techelevator.model.ShelterVolunteer;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.jdbc.core.JdbcTemplate;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;


public class JdbcVolunteersDaoTest extends BaseDaoTest {

    private JdbcUserDao sut;

    protected static final ShelterVolunteer VOL_1 = new ShelterVolunteer("1", "Chris", "Chrissington",
            "chris@shelter.org", "2202004000");
    protected static final ShelterVolunteer VOL_2 = new ShelterVolunteer("2", "Sarah", "Sarrington",
            "sarah@shelter.org", "5505004000");
    private static final ShelterVolunteer VOL_3 = new ShelterVolunteer("3", "Max", "Maxxington",
            "max@shelter.org", "3303005000");

//    String volunteerId, String firstName, String lastName, String email, String phoneNumber

    @BeforeEach
    public void setup() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        sut = new JdbcUserDao(jdbcTemplate);
    }

}
