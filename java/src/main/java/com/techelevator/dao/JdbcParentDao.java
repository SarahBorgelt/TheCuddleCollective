package com.techelevator.dao;

import com.techelevator.exception.DaoException;
import com.techelevator.model.AvailablePet;
import com.techelevator.model.Parent;
import jakarta.validation.constraints.NotBlank;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

@Component
public class JdbcParentDao {

    private final JdbcTemplate jdbcTemplate;

    public JdbcParentDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

// need to edit method SQL statement
    public Parent getParentById(long parentId) {
        Parent parentById = null;
        String sql = "SELECT first_name, last_name, phone_number, street_number\" +\n" +
                "                    \"    street_name, city_name, state_abbreviation " +
                "FROM parent " +
                "WHERE parent_id = ?;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, parentId);
        if (results.next()) {
            parentById = mapRowToParent(results);
        }
        return parentById;
    }

// need to edit method SQL statement
    public Parent addNewParent(Parent parent){
           Parent newParent = new Parent();
            String sql = "INSERT INTO parent (first_name, last_name, phone_number, street_number" +
                    "    street_name, city_name, state_abbreviation) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?)" +
                    "RETURNING parent_id";
            try {
                long newParentId = jdbcTemplate.queryForObject(sql,int.class, newParent.getFirstName(),
                        newParent.getLastName(), newParent.getPhoneNumber(), newParent.getStreetNumber(),
                        newParent.getStreetName(), newParent.getCityName(), newParent.getStateAbbreviation());
                newParent = getParentById(newParentId);
                parent.setParentId(newParentId);
                newParent = parent;
            } catch (Exception e){
                throw new DaoException("Cannot add parent.",e);
            }
            return newParent;
        }




    private Parent mapRowToParent(SqlRowSet rs) {
        Parent parent = new Parent();
        parent.setFirstName(rs.getString("first_name"));
        parent.setLastName(rs.getString("last_name"));
        parent.setPhoneNumber(rs.getString("phone_number"));
        parent.setStreetNumber(rs.getInt("street_number"));
        parent.setStreetName(rs.getString("street_address"));
        parent.setCityName(rs.getString("city_name"));
        parent.setStateAbbreviation(rs.getString("state_name"));
        return parent;
    }

}
