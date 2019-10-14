package data;

import Util.Feedback;
import model.Course;
import org.springframework.dao.DataAccessException;

import java.util.List;

public class CoursesDAO_JPAImpl implements CoursesDAO {
    // TODO

    @Override
    public Course getCourse(int courseId) throws DataAccessException {
        return null;
    }

    @Override
    public Course getCourse(int courseId, int studentId) throws DataAccessException {
        return null;
    }

    @Override
    public List<Course> getAllCourses() throws DataAccessException {
        return null;
    }

    @Override
    public List<Course> getAllCourses(int studentId) throws DataAccessException {
        return null;
    }

    @Override
    public void submitCourse(Course course) throws DataAccessException {

    }

    @Override
    public Feedback editCourse(Course course) throws DataAccessException {
        return null;
    }

    @Override
    public Feedback deleteCourse(int courseId) throws DataAccessException {
        return null;
    }

    @Override
    public void setGradeForCourse(int studentId, int courseId, Double grade) throws DataAccessException {

    }
}
