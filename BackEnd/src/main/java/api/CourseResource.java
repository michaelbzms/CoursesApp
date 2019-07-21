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

public class CourseResource extends ServerResource {

    public final CoursesDAO coursesDAO = Configuration.getInstance().getCoursesDAO();


    @Override
    protected Representation get() throws ResourceException {
        Integer courseId = getCourseId();
        Course course;
        String jwt = JWT.getJWTFromHeaders(getRequest());
        if (jwt == null){    // no session
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
            if (u.getId() == null){
                return JsonMapRepresentation.getJSONforError("Jason Web Token contains no user id");
            }
            course = coursesDAO.getCourse(courseId, u.getId());
        }
        return JsonMapRepresentation.getJSONforObject("course", course);
    }


    ///////////////////////////////////////

    private Integer getCourseId(){
        Integer courseId;
        try {
            String courseIdStr = (String) getRequest().getAttributes().get("courseId");
            try {
                courseId = Integer.parseInt(courseIdStr);
            } catch (NumberFormatException e) {
                courseId = null;
            }
        } catch (NullPointerException e){
            System.err.println("Warning: NullPointerException in fetching variable from URI");
            courseId = null;
        }
        return courseId;
    }

}
