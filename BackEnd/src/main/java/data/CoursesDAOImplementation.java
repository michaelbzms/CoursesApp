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
    public List<Course> getAllCourses() throws DataAccessException {
        return dataAccess.getAllCourses();
    }

    @Override
    public List<Course> getAllCourses(int userId) throws DataAccessException {
        return dataAccess.getAllCourses(userId);
    }
}
