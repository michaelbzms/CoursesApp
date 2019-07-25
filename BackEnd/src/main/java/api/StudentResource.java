package api;

import Util.Feedback;
import Util.JsonMapRepresentation;
import conf.Configuration;
import data.StudentsDAO;
import model.Student;
import org.restlet.data.Form;
import org.restlet.data.Status;
import org.restlet.representation.Representation;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;
import org.springframework.dao.DataAccessException;

public class StudentResource extends ServerResource {

    public final StudentsDAO studentsDAO = Configuration.getInstance().getStudentsDAO();

    @Override
    protected Representation get() throws ResourceException {
        try {
            int studentId = getStudentId();
            Student student = studentsDAO.getStudent(studentId);
            if (student == null) {
                this.setStatus(Status.CLIENT_ERROR_NOT_FOUND);
                return null;
            }
            return JsonMapRepresentation.getJSONforObject("student", student);
        } catch (DataAccessException e) {
            e.printStackTrace();
            return JsonMapRepresentation.getJSONforError("data base error");
        } catch (Exception e) {
            return JsonMapRepresentation.getJSONforError(e.getMessage());
        }
    }

    @Override
    protected Representation put(Representation entity) throws ResourceException {
        try {
            int studentId = getStudentId();
            Form form = new Form(entity);
            String email = form.getFirstValue("email");
            String firstname = form.getFirstValue("firstname");
            String lastname = form.getFirstValue("lastname");
            Feedback fb = studentsDAO.editStudent(new Student(studentId, email, false, firstname, lastname));
            // noinspection Duplicates
            if (!fb.SUCCESS && fb.STATUS == -1) {
                this.setStatus(Status.CLIENT_ERROR_NOT_FOUND);
                return null;
            } else if (!fb.SUCCESS) return JsonMapRepresentation.getJSONforError(fb.MESSAGE);   // aka new email already taken
            return JsonMapRepresentation.getJSONforSuccess();
        } catch (NumberFormatException e) {
            return JsonMapRepresentation.getJSONforError("Non-integer given to parameter that must be an integer number");
        } catch (DataAccessException e) {
            e.printStackTrace();
            return JsonMapRepresentation.getJSONforError("data base error");
        } catch (Exception e) {
            return JsonMapRepresentation.getJSONforError(e.getMessage());
        }
    }

    @Override
    protected Representation delete() throws ResourceException {
        try {
            int studentId = getStudentId();
            Feedback fb = studentsDAO.deleteStudent(studentId);
            if (!fb.SUCCESS) {
                this.setStatus(Status.CLIENT_ERROR_NOT_FOUND);
                return null;
            }
            return JsonMapRepresentation.getJSONforSuccess();
        } catch (DataAccessException e) {
            e.printStackTrace();
            return JsonMapRepresentation.getJSONforError("data base error");
        } catch (Exception e) {
            return JsonMapRepresentation.getJSONforError(e.getMessage());
        }
    }


    private int getStudentId() throws Exception {
        int studentId;
        try {
            String studentIdStr = (String) getRequest().getAttributes().get("studentId");
            try {
                studentId = Integer.parseInt(studentIdStr);
            } catch (NumberFormatException e) {
                throw new Exception("URI student id is not an integer");
            }
        } catch (NullPointerException e){
            System.err.println("Warning: NullPointerException in fetching variable from URI");
            throw new Exception("Could not fetch URI student id");
        }
        return studentId;
    }

}
