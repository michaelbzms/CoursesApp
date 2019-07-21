package api;

import Util.JWT;
import Util.JsonMapRepresentation;
import conf.Configuration;
import data.CoursesDAO;
import model.Course;
import model.User;
import org.restlet.representation.Representation;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;

import java.util.List;

public class CoursesResource extends ServerResource {

    public final CoursesDAO coursesDAO = Configuration.getInstance().getCoursesDAO();

    @Override
    protected Representation get() throws ResourceException {
        List<Course> allCourses;

        String jwt = JWT.getJWTFromHeaders(getRequest());
        if (jwt == null){    // no session
            allCourses = coursesDAO.getAllCourses();
            System.out.println("HEEY");
        } else {
            User u;
            try {
                u = JWT.getUserFromJWT(jwt);
                if (u == null) throw new Exception();
            } catch (Exception e) {
                return JsonMapRepresentation.getJSONforError("Could not decode Jason Web Token " + (e.getMessage() != null ? ": " : "") + e.getMessage());
            }
            if (u.getId() == null){
                return JsonMapRepresentation.getJSONforError("Jason Web Token contains no user id");
            }
            allCourses = coursesDAO.getAllCourses(u.getId());

            for (Course c : allCourses){
                System.out.println(c.getTitle());
            }

        }
        return JsonMapRepresentation.getJSONforList("courses", allCourses);
    }
}
