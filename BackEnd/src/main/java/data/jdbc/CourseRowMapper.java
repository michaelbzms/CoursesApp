package data.jdbc;

import model.Course;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CourseRowMapper implements RowMapper<Course> {

    @Override
    public Course mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Course(
                rs.getInt("idCourses"),
                rs.getString("title"),
                rs.getInt("ects"),
                rs.getInt("semester"),
                rs.getString("path"),
                null,
                rs.getString("type"),
                rs.getString("specificpath")
        );
    }

}
