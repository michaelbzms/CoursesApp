package data.jdbc;

import model.Course;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CourseForStudentRowMapper implements RowMapper<Course> {

    @Override
    public Course mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Course(
                rs.getInt("idCourses"),
                rs.getString("title"),
                rs.getInt("ects"),
                rs.getInt("semester"),
                rs.getString("category"),
                (rs.getDouble("grade") != -1.0) ? rs.getDouble("grade") : null,
                rs.getString("type"),
                new boolean[]{
                        rs.getBoolean("E1"),
                        rs.getBoolean("E2"),
                        rs.getBoolean("E3"),
                        rs.getBoolean("E4"),
                        rs.getBoolean("E5"),
                        rs.getBoolean("E6")
                }
        );
    }

}
