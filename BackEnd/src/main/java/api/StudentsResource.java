package api;

import Util.JWT;
import Util.JsonMapRepresentation;
import conf.Configuration;
import data.StudentsDAO;
import model.Student;
import org.restlet.representation.Representation;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;
import org.springframework.dao.DataAccessException;

import java.util.List;

public class StudentsResource extends ServerResource {

    public final StudentsDAO studentsDAO = Configuration.getInstance().getStudentsDAO();

    @Override
    protected Representation get() throws ResourceException {
        try {
            List<Student> allStudents;
            allStudents = studentsDAO.getALlStudents();
            return JsonMapRepresentation.getJSONforList("students", allStudents);
        } catch (DataAccessException e) {
            return JsonMapRepresentation.getJSONforError("data base error");
        }
    }

}
