package data;

import Util.Feedback;
import model.Student;
import org.springframework.dao.DataAccessException;

import java.util.ArrayList;
import java.util.List;

public class StudentsDAOMockImpl implements StudentsDAO {

    @Override
    public Student getStudent(int studentId) throws DataAccessException {
        return new Student(studentId, "genericemail@mock.com", false, "FirstName", "LastName");
    }

    @Override
    public List<Student> getALlStudents() throws DataAccessException {
        return new ArrayList<>();   // empty list
    }

    @Override
    public Feedback registerStudent(Student student, String hashedPassword) throws DataAccessException {
        return new Feedback(false, "Cannot register in mock mode");
    }

    @Override
    public Feedback editStudent(Student student) throws DataAccessException {
        return new Feedback(false, "Cannot edit in mock mode");
    }

    @Override
    public Feedback deleteStudent(int studentId) throws DataAccessException {
        return new Feedback(false, "Cannot delete in mock mode");
    }

}
