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
            "brown", 8, "gator", "available",
            "img1", "img2", "img3");
    private static final AvailablePet PET_3 = new AvailablePet(3L, "cat", "mutt",
            "orange", 6, "kyo", "available",
            "img1", "img2", "img3");
//
//    Long animalId, String animalType, String animalBreed, String animalColor,
//    Integer animalAge, String animalName, String adoptionStatus,
//    String imageUrl, String imageUrl1, String imageUrl2

    private JdbcUserDao sut;

    @BeforeEach
    public void setup() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        sut = new JdbcUserDao(jdbcTemplate);
    }

}
