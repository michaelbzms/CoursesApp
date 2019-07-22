package data.jdbc;

import model.User;
import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRowMapper implements RowMapper<User> {

    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new User(
                rs.getInt("idUsers"),
                rs.getString("email"),
                rs.getBoolean("isAdmin")
        );
    }

}
