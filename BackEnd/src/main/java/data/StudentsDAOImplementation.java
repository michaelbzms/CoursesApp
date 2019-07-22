package data;

import Util.Feedback;
import data.jdbc.DataAccess;
import model.Student;
import model.User;
import org.springframework.dao.DataAccessException;

import java.util.List;

public class StudentsDAOImplementation implements StudentsDAO {

    private final DataAccess dataAccess;

    public StudentsDAOImplementation(DataAccess dataAccess){
        this.dataAccess = dataAccess;
    }

    @Override
    public User authenticateUser(String email, String hashedPassword) throws DataAccessException {
        return dataAccess.authenticateUser(email, hashedPassword);
    }

    @Override
    public Feedback changeUserPassword(int userId, String oldHashedPassword, String newHashedPassword) throws DataAccessException {
        return dataAccess.changeUserPassword(userId, oldHashedPassword, newHashedPassword);
    }

    @Override
    public Student getStudent(int studentId) throws DataAccessException {
        return dataAccess.getStudent(studentId);
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
    public Feedback editStudent(Student student) throws DataAccessException {
        return dataAccess.editStudent(student);
    }

    @Override
    public Feedback deleteStudent(int studentId) throws DataAccessException {
        return dataAccess.deleteStudent(studentId);
    }

}
