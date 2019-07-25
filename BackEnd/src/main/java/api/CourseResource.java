package api;

import Util.Feedback;
import Util.JWT;
import Util.JsonMapRepresentation;
import conf.Configuration;
import data.CoursesDAO;
import model.Course;
import model.User;
import org.restlet.data.Form;
import org.restlet.data.Status;
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
                // if there is a student(!)-user in session then any registered grade will also be returned
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
                if (u.isAdmin()) course = coursesDAO.getCourse(courseId);
                else course = coursesDAO.getCourse(courseId, u.getId());
            }
            if (course == null) {
                this.setStatus(Status.CLIENT_ERROR_NOT_FOUND);
                return null;
            }
            return JsonMapRepresentation.getJSONforObject("course", course);
        } catch (DataAccessException e) {
            e.printStackTrace();
            return JsonMapRepresentation.getJSONforError("data base error");
        } catch (Exception e) {
            return JsonMapRepresentation.getJSONforError(e.getMessage());
        }
    }

    @Override
    protected Representation post(Representation entity) throws ResourceException {
        // stores grade for user in session and this course
        try {
            int courseId = getCourseId();
            Form form = new Form(entity);
            Double grade = Double.parseDouble(form.getFirstValue("grade"));
            if (grade != null && (grade < 0.0 || grade > 10.0)){
                return JsonMapRepresentation.getJSONforError("Invalid grade (must be between 0 and 10)");
            }
            String jwt = JWT.getJWTFromHeaders(getRequest());
            if (jwt == null) {    // no session
                return JsonMapRepresentation.getJSONforError("You need to be logged in to register a grade for a course");
            }
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
            if (u.isAdmin()){
                return JsonMapRepresentation.getJSONforError("You are not a student and therefore cannot have a grade on courses");
            }
            // register grade (might be null -> reset grade)
            coursesDAO.setGradeForCourse(u.getId(), courseId, grade);
            return JsonMapRepresentation.getJSONforSuccess();
        } catch (NumberFormatException e) {
            return JsonMapRepresentation.getJSONforError("Non-number given to parameter that must be a number");
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
            int courseId = getCourseId();
            Form form = new Form(entity);
            String title = form.getFirstValue("title");
            String ectsStr = form.getFirstValue("ects");
            String semesterStr = form.getFirstValue("semester");
            String path = form.getFirstValue("path");
            String type = form.getFirstValue("type");
            String specificpath = form.getFirstValue("specificpath");
            Feedback fb = coursesDAO.editCourse(new Course(courseId, title, (ectsStr == null) ? -1 : Integer.parseInt(ectsStr), (semesterStr == null) ? -1 : Integer.parseInt(semesterStr), path, null, type, specificpath));
            if (!fb.SUCCESS) {
                this.setStatus(Status.CLIENT_ERROR_NOT_FOUND);
                return null;
            }
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
            int courseId = getCourseId();
            Feedback fb = coursesDAO.deleteCourse(courseId);
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
