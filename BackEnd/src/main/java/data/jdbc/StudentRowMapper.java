package data.jdbc;

import model.Student;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class StudentRowMapper implements RowMapper<Student> {

    @Override
    public Student mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Student(
                rs.getInt("idUsers"),
                rs.getString("email"),
                rs.getBoolean("isAdmin"),
                rs.getString("firstname"),
                rs.getString("lastname")
        );
    }

}
