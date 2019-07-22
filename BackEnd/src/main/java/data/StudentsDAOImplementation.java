package data;

import data.jdbc.DataAccess;
import model.Student;
import org.springframework.dao.DataAccessException;

import java.util.List;

public class StudentsDAOImplementation implements StudentsDAO {

    private final DataAccess dataAccess;

    public StudentsDAOImplementation(DataAccess dataAccess){
        this.dataAccess = dataAccess;
    }

    @Override
    public List<Student> getALlStudents() throws DataAccessException {
        return dataAccess.getALlStudents();
    }

    @Override
    public void registerStudent(Student student, String hashedPassword) throws DataAccessException {
        dataAccess.registerStudent(student, hashedPassword);
    }

}
