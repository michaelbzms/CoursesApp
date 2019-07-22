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
                rs.getString("path"),
                (rs.getDouble("grade") != -1.0) ? rs.getDouble("grade") : null,
                rs.getString("type"),
                rs.getString("specificpath")
        );
    }

}
