package data;

import model.Student;
import org.springframework.dao.DataAccessException;

import java.util.List;

public interface StudentsDAO {

    List<Student> getALlStudents() throws DataAccessException;

}
