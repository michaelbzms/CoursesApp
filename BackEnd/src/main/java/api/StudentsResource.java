package api;

import Util.Feedback;
import Util.Hashing;
import Util.JsonMapRepresentation;
import conf.Configuration;
import data.StudentsDAO;
import model.Student;
import org.restlet.data.Form;
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
            e.printStackTrace();
            return JsonMapRepresentation.getJSONforError("data base error");
        }
    }

    @Override
    protected Representation post(Representation entity) throws ResourceException {
        try {
            Form form = new Form(entity);
            String email = form.getFirstValue("email");
            String password = form.getFirstValue("password");
            String firstname = form.getFirstValue("firstname");
            String lastname = form.getFirstValue("lastname");
            if (email == null || "".equals(email) ||
                password == null || "".equals(password) ||
                firstname == null || "".equals(firstname) ||
                lastname == null || "".equals(lastname)) {
                return JsonMapRepresentation.getJSONforError("Missing or empty necessary parameter(s)");
            }
            String hashedPassword = Hashing.getHashSHA256(password);    // hash password for security
            Feedback fb = studentsDAO.registerStudent(new Student(null, email, false, firstname, lastname), hashedPassword);
            if (!fb.SUCCESS) return JsonMapRepresentation.getJSONforError(fb.MESSAGE);   // aka email already taken
            return JsonMapRepresentation.SUCCESS_JSON;
        } catch (NumberFormatException e) {
            return JsonMapRepresentation.getJSONforError("Non-integer given to parameter that must be an integer number");
        } catch (DataAccessException e) {
            e.printStackTrace();
            return JsonMapRepresentation.getJSONforError("data base error");
        }
    }

}
