package api;

import Util.JWT;
import Util.JsonMapRepresentation;
import conf.Configuration;
import data.CoursesDAO;
import model.Course;
import model.User;
import org.restlet.data.Form;
import org.restlet.representation.Representation;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;
import org.springframework.dao.DataAccessException;

public class CourseResource extends ServerResource {

    public final CoursesDAO coursesDAO = Configuration.getInstance().getCoursesDAO();

    @Override
    protected Representation get() throws ResourceException {
        try {
            int courseId = getCourseId();
            Course course;
            String jwt = JWT.getJWTFromHeaders(getRequest());
            if (jwt == null) {    // no session
                course = coursesDAO.getCourse(courseId);
            } else {
                User u;
                // noinspection Duplicates
                try {
                    u = JWT.getUserFromJWT(jwt);
                    if (u == null) throw new Exception();
                } catch (Exception e) {
                    return JsonMapRepresentation.getJSONforError("Could not decode Jason Web Token " + (e.getMessage() != null ? ": " : "") + e.getMessage());
                }
                if (u.getId() == null) {
                    return JsonMapRepresentation.getJSONforError("Jason Web Token contains no user id");
                }
                course = coursesDAO.getCourse(courseId, u.getId());
            }
            return JsonMapRepresentation.getJSONforObject("course", course);
        } catch (DataAccessException e) {
            return JsonMapRepresentation.getJSONforError("data base error");
        } catch (Exception e) {
            return JsonMapRepresentation.getJSONforError(e.getMessage());
        }
    }

    @Override
    protected Representation put(Representation entity) throws ResourceException {
        try {
            int courseId = getCourseId();
            Form form = new Form(entity);
            String title = form.getFirstValue("title");
            String ectsStr = form.getFirstValue("ects");
            String semesterStr = form.getFirstValue("semester");
            String path = form.getFirstValue("path");
            String type = form.getFirstValue("type");
            String specificpath = form.getFirstValue("specificpath");
            coursesDAO.editCourse(new Course(courseId, title, (ectsStr == null) ? -1 : Integer.parseInt(ectsStr), (semesterStr == null) ? -1 : Integer.parseInt(semesterStr), path, null, type, specificpath));
            return JsonMapRepresentation.SUCCESS_JSON;
        } catch (NumberFormatException e) {
            return JsonMapRepresentation.getJSONforError("Non-integer given to parameter that must be an integer number");
        } catch (DataAccessException e) {
            return JsonMapRepresentation.getJSONforError("data base error");
        } catch (Exception e) {
            return JsonMapRepresentation.getJSONforError(e.getMessage());
        }
    }

    @Override
    protected Representation delete() throws ResourceException {
        try {
            int courseId = getCourseId();
            coursesDAO.deleteCourse(courseId);
            return JsonMapRepresentation.SUCCESS_JSON;
        } catch (DataAccessException e) {
            return JsonMapRepresentation.getJSONforError("data base error");
        } catch (Exception e) {
            return JsonMapRepresentation.getJSONforError(e.getMessage());
        }
    }

    ///////////////////////////////////////

    private int getCourseId() throws Exception {
        int courseId;
        try {
            String courseIdStr = (String) getRequest().getAttributes().get("courseId");
            try {
                courseId = Integer.parseInt(courseIdStr);
            } catch (NumberFormatException e) {
                throw new Exception("URI course id is not an integer");
            }
        } catch (NullPointerException e){
            System.err.println("Warning: NullPointerException in fetching variable from URI");
            throw new Exception("Could not fetch URI course id");
        }
        return courseId;
    }

}
