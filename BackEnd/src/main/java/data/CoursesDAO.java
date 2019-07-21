package data;

import model.Course;
import org.springframework.dao.DataAccessException;

import java.util.List;

public interface CoursesDAO {

    Course getCourse(int courseId) throws DataAccessException;

    Course getCourse(int courseId, int studentId) throws DataAccessException;

    List<Course> getAllCourses() throws DataAccessException;

    List<Course> getAllCourses(int studentId) throws DataAccessException;   // this also gets grades associated with given user

}
