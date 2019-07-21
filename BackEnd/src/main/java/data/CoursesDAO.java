package data;

import model.Course;
import org.springframework.dao.DataAccessException;

import java.util.List;

public interface CoursesDAO {

    List<Course> getAllCourses() throws DataAccessException;

    List<Course> getAllCourses(int userId) throws DataAccessException;   // this also gets grades associated with given user

}
