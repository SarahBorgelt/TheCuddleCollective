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
            180, "Fun Dr", "Cleveland","OH");
    protected static final Parent PARENT_2 = new Parent(2, "Jake", "Jakington", "3303405000",
            360, "Cool Dr", "Seattle", "WA");
    private static final Parent PARENT_3 = new Parent(3, "Carol", "Carrington", "4403409000",
            720, "Cozytown Ln", "Smith", "AK");

//    long parentId, String firstName, String lastName,
//    int phoneNumber, int streetNumber, String streetName,
//    String cityName, String stateAbbreviation

    private JdbcUserDao sut;

    @BeforeEach
    public void setup() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        sut = new JdbcUserDao(jdbcTemplate);
    }

//    Parent addNewParent(Parent parent);



//    Parent getParentById(long parentId);
}

