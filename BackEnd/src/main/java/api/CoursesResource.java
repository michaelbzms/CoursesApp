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

import java.util.List;

public class CoursesResource extends ServerResource {

    public final CoursesDAO coursesDAO = Configuration.getInstance().getCoursesDAO();

    @Override
    protected Representation get() throws ResourceException {
        try {
            List<Course> allCourses;
            String jwt = JWT.getJWTFromHeaders(getRequest());
            if (jwt == null) {    // no session
                allCourses = coursesDAO.getAllCourses();
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
                allCourses = coursesDAO.getAllCourses(u.getId());
            }
            return JsonMapRepresentation.getJSONforList("courses", allCourses);
        } catch (DataAccessException e) {
            e.printStackTrace();
            return JsonMapRepresentation.getJSONforError("data base error");
        }
    }

    @Override
    protected Representation post(Representation entity) throws ResourceException {
        try {
            Form form = new Form(entity);
            String title = form.getFirstValue("title");
            String ectsStr = form.getFirstValue("ects");
            String semesterStr = form.getFirstValue("semester");
            String category = form.getFirstValue("category");
            String type = form.getFirstValue("type");
            // TODO: add E1, ..., E6 options
            if (title == null || "".equals(title) ||
                ectsStr == null || "".equals(ectsStr) ||
                semesterStr == null || "".equals(semesterStr) ||
                category == null || "".equals(category)) {
                return JsonMapRepresentation.getJSONforError("Missing or empty necessary parameter(s)");
            }
            coursesDAO.submitCourse(new Course(null, title, Integer.parseInt(ectsStr), Integer.parseInt(semesterStr), category, null, type));
            return JsonMapRepresentation.getJSONforSuccess();
        } catch (NumberFormatException e) {
            return JsonMapRepresentation.getJSONforError("Non-integer given to parameter that must be an integer number");
        } catch (DataAccessException e) {
            e.printStackTrace();
            return JsonMapRepresentation.getJSONforError("data base error");
        }
    }

}
