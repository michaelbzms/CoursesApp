package data;

import Util.Feedback;
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
    public Feedback registerStudent(Student student, String hashedPassword) throws DataAccessException {
        return dataAccess.registerStudent(student, hashedPassword);
    }

    @Override
    public void editStudent(Student student) throws DataAccessException {
        dataAccess.editStudent(student);
    }

    @Override
    public Feedback changeUserPassword(int userId, String oldHashedPassword, String newHashedPassword) throws DataAccessException {
        return dataAccess.changeUserPassword();
    }

}
