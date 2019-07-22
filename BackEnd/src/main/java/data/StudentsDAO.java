package data;

import Util.Feedback;
import model.Student;
import org.springframework.dao.DataAccessException;

import java.util.List;

public interface StudentsDAO {

    Student getStudent(int studentId) throws DataAccessException;

    List<Student> getALlStudents() throws DataAccessException;

    Feedback registerStudent(Student student, String hashedPassword) throws DataAccessException;

    Feedback editStudent(Student student) throws DataAccessException;   // changes email, firstname, lastname

    Feedback deleteStudent(int studentId) throws DataAccessException;

}
