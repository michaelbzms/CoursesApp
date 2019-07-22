package data;

import Util.Feedback;
import model.Course;
import org.springframework.dao.DataAccessException;

import java.util.List;

public interface CoursesDAO {

    Course getCourse(int courseId) throws DataAccessException;

    Course getCourse(int courseId, int studentId) throws DataAccessException;

    List<Course> getAllCourses() throws DataAccessException;

    List<Course> getAllCourses(int studentId) throws DataAccessException;   // this also gets grades associated with given user

    void submitCourse(Course course) throws DataAccessException;

    Feedback editCourse(Course course) throws DataAccessException;

    Feedback deleteCourse(int courseId) throws DataAccessException;

}
