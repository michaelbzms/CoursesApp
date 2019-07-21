package data;

import data.jdbc.DataAccess;
import model.Course;
import org.springframework.dao.DataAccessException;

import java.util.List;

public class CoursesDAOImplementation implements CoursesDAO {

    private final DataAccess dataAccess;

    public CoursesDAOImplementation(DataAccess dataAccess){
        this.dataAccess = dataAccess;
    }

    @Override
    public Course getCourse(int courseId) throws DataAccessException {
        return dataAccess.getCourse(courseId);
    }

    @Override
    public Course getCourse(int courseId, int studentId) throws DataAccessException {
        return dataAccess.getCourse(courseId, studentId);
    }

    @Override
    public List<Course> getAllCourses() throws DataAccessException {
        return dataAccess.getAllCourses();
    }

    @Override
    public List<Course> getAllCourses(int studentId) throws DataAccessException {
        return dataAccess.getAllCourses(studentId);
    }
}
