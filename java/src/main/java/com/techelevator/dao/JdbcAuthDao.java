package com.techelevator.dao;

import com.techelevator.exception.DaoException;
import com.techelevator.model.ShelterVolunteer;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

@Component
public class JdbcAuthDao implements AuthDao {

    private final JdbcTemplate jdbcTemplate;

    public JdbcAuthDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public ShelterVolunteer findByUsername(String username) {
        String sql = "SELECT u.user_id, u.username, u.password_hash, u.first_login," +
                " v.first_name, v.last_name, v.email" +
                " FROM users u" +
                " JOIN volunteers v ON u.user_id = v.volunteer_id" +
                " WHERE u.username = ?";

        SqlRowSet rs = jdbcTemplate.queryForRowSet(sql, username);

        if (rs.next()) {
            return mapRowToVolunteer(rs);
        }
        return null;
    }

    private ShelterVolunteer mapRowToVolunteer(SqlRowSet rs) {
        ShelterVolunteer vol = new ShelterVolunteer();
        vol.setVolunteerId(rs.getString("volunteer_id"));
        vol.setFirstName(rs.getString("first_name"));
        vol.setLastName(rs.getString("last_name"));
        vol.setEmail(rs.getString("email"));
        vol.setUsername(rs.getString("username"));
        vol.setPasswordHash(rs.getString("password_hash"));
        vol.setTemporaryPasswordActive(rs.getBoolean("temp_password_active"));
        vol.setFirstLogin(rs.getBoolean("first_login"));
        return vol;
    }
}
