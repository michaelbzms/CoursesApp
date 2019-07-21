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
            return JsonMapRepresentation.getJSONforError("data base error");
        }
    }

    @Override
    protected Representation post(Representation entity) throws ResourceException {
        try {
            Form form = new Form(entity);
            String title = form.getFirstValue("title");
            String ectsStr = form.getFirstValue("ects");
            String path = form.getFirstValue("path");
            String type = form.getFirstValue("type");
            String specificpath = form.getFirstValue("specificpath");
            if (title == null || "".equals(title) ||
                ectsStr == null || "".equals(ectsStr) ||
                path == null || "".equals(path)){
                return JsonMapRepresentation.getJSONforError("Missing necessary parameter(s)");
            }
            coursesDAO.submitCourse(new Course(null, title, Integer.parseInt(ectsStr), path, null, type, specificpath));
            return JsonMapRepresentation.SUCCESS_JSON;
        } catch (NumberFormatException e) {
            return JsonMapRepresentation.getJSONforError("ects parameter must be an integer number");
        } catch (DataAccessException e) {
            return JsonMapRepresentation.getJSONforError("data base error");
        }
    }

}
