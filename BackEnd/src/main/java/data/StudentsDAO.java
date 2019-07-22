package data;

import Util.Feedback;
import model.Student;
import org.springframework.dao.DataAccessException;

import java.util.List;

public interface StudentsDAO {

    List<Student> getALlStudents() throws DataAccessException;

    Feedback registerStudent(Student student, String hashedPassword) throws DataAccessException;

    void editStudent(Student student) throws DataAccessException;

    Feedback changeUserPassword(int userId, String oldHashedPassword, String newHashedPassword) throws DataAccessException;
}
